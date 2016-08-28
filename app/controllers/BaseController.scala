package controllers

import http.HttpClient._
import model.Discography
import play.api.mvc._

import scala.util.parsing.json.JSONArray

class BaseController extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def search() = Action { implicit request =>
    Ok(views.html.discography(Discography.create(new JSONArray(List()))))
  }

  def discography = Action { implicit request =>
    val artist: Option[String] = request.getQueryString("artist")
    get(s"itunes.apple.com/search?term=${artist.get.replace(" ", "+")}", "https")
    Ok(views.html.discography(Discography.create(lastResponse.get)))
  }

}