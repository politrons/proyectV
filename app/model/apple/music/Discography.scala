package model.apple.music

import factories.{AlbumFactory, VideoClip}
import implicits.Utils.anyUtils

import scala.util.parsing.json.{JSONArray, JSONObject}

/**
  * Model class in charge of logic of discogrpahies of artists
  */
object Discography {

  def albums(albumsArray: JSONArray): List[Album] = {
    var albums: List[Album] = List()
    albumsArray.list foreach (json => {
      val jsonAlbum = new JSONObject(json.asStringMap)
      try {
        val album = AlbumFactory.create(jsonAlbum)
        if (!isAlbumMerged(album, albums)) {
          albums = album :: albums
        }
      } catch {
        case e: NoSuchElementException => {
          println(s"Error adding album:$jsonAlbum")
        }
      }
    })
    albums
  }

  def attachVideos(videosArray: JSONArray, albums: List[Album]): List[Album] = {
    albums.toStream
      .flatMap(album => album.songs.toStream
        .flatMap(song => videosArray.list.toStream
          .map(json => new JSONObject(json.asStringMap))
          .map(jsonVideo => VideoClip.create(jsonVideo))
          .filter(video => song.trackName.equals(video.trackName))
          .map(video => song.addVideoClip(video))
          .map(song => album.replace(song))))
      .toList
  }

  def isAlbumMerged(newAlbum: Album, albums: List[Album]): Boolean = {
    var found = false
    albums.toStream
      .filter(album => isSameAlbum(newAlbum, album))
      .foreach(album => {
        found = true
        album.addSongs(newAlbum.songs.head)
      })
    found
  }

  def isSameAlbum(newAlbum: Album, album: Album): Boolean = {
    album.collectionName.equals(newAlbum.collectionName)
  }
}


