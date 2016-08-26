package http

import exceptions.HttpResponseException

import scala.util.parsing.json._
import scalaj.http.{HttpRequest, HttpResponse}

trait HttpExtensions {

  def CONNECT_TIMEOUT: Int = 10000

  def READ_TIMEOUT: Int = 10000

  implicit class HttpRequestExt(request: HttpRequest) {
    def sendJson: HttpResponse[String] = {
//      request.header("content-type", "application/json")
      // This sends request and returns response as a string.
      request.asString
    }

    def jsonContent: HttpRequest = {
      request.header("content-type", "application/json")
    }

    def asJson:JSONObject = {
      val response: HttpResponse[String] = request.asString
      if (response.isSuccess) {
        val map = JSON.parseFull(response.body).get.asInstanceOf[Map[String, Any]]
        val jsonList= map.get("results").get.asInstanceOf[List[Map[String, Any]]]
        new JSONObject(jsonList(0))
      }
      else
        throw new HttpResponseException(s"Error: $response")
    }
  }


}
