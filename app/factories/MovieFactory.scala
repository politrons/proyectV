package factories

import implicits.Utils.optionUtils
import model.movie.Movie

import scala.util.parsing.json.JSONObject


/**
  * Created by pabloperezgarcia on 27/8/16.
  */
object MovieFactory {


  def create(json: JSONObject): Movie = {
    new Movie(
      json.obj.get("artistName").asString,
      json.obj.get("trackName").asString,
      json.obj.get("previewUrl").asString,
      json.obj.get("trackViewUrl").asString,
      json.obj.get("trackRentalPrice").asString,
      json.obj.get("trackPrice").asString,
      json.obj.get("collectionHdPrice").asString,
      json.obj.get("primaryGenreName").asString,
      json.obj.get("artworkUrl100").asString
    )
  }

}
