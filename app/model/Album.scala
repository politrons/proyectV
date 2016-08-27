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
            val previewUrl: String,
            val artWorkUrl: String
           ) {

  var songs: List[Song] = List(new Song(trackName, previewUrl))

  def addSongs(song: Song) {
    songs = songs.::(song)
  }

}
