package model

/**
  * Created by pabloperezgarcia on 27/8/16.
  */
class Album(val artistName: String,
            val collectionName: String,
            val primaryGenreName: String,
            val country: String,
            val trackPrice: String,
            val releaseDate: String,
            val trackName: String,
            val previewUrl: String
           ) {

  var trackNames: List[String] = List(trackName)
  var previewUrls: List[String] = List(previewUrl)

  def addTrack(name: String) {
    trackNames = trackNames.::(name)
  }

  def addPreviewUrl(url: String) {
    previewUrls = previewUrls.::(url)
  }

}
