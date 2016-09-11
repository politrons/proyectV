package model.app

import factories.ApplicationFactory
import implicits.Utils.anyUtils

import scala.util.parsing.json.{JSONArray, JSONObject}

/**
  * Created by pabloperezgarcia on 31/8/16.
  */
object AppleStore {

  def applications(jsonArray: JSONArray): List[Application] = {
    var applications: List[Application] = List()
    jsonArray.list foreach (json => {
      try {
        val application = ApplicationFactory.create(new JSONObject(json.asStringMap))
        applications = applications ++ List(application)
      } catch {
        case e: NoSuchElementException => {
          println(s"Error adding app:$json")
        }
      }
    })
    applications
  }

}
