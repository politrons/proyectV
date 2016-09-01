package controllers

import http.HttpClient._
import model.app.AppleStore
import model.music.Discography
import play.api.mvc._
import views.html

import scala.util.parsing.json.JSONArray

class BaseController extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def search() = Action {
    Ok(html.discography(Discography.albums(new JSONArray(List()))))
  }

  def discography = Action { implicit request =>
    val artist: Option[String] = request.getQueryString("artist")
    get(s"itunes.apple.com/search?term=${artist.get.replace(" ", "+")}", "https")
    val albums = Discography.albums(lastResponse.get)
    get(s"itunes.apple.com/search?term=${artist.get.replace(" ", "+").concat("&entity=musicVideo")}", "https")
    Discography.attachVideos(lastResponse.get, albums)
    Ok(html.discography(albums))
  }

  def application = Action { implicit request =>
    val artist: Option[String] = request.getQueryString("app")
    if (!artist.isDefined) {
      Ok(html.application(AppleStore.applications(new JSONArray(List()))))
    } else {
      get(s"itunes.apple.com/search?term=${artist.get.replace(" ", "+").concat("&country=us&entity=software")}", "https")
      val apps = AppleStore.applications(lastResponse.get)
      Ok(html.application(apps))
    }
  }

}


