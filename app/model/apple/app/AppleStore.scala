package model.apple.app

import factories.ApplicationFactory
import implicits.Utils.anyUtils

import scala.util.parsing.json.{JSONArray, JSONObject}

/**
  * Created by pabloperezgarcia on 31/8/16.
  */
object AppleStore {

  def applications(applicationsArray: JSONArray): List[Application] = {
    var applications: List[Application] = List()
    applicationsArray.list foreach (json => {
      try {
        applications = ApplicationFactory.create(new JSONObject(json.asStringMap)) :: applications
      } catch {
        case e: NoSuchElementException => {
          println(s"Error adding app:$json")
        }
      }
    })
    applications
  }

}
