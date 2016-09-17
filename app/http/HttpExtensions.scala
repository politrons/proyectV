package http

import scala.util.parsing.json._
import scalaj.http.{HttpRequest, HttpResponse}

trait HttpExtensions {

  def CONNECT_TIMEOUT: Int = 60000

  def READ_TIMEOUT: Int = 60000

  implicit class HttpRequestExt(request: HttpRequest) {
    def sendJson: HttpResponse[String] = {
      request.header("content-type", "application/json")
      request.asString
    }

    def jsonContent: HttpRequest = {
      request.header("content-type", "application/json")
    }

    def asJson(function: HttpRequest => JSONArray):JSONArray = {
      function.apply(request)
    }

  }

}
