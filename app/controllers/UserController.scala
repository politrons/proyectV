package controllers

import javax.inject.Inject

import events.UserCreated
import model.user.User
import persistance.CouchbaseDAO
import play.api.cache._
import play.api.mvc._

import scalaHydration.EventSourcing


class UserController @Inject()(cache: CacheApi) extends BaseController {

  val eventSourcing= new EventSourcing()
  eventSourcing.initialize(new CouchbaseDAO())
  eventSourcing.setMapping[UserCreated, User](classOf[UserCreated], (user, evt) => user.loadUserName(evt.userName, evt.password))

  def create = Action { implicit request =>
    val userName = request.getQueryString("userName")
    if (userName.isDefined) {
      val documentId: String = eventSourcing.createDocument(userName.get)
      val event = new UserCreated(documentId, "")
      eventSourcing.appendEvent(documentId, event)
      val user:User = eventSourcing.rehydrateModel(new User(),documentId).asInstanceOf[User]
      Ok(views.html.index("Your new application is ready.", user))
    }else{
      Ok(views.html.index("Your new application is ready."))
    }

  }

  def search = Action { implicit request =>
    var fromRequest = request.getQueryString("username")
    if (fromRequest.isEmpty) {
      fromRequest = Option("0")
    }
    val userName = null
    Ok(views.html.index("Your new application is ready.", userName))

  }


}



