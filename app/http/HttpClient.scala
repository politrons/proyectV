package http

import scala.util.parsing.json.JSONArray
import scalaj.http.{HttpRequest, Http, HttpOptions}

object HttpClient extends HttpExtensions {

  var lastResponse: Option[JSONArray] = None

  def get(url: String, function:HttpRequest=>JSONArray, protocol: String = "http") {
    lastResponse = Some(
      Http(s"$protocol://$url")
        .timeout(connTimeoutMs = CONNECT_TIMEOUT, readTimeoutMs = READ_TIMEOUT)
        .option(HttpOptions.allowUnsafeSSL)
        .asJson(function)
    )
  }

  def post(url: String, function:(HttpRequest)=>JSONArray, protocol: String = "http") {
    lastResponse = Some(
      Http(s"$protocol://$url").option(HttpOptions.allowUnsafeSSL)
        .timeout(connTimeoutMs = CONNECT_TIMEOUT, readTimeoutMs = READ_TIMEOUT)
        .postForm
        .asJson(function)
    )
  }

}