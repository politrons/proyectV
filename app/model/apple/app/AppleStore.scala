package model.apple.app

import factories.ApplicationFactory
import implicits.Utils.anyUtils

import scala.util.parsing.json.{JSONArray, JSONObject}

/**
  * Created by pabloperezgarcia on 31/8/16.
  */
object AppleStore {

  def applications(applicationsArray: JSONArray): List[Application] = {
    applicationsArray.list.toStream
      .map(app => {
        try {
          ApplicationFactory.create(new JSONObject(app.asStringMap))
        } catch {
          case e: NoSuchElementException => null
        }
      }).filter(app => app != null)
      .toList
  }

}
