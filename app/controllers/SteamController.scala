package controllers

import javax.inject.Inject

import exceptions.HttpResponseException
import http.HttpClient._
import model.apple.music.{Album, Discography}
import play.api.cache._
import play.api.mvc._
import views.html

import scalaj.http.{HttpRequest, HttpResponse}

class SteamController @Inject()(cache: CacheApi) extends BaseController {


  private val gameIdsList: String = "api.steampowered.com/ISteamApps/GetAppList/v2"

  def games = Action { implicit request =>
    var albums: List[Album] = List()
    val games = get(s"$gameIdsList", asJsonGame)
    //      get(s"api.steampowered.com/ISteamNews/GetNewsForApp/v0002/?appid=$appId&maxlength=300&format=json")
    albums = Discography.albums(lastResponse.get)
    Discography.attachVideos(lastResponse.get, albums)
    Ok(html.discography(albums))
  }

  import scala.util.parsing.json._

  def asJsonGame: (HttpRequest) => JSONArray = {
    request =>
      val response: HttpResponse[String] = request.asString
      if (response.isSuccess) {
        val map = util.parsing.json.JSON.parseFull(response.body).get.asInstanceOf[Map[String, Any]]
        val jsonList = map.get("results").get.asInstanceOf[List[Map[String, Any]]]
        new JSONArray(jsonList)
      }
      else
        throw new HttpResponseException(s"Error: $response")
  }

}


