package mocks

import scala.io.Source
import scala.util.parsing.json.{JSON, JSONArray}

/**
  * Created by pabloperezgarcia on 27/8/16.
  */
object SteamStoreMock {

  def mockGame():JSONArray ={
    val source = Source.fromURL(getClass.getResource("/game.json")).getLines.mkString
    val map = JSON.parseFull(source).get.asInstanceOf[Map[String, Any]]
    new JSONArray(List(map))
  }

}
