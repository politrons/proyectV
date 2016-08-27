package model

import java.util.NoSuchElementException
import rx.lang.scala.Observable
import scala.util.parsing.json.{JSONArray, JSONObject}
import implicits.Utils.anyUtils

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
        albums = albums ++ List(album)
      } catch {
        case e: NoSuchElementException => {
          println(s"Error adding album:$jsonAlbum")
        }
      }
    })
    albums
  }

  def mergeAlbums(a: Album, albums: List[Album]): List[Album] = {
    Observable.just(a)
      .map(newAlbum => Observable.from(albums)
        .filter(album => album.equals(newAlbum.collectionName))
        .map(album => {
          album.addTrack(newAlbum.trackNames.head)
          album.addPreviewUrl(newAlbum.previewUrls.head)
          album
        }).toList)
      .switchIfEmpty(Observable.from(List(a)))
      .toBlocking
      .last
      .asInstanceOf[List[Album]]
  }

}


