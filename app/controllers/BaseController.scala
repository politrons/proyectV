package controllers

import javax.inject.Inject

import http.HttpClient._
import implicits.Utils.optionUtils
import model.app.{AppleStore, Application}
import model.movie.{AppleTv, Movie}
import model.music.{Album, Discography}
import play.api.cache._
import play.api.mvc._
import views.html

import scala.concurrent.duration._
import scala.util.parsing.json.JSONArray

class BaseController @Inject()(cache: CacheApi) extends Controller {

  var country = "us"

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def selectCountry = Action { implicit request =>
    val newCountry: Option[String] = request.getQueryString("country")
    country = newCountry.get
    Ok(views.html.index("Your new application is ready."))
  }

  private val appleAPI: String = "itunes.apple.com/search?term="

  def discography = Action { implicit request =>
    val artist: Option[String] = request.getQueryString("artist")
    if (artist.isEmpty) {
      Ok(html.discography(Discography.albums(new JSONArray(List()))))
    } else {
      val cacheKey=artist.asString+"-"+country
      var albums: List[Album] = List()
      val albumsCached = cache.get(cacheKey)
      if (albumsCached.isEmpty) {
        get(s"$appleAPI${artist.get.replace(" ", "+")}", "https")
        albums = Discography.albums(lastResponse.get)
        get(s"$appleAPI${artist.get.replace(" ", "+").concat(s"&country=$country&entity=musicVideo")}", "https")
        Discography.attachVideos(lastResponse.get, albums)
        cache.set(cacheKey, albums, 5.minutes)
      } else {
        albums = albumsCached.get
      }
      Ok(html.discography(albums))
    }
  }

  def application = Action { implicit request =>
    val app: Option[String] = request.getQueryString("app")
    if (app.isEmpty) {
      Ok(html.application(AppleStore.applications(new JSONArray(List()))))
    } else {
      val cacheKey=app.asString+"-"+country
      var apps: List[Application] = List()
      val appsCached = cache.get(cacheKey)
      if (appsCached.isEmpty) {
        get(s"$appleAPI${app.get.replace(" ", "+").concat(s"&country=$country&entity=software")}", "https")
        apps = AppleStore.applications(lastResponse.get)
        cache.set(cacheKey, apps, 5.minutes)
      } else {
        apps = appsCached.get
      }
      Ok(html.application(apps))

    }
  }

  def movie = Action { implicit request =>
    val movie: Option[String] = request.getQueryString("movie")
    if (movie.isEmpty) {
      Ok(html.movie(AppleTv.movies(new JSONArray(List()))))
    } else {
      val cacheKey=movie.asString+"-"+country
      var movies: List[Movie] = List()
      val moviesCached = cache.get(cacheKey)
      if (moviesCached.isEmpty) {
        get(s"$appleAPI${movie.get.replace(" ", "+").concat(s"&country=$country&entity=movie")}", "https")
        movies = AppleTv.movies(lastResponse.get)
        cache.set(cacheKey, movies, 5.minutes)
      } else {
        movies = moviesCached.get
      }
      Ok(html.movie(movies))
    }
  }

}


