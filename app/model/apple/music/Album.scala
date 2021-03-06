package model.apple.music

import model.apple.AppleBase

/**
  * Created by pabloperezgarcia on 27/8/16.
  */
class Album(artistName: String,
            val collectionName: String,
            val primaryGenreName: String,
            val country: String,
            val trackPrice: String,
            val trackViewUrl: String,
            val releaseDate: String,
            trackName: String,
            val previewUrl: String,
            val artWorkUrl: String
           ) extends AppleBase(artistName, trackName) {

  def replace(song: Song): Album = {
    songs.map { case song => song }
    this
  }


  var songs: List[Song] = List(new Song(trackName, previewUrl, trackPrice, trackViewUrl))

  def addSongs(song: Song) {
    songs = songs.::(song)
  }

}
