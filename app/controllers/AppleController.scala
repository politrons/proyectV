package controllers

import javax.inject.Inject

import appleSearch.model.app.Application
import appleSearch.model.movie.Movie
import play.api.cache._
import play.api.mvc.Action
import politrons.apple.search.model.music.Album
import views.html

class AppleController @Inject()(cache: CacheApi) extends BaseController {

  def discography = Action { implicit request =>
    val artist: Option[String] = request.getQueryString("artist")
    val album = new Album(artistName = artist.get, country = country)
    artist.isEmpty match {
      case true => Ok(html.discography(List(new Album())))
      case _ => Ok(html.discography(album.find().get))
    }
  }

  def application = Action { implicit request =>
    val app: Option[String] = request.getQueryString("app")
    val apps = new Application(artistName = app.get, country = country)
    app.isEmpty match {
      case true => Ok(html.application(List()))
      case _ => Ok(html.application(apps.find().get))
    }
  }

  def movie = Action { implicit request =>
    val title: Option[String] = request.getQueryString("movie")
    val movie = new Movie(artistName=title.get, country = country)
    title.isEmpty match {
      case true => Ok(html.movie(List()))
      case _ => Ok(html.movie(movie.find().get))
    }
  }


  //  val cacheKey = artist.asString + "-" + country
  //  var albums: List[Album] = List()
  //  val albumsCached = cache.get(cacheKey)
  //  albumsCached.isEmpty match {
  //    case true =>
  //      albums = findAlbums(artist)
  //      attachVideoClips(artist, albums)
  //      cache.set(cacheKey, albums, 5.minutes)
  //    case _ => albums = albumsCached.get
  //  }
  //  albums

}


