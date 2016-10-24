package persistance

import events.{EventBase, UserCreated}
import model.user.User


object EventMapping {

  val eventMapping = collection.mutable.Map[Class[_ <: EventBase], (User, EventBase) => Unit]()

  setMapping[UserCreated](classOf[UserCreated], (user, evt) => user.loadUserName(evt.userName))

  private def setMapping[T <: EventBase](clazz: Class[T], fn: (User, T) => Unit) {
    eventMapping += clazz -> fn.asInstanceOf[(User, EventBase) => Unit]
  }


}
