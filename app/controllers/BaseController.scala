package controllers

import http.HttpClient._
import model.Discography
import play.api.mvc._

class BaseController extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def search(artist:String) = Action {
    get(s"itunes.apple.com/search?term=${artist.replace(" ", "+")}", "https")
    Ok(views.html.search(Discography.create(lastResponse.get)))
  }

}