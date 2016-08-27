package http

import exceptions.HttpResponseException

import scala.util.parsing.json._
import scalaj.http.{HttpRequest, HttpResponse}

trait HttpExtensions {

  def CONNECT_TIMEOUT: Int = 10000

  def READ_TIMEOUT: Int = 10000

  implicit class HttpRequestExt(request: HttpRequest) {
    def sendJson: HttpResponse[String] = {
      request.header("content-type", "application/json")
      request.asString
    }

    def jsonContent: HttpRequest = {
      request.header("content-type", "application/json")
    }

    def asJson:JSONArray = {
      val response: HttpResponse[String] = request.asString
      if (response.isSuccess) {
        val map = JSON.parseFull(response.body).get.asInstanceOf[Map[String, Any]]
        val jsonList= map.get("results").get.asInstanceOf[List[Map[String, Any]]]
        new JSONArray(jsonList)
      }
      else
        throw new HttpResponseException(s"Error: $response")
    }

  }


}
