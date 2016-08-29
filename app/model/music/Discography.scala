package model.music

import factories.{AlbumFactory, VideoClipFactory}
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
        if (!mergeAlbum(album, albums)) {
          albums = albums ++ List(album)
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
    videosArray.list foreach (json => {
      val jsonVideo = new JSONObject(json.asStringMap)
      try {
        val videoClip = VideoClipFactory.create(jsonVideo)
        mergeVideoClips(videoClip, albums)
      } catch {
        case e: NoSuchElementException => {
          println(s"Error adding album:$jsonVideo")
        }
      }
    })
    albums
  }

  def mergeAlbum(newAlbum: Album, albums: List[Album]): Boolean = {
    var found = false
    albums.toStream
      .filter(album => isSameAlbum(newAlbum, album))
      .foreach(album => {
        found = true
        album.addSongs(newAlbum.songs.head)
      })
    found
  }

  def mergeVideoClips(video: VideoClip, albums: List[Album]) {
    albums foreach (album => {
      album.songs foreach (song => {
        if (song.trackName.equals(video.trackName)) {
          song.addVideoClip(video)
        }
      })
    })
  }


  def isSameAlbum(newAlbum: Album, album: Album): Boolean = {
    album.collectionName.equals(newAlbum.collectionName)
  }
}


