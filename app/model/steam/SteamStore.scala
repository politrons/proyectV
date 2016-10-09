package model.steam

import factories.GameFactory
import implicits.Utils.{anyUtils, jsonArrayUtils, optionUtils}

import scala.util.parsing.json.{JSONArray, JSONObject}

/**
  * Created by pabloperezgarcia on 31/8/16.
  */
object SteamStore {

  def game(gameId: String, jsonArray: JSONArray): Game = {
    try {
       GameFactory.create(new JSONObject(getJsonGame(gameId, jsonArray.asFirstStringMap)))
    } catch {
      case e: NoSuchElementException => null
    }
  }

  def getJsonGame(gameId: String, json: Map[String, Any]): Map[String, Any] = {
    val jsonData = json.get(gameId).asStringMap
    jsonData.get("data").asStringMap
  }

  def gamesIds(jsonArray: JSONArray, from: Int, to: Int): List[GameId] = {
    jsonArray.list.slice(from, to).toStream
      .map(json => {
        try {
          GameFactory.createId(new JSONObject(json.asStringMap))
        } catch {
          case e: NoSuchElementException => null
        }
      }).filter(gameId => gameId != null)
      .toList
      .sortBy(_.name)
}

}
