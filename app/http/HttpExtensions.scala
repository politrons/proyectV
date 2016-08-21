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

    def asJson: Map[String, Any] = {
      val response: HttpResponse[String] = request.asString
      if (response.isSuccess) {
        JSON.parseFull(response.body).get.asInstanceOf[Map[String, Any]]
      }
      else
        throw new HttpResponseException(s"Error: $response")
    }
  }


}
