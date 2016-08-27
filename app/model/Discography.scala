package model

import java.util.NoSuchElementException

import implicits.Utils.anyUtils

import scala.util.parsing.json.{JSONArray, JSONObject}

/**
  * Created by pabloperezgarcia on 27/8/16.
  */
object Discography {

  def create(array: JSONArray): List[Album] = {

    var albums: List[Album] = List()

    array.list foreach (json => {
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

  def isSameAlbum(newAlbum: Album, album: Album): Boolean = {
    album.collectionName.equals(newAlbum.collectionName)
  }
}


