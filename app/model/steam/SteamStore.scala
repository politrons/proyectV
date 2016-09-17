package model.steam

import factories.GameFactory
import implicits.Utils.anyUtils

import scala.util.parsing.json.{JSONArray, JSONObject}

/**
  * Created by pabloperezgarcia on 31/8/16.
  */
object SteamStore {

  def games(jsonArray: JSONArray): List[Game] = {
    var games: List[Game] = List()
    jsonArray.list foreach (json => {
      try {
        val game = GameFactory.create(new JSONObject(json.asStringMap))
        games = games ++ List(game)
      } catch {
        case e: NoSuchElementException => {
          println(s"Error adding app:$json")
        }
      }
    })
    games
  }

}
