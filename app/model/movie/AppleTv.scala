package model.movie

import factories.MovieFactory
import implicits.Utils.anyUtils

import scala.util.parsing.json.{JSONArray, JSONObject}

/**
  * Created by pabloperezgarcia on 31/8/16.
  */
object AppleTv {

  def movies(jsonArray: JSONArray): List[Movie] = {
    var movies: List[Movie] = List()
    jsonArray.list foreach (json => {
      try {
        val movie = MovieFactory.create(new JSONObject(json.asStringMap))
        movies = movies ++ List(movie)
      } catch {
        case e: NoSuchElementException => {
          println(s"Error adding album:$json")
        }
      }
    })
    movies
  }

}
