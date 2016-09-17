package factories

import implicits.Utils.optionUtils
import model.steam.Game

import scala.util.parsing.json.JSONObject


/**
  * Created by pabloperezgarcia on 27/8/16.
  */
object GameFactory {


  def create(json: JSONObject): Game = {
    new Game(
      json.obj.get("gid").asString,
      json.obj.get("title").asString,
      json.obj.get("url").asString,
      json.obj.get("author").asString,
      json.obj.get("contents").asString,
      json.obj.get("feedlabel").asString,
      json.obj.get("feedname").asString
    )
  }


}
