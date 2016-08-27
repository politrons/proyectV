package model

import java.util.NoSuchElementException

import scala.util.parsing.json.{JSONArray, JSONObject}

/**
  * Created by pabloperezgarcia on 27/8/16.
  */
object Discography {

  def create(array: JSONArray): List[Album] = {

    var albums: List[Album] = List()

    array.list foreach (json => {
      val jsonAlbum = new JSONObject(json.asInstanceOf[Map[String, Any]])
      try {
        val album = AlbumFactory.create(jsonAlbum)
        albums = albums ++ List(album)
      } catch {
        case e: NoSuchElementException => {
          println(s"Error adding album:$jsonAlbum")
        }
      }
    })
    albums
  }
//
//  def mergeAlbums(album: Album, albums: List[Album]): List[Album] = {
//    Observable.from(albums)
//      .map(al => Observable.just(al.collectionName)
//        .filter(disc => disc.equals(album.collectionName))
//        .map(x => al.addTrack(album.trackNames)
//  }

}


