package controllers

import javax.inject.Inject

import exceptions.HttpResponseException
import http.HttpClient._
import implicits.Utils.optionUtils
import model.apple.app.{AppleStore, Application}
import model.apple.movie.{AppleTv, Movie}
import model.apple.music.{Album, Discography}
import play.api.cache._
import play.api.mvc._
import views.html

import scala.concurrent.duration._
import scala.util.parsing.json._
import scalaj.http.{HttpRequest, HttpResponse}

class AppleController @Inject()(cache: CacheApi) extends BaseController {

  private val appleAPI: String = "itunes.apple.com/search?term="

  def discography = Action { implicit request =>
    val artist: Option[String] = request.getQueryString("artist")
    artist.isEmpty match {
      case true => Ok(html.discography(Discography.albums(new JSONArray(List()))))
      case _ => Ok(html.discography(getDiscography(artist)))
    }
  }

  private def getDiscography(artist: Option[String]): List[Album] = {
    val cacheKey = artist.asString + "-" + country
    var albums: List[Album] = List()
    val albumsCached = cache.get(cacheKey)
    albumsCached.isEmpty match {
      case true => {
        albums = findAlbums(artist)
        attachVideoClips(artist, albums)
        cache.set(cacheKey, albums, 5.minutes)
      }
      case _ => albums = albumsCached.get
    }
    albums
  }

  private def attachVideoClips(artist: Option[String], albums: List[Album]): List[Album] = {
    get(s"$appleAPI${artist.get.replace(" ", "+").concat(s"&country=$country&entity=musicVideo")}", asJson, "https")
    Discography.attachVideos(lastResponse.get, albums)
  }

  private def findAlbums(artist: Option[String]): List[Album] = {
    get(s"$appleAPI${artist.get.replace(" ", "+")}", asJson, "https")
    Discography.albums(lastResponse.get)
  }

  def application = Action { implicit request =>
    val app: Option[String] = request.getQueryString("app")
    app.isEmpty match {
      case true => Ok(html.application(AppleStore.applications(new JSONArray(List()))))
      case _ => Ok(html.application(getApplications(app)))
    }
  }

  private def getApplications(app: Option[String]): List[Application] = {
    val cacheKey = app.asString + "-" + country
    var apps: List[Application] = List()
    val appsCached = cache.get(cacheKey)
    appsCached.isEmpty match {
      case true => {
        apps = findApps(app)
        cache.set(cacheKey, apps, 5.minutes)
      }
      case _ => apps = appsCached.get
    }
    apps
  }

  private def findApps(app: Option[String]): List[Application] = {
    get(s"$appleAPI${app.get.replace(" ", "+").concat(s"&country=$country&entity=software")}", asJson, "https")
    AppleStore.applications(lastResponse.get)
  }

  def movie = Action { implicit request =>
    val title: Option[String] = request.getQueryString("movie")
    title.isEmpty match {
      case true => Ok(html.movie(AppleTv.movies(new JSONArray(List()))))
      case _ => Ok(html.movie(getMovies(title)))
    }
  }

  private def getMovies(title: Option[String]): List[Movie] = {
    var movies: List[Movie] = List()
    val cacheKey = title.asString + "-" + country
    val moviesCached = cache.get(cacheKey)
    moviesCached.isEmpty match {
      case true => {
        movies = findMovies(title)
        cache.set(cacheKey, movies, 5.minutes)
      }
      case _ => movies = moviesCached.get
    }
    movies
  }

  def findMovies(movie: Option[String]): List[Movie] = {
    get(s"$appleAPI${movie.get.replace(" ", "+").concat(s"&country=$country&entity=movie")}", asJson, "https")
    AppleTv.movies(lastResponse.get)
  }

  def asJson: (HttpRequest) => JSONArray = {
    request =>
      val response: HttpResponse[String] = request.asString
      response.isSuccess match {
        case true => {
          val map = util.parsing.json.JSON.parseFull(response.body).get.asInstanceOf[Map[String, Any]]
          val jsonList = map.get("results").get.asInstanceOf[List[Map[String, Any]]]
          new JSONArray(jsonList)
        }
        case _ => throw new HttpResponseException(s"Error: $response")
      }
  }

}


