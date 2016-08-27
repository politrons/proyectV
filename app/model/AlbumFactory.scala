package model

import scala.util.parsing.json.JSONObject

/**
  * Created by pabloperezgarcia on 27/8/16.
  */
object AlbumFactory {

  implicit class parseJson(o: Option[Any]) {
    def asString: String = String.valueOf(o.get)
  }

  def create(json: JSONObject): Album = {
    val album = new Album(
      json.obj.get("artistName").asString,
      json.obj.get("collectionName").asString,
      json.obj.get("primaryGenreName").asString,
      json.obj.get("country").asString,
      json.obj.get("trackPrice").asString,
      json.obj.get("releaseDate").asString)

    album.addTrack(json.obj.get("trackName").asString)
    album.addPreviewUrl(json.obj.get("previewUrl").asString)
    album
  }

}
