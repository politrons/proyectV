package controllers

import javax.inject.Inject

import events.UserCreated
import model.user.User
import persistance.{CouchbaseDAO, EventSourcing}
import play.api.cache._
import play.api.mvc._


class UserController @Inject()(cache: CacheApi) extends BaseController {

  val eventSourcing= new EventSourcing()
  eventSourcing.initialize(new CouchbaseDAO())
  eventSourcing.setMapping[UserCreated, User](classOf[UserCreated], (user, evt) => user.loadUserName(evt.userName))

  def create = Action { implicit request =>
    val userName = "politron"
    val documentId: String = eventSourcing.createDocument(userName)
    val event = new UserCreated(documentId)
    eventSourcing.appendEvent(documentId, event)
    val user = eventSourcing.rehydrateModel(new User(),documentId).asInstanceOf[User]
    Ok(views.html.index("Your new application is ready.", user.userName))
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



