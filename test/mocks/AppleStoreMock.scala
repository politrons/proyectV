package mocks

import scala.io.Source
import scala.util.parsing.json.{JSON, JSONArray}

/**
  * Created by pabloperezgarcia on 27/8/16.
  */
object AppleStoreMock {

  def mockApps(): JSONArray ={
    val source = Source.fromURL(getClass.getResource("/apps.json")).getLines.mkString
    val map = JSON.parseFull(source).get.asInstanceOf[Map[String, Any]]
    val jsonList= map.get("results").get.asInstanceOf[List[Map[String, Any]]]
    new JSONArray(jsonList)
  }

}
