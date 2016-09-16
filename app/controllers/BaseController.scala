package controllers

import http.HttpClient._
import model.app.AppleStore
import model.movie.AppleTv
import model.music.Discography
import play.api.mvc._
import views.html

import scala.util.parsing.json.JSONArray

class BaseController extends Controller {

   var country="us"

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def selectCountry = Action { implicit request =>
    val newCountry: Option[String] = request.getQueryString("country")
    country=newCountry.get
    Ok(views.html.index("Your new application is ready."))
  }

  private val appleAPI: String = "itunes.apple.com/search?term="

  def discography = Action { implicit request =>
    val artist: Option[String] = request.getQueryString("artist")
    if (artist.isEmpty) {
      Ok(html.discography(Discography.albums(new JSONArray(List()))))
    }else {
      get(s"$appleAPI${artist.get.replace(" ", "+")}", "https")
      val albums = Discography.albums(lastResponse.get)
      get(s"$appleAPI${artist.get.replace(" ", "+").concat(s"&country=$country&entity=musicVideo")}", "https")
      Discography.attachVideos(lastResponse.get, albums)
      Ok(html.discography(albums))
    }
  }

  def application = Action { implicit request =>
    val app: Option[String] = request.getQueryString("app")
    if (app.isEmpty) {
      Ok(html.application(AppleStore.applications(new JSONArray(List()))))
    } else {
      get(s"$appleAPI${app.get.replace(" ", "+").concat(s"&country=$country&entity=software")}", "https")
      val apps = AppleStore.applications(lastResponse.get)
      Ok(html.application(apps))
    }
  }

  def movie = Action { implicit request =>
    val movie: Option[String] = request.getQueryString("movie")
    if (movie.isEmpty) {
      Ok(html.movie(AppleTv.movies(new JSONArray(List()))))
    } else {
      get(s"$appleAPI${movie.get.replace(" ", "+").concat(s"&country=$country&entity=movie")}", "https")
      val movies = AppleTv.movies(lastResponse.get)
      Ok(html.movie(movies))
    }
  }

}


