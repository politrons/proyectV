package controllers

import play.api.mvc._

class BaseController extends Controller {

  var country = "us"

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def selectCountry = Action { implicit request =>
    val newCountry: Option[String] = request.getQueryString("country")
    country = newCountry.get
    Ok(views.html.index("Your new application is ready."))
  }
}


