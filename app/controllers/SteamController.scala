package controllers

import javax.inject.Inject

import exceptions.HttpResponseException
import http.HttpClient._
import model.steam.{Game, SteamStore}
import play.api.cache._
import play.api.mvc._
import views.html

import scala.util.parsing.json._
import scalaj.http.{HttpRequest, HttpResponse}
import util.control.Breaks._

class SteamController @Inject()(cache: CacheApi) extends BaseController {

  private val gameIdsListAPI: String = "api.steampowered.com/ISteamApps/GetAppList/v2"

  private val gameAPI: String = "store.steampowered.com/api/appdetails?appids="

  def games = Action { implicit request =>
    get(s"$gameIdsListAPI", asJsonGamesId)
    val gamesIds = SteamStore.gamesIds(lastResponse.get)
    var games: List[Game] = List()
    var index = 0 //TODO:Pagination it´ needed
    breakable {
      gamesIds foreach (gameId => {
        try {
          get(s"$gameAPI${gameId.appid}&maxlength=300&format=json", asJsonGame)
          val game = SteamStore.game(gameId.appid, lastResponse.get)
          games = games ++ List(game)
          index += 1
          if (index == 20) break //TODO:Change this by pagination
        } catch {
          case e: Exception => {
            println(s"Error getting game:${gameId.appid}")
          }
        }
      })
    }
    Ok(html.gamesNews(games))
  }

  def asJsonGamesId: (HttpRequest) => JSONArray = {
    request =>
      val response: HttpResponse[String] = request.asString
      if (response.isSuccess) {
        val map = util.parsing.json.JSON.parseFull(response.body).get.asInstanceOf[Map[String, Any]]
        val jsonMap = map.get("applist").get.asInstanceOf[Map[String, Any]]
        val jsonList = jsonMap.get("apps").get.asInstanceOf[List[Map[String, Any]]]
        new JSONArray(jsonList)
      }
      else
        throw new HttpResponseException(s"Error: $response")
  }

  def asJsonGame: (HttpRequest) => JSONArray = {
    request =>
      val response: HttpResponse[String] = request.asString
      if (response.isSuccess) {
        val map = util.parsing.json.JSON.parseFull(response.body).get.asInstanceOf[Map[String, Any]]
        new JSONArray(List(map))
      }
      else
        throw new HttpResponseException(s"Error: $response")
  }

}


