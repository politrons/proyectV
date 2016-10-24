package persistance

import java.io.IOException

import com.couchbase.client.java.document.JsonDocument
import com.couchbase.client.java.document.json.JsonObject.{create, from, _}
import com.couchbase.client.java.document.json.{JsonArray, JsonObject}
import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import events.EventBase
import model.user.User
import persistance.CouchbaseDAO._
import persistance.EventMapping._
import rx.Observable


object EventSourcing {

  private val mapper: ObjectMapper = new ObjectMapper

  val EVENTS: String = "events"

  def initialize(): Unit = {
    initialize()
  }

  /**
    * This method will create the document where all events for that documentId.
    */
  def createDocument(documentId: String): Observable[JsonDocument] = {
    val emptyDocument: JsonObject = create.put(EVENTS, JsonArray.create)
    val userDocument: JsonDocument = JsonDocument.create(documentId, emptyDocument)
    insert(userDocument)


  }

  /**
    * This method will append event in the previous document created.
    */
  def appendEvent(documentId: String, event: EventBase): Observable[JsonDocument] = {
    val document = getDocument(documentId)
    document.getArray(EVENTS).add(fromJson(event.encode))
    replace(JsonDocument.create(documentId, document))
  }

  /**
    * Get the document from Couchbase and rehydrate the User from the events.
    */
  def getUser(documentId: String): User = {
    val document = getDocument(documentId)
    rehydrate(document)
  }

  import scala.collection.JavaConversions._

  @SuppressWarnings(Array("unchecked")) private def rehydrate(document: JsonObject): User = {
    val user: User = new User()
    val array: JsonArray = document.getArray(EVENTS)
    array.toList.toList.foreach { entry =>
      val json = from(entry.asInstanceOf[java.util.HashMap[String, JsonObject]])
      applyEvent(user, deserialiseEvent(json))
    }
    user
  }

  private def deserialiseEvent(event: JsonObject): EventBase = {
    try {
      mapper.readValue(event.toString, new TypeReference[EventBase]() {
      })
    }
    catch {
      case e: IOException =>
        throw new IllegalArgumentException("Exception parsing JSON: " + event, e)
    }
  }

  private def applyEvent(user: User, event: EventBase) {
    eventMapping.get(event.getClass).get.apply(user, event)
  }
}
