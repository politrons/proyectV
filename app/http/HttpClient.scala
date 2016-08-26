package http

import scala.util.parsing.json.JSONObject
import scalaj.http.{Http, HttpOptions}

object HttpClient extends HttpExtensions {

  var lastResponse: Option[JSONObject] = None

  def get(url: String, protocol: String = "http") {
    lastResponse = Some(
      Http(s"$protocol://$url")
        .timeout(connTimeoutMs = CONNECT_TIMEOUT, readTimeoutMs = READ_TIMEOUT)
        .option(HttpOptions.allowUnsafeSSL)
        .asJson
    )
  }

  def post(url: String,  protocol: String = "http") {
    lastResponse = Some(
      Http(s"$protocol://$url").option(HttpOptions.allowUnsafeSSL)
        .timeout(connTimeoutMs = CONNECT_TIMEOUT, readTimeoutMs = READ_TIMEOUT)
        .postForm
        .asJson
    )
  }

}