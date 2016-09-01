package mocks

import scala.io.Source
import scala.util.parsing.json.{JSONArray, JSON}

/**
  * Created by pabloperezgarcia on 27/8/16.
  */
object ItunesMock {

  def mockMusic(): JSONArray ={
    val source = Source.fromURL(getClass.getResource("/music.json")).getLines.mkString
    val map = JSON.parseFull(source).get.asInstanceOf[Map[String, Any]]
    val jsonList= map.get("results").get.asInstanceOf[List[Map[String, Any]]]
    new JSONArray(jsonList)
  }

  def mockVideos(): JSONArray ={
    val source = Source.fromURL(getClass.getResource("/video.json")).getLines.mkString
    val map = JSON.parseFull(source).get.asInstanceOf[Map[String, Any]]
    val jsonList= map.get("results").get.asInstanceOf[List[Map[String, Any]]]
    new JSONArray(jsonList)
  }
}
