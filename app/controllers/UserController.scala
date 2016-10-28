package controllers

import javax.inject.Inject

import events.UserCreated
import model.account.User
import persistance.CouchbaseDAO
import play.api.cache._
import play.api.mvc._

import scaladration.Model
import scaladration.PersistenceModel._

class UserController @Inject()(cache: CacheApi) extends BaseController {

  val user = initialize[User](new CouchbaseDAO())

  def create = Action { implicit request =>
    val userName = request.getQueryString("userName")
    if (userName.isDefined) {
      val documentId: String = user.createDocument(userName.get)
      val event = new UserCreated(documentId, "")

      user.appendEvent(documentId, event, classOf[UserCreated], getCreateUserAction)
      user.rehydrate(documentId)
      Ok(views.html.index("Your new application is ready.", user))
    } else {
      Ok(views.html.index("Your new application is ready."))
    }
  }

  def search = Action { implicit request =>
    val userName = request.getQueryString("userName")
    if (userName.isDefined) {
      user.rehydrate(userName.get)
      Ok(views.html.index("Your new application is ready.", user))
    } else {
      Ok(views.html.index("Your new application is ready."))
    }

  }

  private def getCreateUserAction: (Model, UserCreated) => Unit = {
    (model, evt) => user.loadUserName(evt.userName, evt.password)
  }

}



