package controllers

import javax.inject.Inject

import com.couchbase.client.java.document.JsonDocument
import events.UserCreated
import persistance.EventSourcing
import play.api.cache._
import play.api.mvc._


class UserController @Inject()(cache: CacheApi) extends BaseController {

  EventSourcing.initialize()

  def create = Action { implicit request =>
    //    var fromRequest = request.getQueryString("username")
    //    if (fromRequest.isEmpty) {
    //      fromRequest = Option("0")
    //    }
    val userName = "politron"
    val document: JsonDocument = EventSourcing.createDocument(userName).toBlocking.first()
    val event = new UserCreated(document.id())
    EventSourcing.appendEvent(document.id(), event).toBlocking.first()
    val user = EventSourcing.getUser(document.id())
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



