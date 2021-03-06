package factories

import implicits.Utils.optionUtils
import model.apple.music.VideoClip

import scala.util.parsing.json.JSONObject

/**
  * Created by pabloperezgarcia on 28/8/16.
  */
object VideoClip {
  def create(videoClip: JSONObject): VideoClip = {
    new VideoClip(videoClip.obj.get("artistName").asString,
      videoClip.obj.get("trackName").asString,
      videoClip.obj.get("previewUrl").asString,
      videoClip.obj.get("artworkUrl100").asString)
  }
}
