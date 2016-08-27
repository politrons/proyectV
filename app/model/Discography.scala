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
      try {
        val jsonAlbum = new JSONObject(json.asInstanceOf[Map[String, Any]])
        val album = AlbumFactory.create(jsonAlbum)
        albums = albums ++ List(album)
      } catch {
        case e: NoSuchElementException => {
          println(s" Excewption adding alnum")
        }
      }
    })
    albums
  }

}


