package factories

import implicits.Utils.optionUtils
import model.app.Application

import scala.util.parsing.json.JSONObject


/**
  * Created by pabloperezgarcia on 27/8/16.
  */
object ApplicationFactory {


  def create(json: JSONObject): Application = {
    new Application(
      json.obj.get("artistName").asString,
      json.obj.get("trackName").asString,
      json.obj.get("artistViewUrl").asString,
      json.obj.get("trackViewUrl").asString,
      json.obj.get("price").asString,
      json.obj.get("artworkUrl100").asString
    )
  }

}
