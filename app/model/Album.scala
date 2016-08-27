package model

/**
  * Created by pabloperezgarcia on 27/8/16.
  */
class Album(val artistName: String,
            val collectionName: String,
            val primaryGenreName: String,
            val country: String,
            val trackPrice: String,
            val releaseDate: String
                 ) {

  var trackNames: List[String]=List()
  var previewUrl: List[String]=List()

  def addTrack(name: String) {
    trackNames = trackNames.::(name)
  }

  def addPreviewUrl(url: String) {
    previewUrl = previewUrl.::(url)
  }

}
