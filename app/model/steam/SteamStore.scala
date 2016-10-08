package model.steam

import factories.GameFactory
import implicits.Utils.{anyUtils, jsonArrayUtils, optionUtils}

import scala.util.parsing.json.{JSONArray, JSONObject}

/**
  * Created by pabloperezgarcia on 31/8/16.
  */
object SteamStore {

  def game(gameId: String, jsonArray: JSONArray): Game = {
    // _ initialize in the default value of type, Int -> 0 reference instance null
    var game: Game = null
    val json = jsonArray.asFirstStringMap
    try {
      game = GameFactory.create(new JSONObject(getJsonGame(gameId, json)))
    } catch {
      case e: NoSuchElementException => {
        println(s"Error adding app:$json")
      }
    }
    game
  }

  def getJsonGame(gameId: String, json: Map[String, Any]): Map[String, Any] = {
    val jsonData = json.get(gameId).asStringMap
    jsonData.get("data").asStringMap
  }

  def gamesIds(jsonArray: JSONArray, from: Int, to: Int): List[GameId] = {
    var gamesId: List[GameId] = List()
    jsonArray.list.slice(from, to) foreach (json => {
      try {
        val gameId = GameFactory.createId(new JSONObject(json.asStringMap))
        gamesId = gameId :: gamesId
      } catch {
        case e: NoSuchElementException => {
          println(s"Error adding app:$json")
        }
      }
    })
    gamesId.sortBy(_.name)
  }

}
