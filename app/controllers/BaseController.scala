package controllers

import play.api.mvc._

class BaseController extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def search = Action {
    Ok(views.html.search("Section to search media"))
  }

}