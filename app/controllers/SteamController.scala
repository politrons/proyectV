package controllers

import javax.inject.Inject

import exceptions.HttpResponseException
import http.HttpClient._
import model.steam.{GameId, SteamStore}
import play.api.cache._
import play.api.mvc._
import views.html

import scala.concurrent.duration._
import scala.util.parsing.json._
import scalaj.http.{HttpRequest, HttpResponse}

import implicits.Utils.cacheUtils


class SteamController @Inject()(cache: CacheApi) extends BaseController {

  private val GAME_IDS_LIST_API: String = "api.steampowered.com/ISteamApps/GetAppList/v2"

  private val GAME_API: String = "store.steampowered.com/api/appdetails?appids="

  private val GAME_KEY: String = "games"

  loadGameIds()

  def games = Action { implicit request =>
    var fromRequest = request.getQueryString("from")
    if (fromRequest.isEmpty) {
      fromRequest = Option("0")
    }
    val from = Integer.parseInt(fromRequest.get) * 10
    val to = from + 10
    loadGameIds()
    Ok(html.games(getGamesIds(from, to), cache.jsonArraySize(GAME_KEY)/10))
  }

  private def getGamesIds(from: Int, to: Int): List[GameId] = {
    SteamStore.gamesIds(cache.getVal[JSONArray](GAME_KEY), from, to)
  }

  private def loadGameIds(): Unit = {
    val games = cache.get(GAME_KEY)
    if (games.isEmpty) {
      get(s"$GAME_IDS_LIST_API", asJsonGamesId)
      cache.set(GAME_KEY, lastResponse.get, 60.minutes)
    }
  }

  //  def gameDetails = Action { implicit request =>
  //    get(s"$gameIdsListAPI", asJsonGamesId)
  //    val gamesIds = SteamStore.gamesIds(lastResponse.get)
  //    var games: List[Game] = List()
  //    var index = 0 //TODO:Pagination it´ needed
  //    breakable {
  //      gamesIds foreach (gameId => {
  //        try {
  //          get(s"$gameAPI${gameId.appid}&maxlength=300&format=json", asJsonGame)
  //          val game = SteamStore.game(gameId.appid, lastResponse.get)
  //          games = games ++ List(game)
  //          index += 1
  //          if (index == 20) break //TODO:Change this by pagination
  //        } catch {
  //          case e: Exception => {
  //            println(s"Error getting game:${gameId.appid}")
  //          }
  //        }
  //      })
  //    }
  //    Ok(html.games(games))
  //  }

  //  def findHardware(): { implicit request =>

  //    /* Total number of processors or cores available to the JVM */
  //    System.out.println("Available processors (cores): " +
  //      Runtime.getRuntime().availableProcessors());
  //
  //    /* Total amount of free memory available to the JVM */
  //    System.out.println("Free memory (bytes): " +
  //      Runtime.getRuntime().freeMemory());
  //
  //    /* This will return Long.MAX_VALUE if there is no preset limit */
  //    long maxMemory = Runtime.getRuntime().maxMemory();
  //    /* Maximum amount of memory the JVM will attempt to use */
  //    System.out.println("Maximum memory (bytes): " +
  //      (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory));
  //
  //    /* Total memory currently available to the JVM */
  //    System.out.println("Total memory available to JVM (bytes): " +
  //      Runtime.getRuntime().totalMemory());
  //
  //    /* Get a list of all filesystem roots on this system */
  //    File[] roots = File.listRoots();
  //
  //    /* For each filesystem root, print some info */
  //    for (File root : roots) {
  //      System.out.println("File system root: " + root.getAbsolutePath());
  //      System.out.println("Total space (bytes): " + root.getTotalSpace());
  //      System.out.println("Free space (bytes): " + root.getFreeSpace());
  //      System.out.println("Usable space (bytes): " + root.getUsableSpace());
  //    }
  //  }


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



