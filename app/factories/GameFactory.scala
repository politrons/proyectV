package factories

import implicits.Utils.optionUtils
import model.steam.{GameId, Game}

import scala.util.parsing.json.JSONObject


/**
  * Created by pabloperezgarcia on 27/8/16.
  */
object GameFactory {

  def create(json: JSONObject): Game = {
    new Game(
      json.obj.get("steam_appid").asString,
      json.obj.get("name").asString,
      json.obj.get("detailed_description").asString,
      json.obj.get("supported_languages").asString,
      json.obj.get("header_image").asString,
      json.obj.get("background").asString,
      json.obj.get("website").asString)
  }

  def createId(json: JSONObject): GameId = {
    new GameId(
      String.valueOf(json.obj.get("appid").toInt))
  }


}
