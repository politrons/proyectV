package model.apple.movie

import factories.MovieFactory
import implicits.Utils.anyUtils

import scala.util.parsing.json.{JSONArray, JSONObject}

/**
  * Created by pabloperezgarcia on 31/8/16.
  */
object AppleTv {

  def movies(moviesArray: JSONArray): List[Movie] = {
    var movies: List[Movie] = List()
    moviesArray.list foreach (json => {
      try {
        movies = MovieFactory.create(new JSONObject(json.asStringMap)) :: movies
      } catch {
        case e: NoSuchElementException => {
          println(s"Error adding movie:$json")
        }
      }
    })
    movies
  }

}
