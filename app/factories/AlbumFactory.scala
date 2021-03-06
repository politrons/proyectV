package factories

import implicits.Utils.optionUtils
import model.apple.music.Album

import scala.util.parsing.json.JSONObject


/**
  * Created by pabloperezgarcia on 27/8/16.
  */
object AlbumFactory {

  def create(json: JSONObject): Album = {
    new Album(
      json.obj.get("artistName").asString,
      json.obj.get("collectionName").asString,
      json.obj.get("primaryGenreName").asString,
      json.obj.get("country").asString,
      json.obj.get("trackPrice").asString,
      json.obj.get("trackViewUrl").asString,
      json.obj.get("releaseDate").asString,
      json.obj.get("trackName").asString,
      json.obj.get("previewUrl").asString,
      json.obj.get("artworkUrl100").asString
    )
  }

}
