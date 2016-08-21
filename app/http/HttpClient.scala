package http

import scalaj.http.{HttpResponse, Http, HttpOptions}

object HttpClient extends HttpExtensions {

  var lastResponse: Option[HttpResponse[String]] = None

  def get(url: String, protocol: String = "http") {
    lastResponse = Some(
      Http(s"$protocol://$url")
        .timeout(connTimeoutMs = CONNECT_TIMEOUT, readTimeoutMs = READ_TIMEOUT)
        .option(HttpOptions.allowUnsafeSSL)
        .sendJson
    )
  }

  def post(url: String,  protocol: String = "http") {
    lastResponse = Some(
      Http(s"$protocol://$url").option(HttpOptions.allowUnsafeSSL)
        .timeout(connTimeoutMs = CONNECT_TIMEOUT, readTimeoutMs = READ_TIMEOUT)
        .postForm
        .sendJson
    )
  }


}