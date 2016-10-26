package scalaHydration

import java.io.IOException

import com.couchbase.client.java.document.JsonDocument
import com.couchbase.client.java.document.json.JsonObject.{create, from, _}
import com.couchbase.client.java.document.json.{JsonArray, JsonObject}
import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

import scala.reflect._


class EventSourcing {

  private val mapper: ObjectMapper = new ObjectMapper

  val EVENTS: String = "events"

  val eventMapping = collection.mutable.Map[Class[_ <: EventBase], (Model, EventBase) => AnyVal]()

  var dao: PersistenceDAO = _

  def initialize(persistenceDAO: PersistenceDAO): Unit = {
    this.dao = persistenceDAO
    dao.init()
  }

  /**
    * This method will create the document where all events for that documentId.
    */
  def createDocument(documentId: String): String = {
    val emptyDocument: JsonObject = create.put(EVENTS, JsonArray.create)
    val userDocument: JsonDocument = JsonDocument.create(documentId, emptyDocument)
    dao.insert(userDocument)
  }

  /**
    * This method will append event in the previous document created.
    */
  def appendEvent(documentId: String, event: EventBase) {
    val document = dao.getDocument(documentId)
    document.getArray(EVENTS).add(fromJson(event.encode))
    dao.replace(JsonDocument.create(documentId, document))
  }

  /**
    * Get the document from Couchbase and rehydrate the User from the events.
    */
  def getModel[M <: Model : ClassTag](documentId: String): M = {
    val model = classTag[M].runtimeClass.newInstance.asInstanceOf[M]
    val document = dao.getDocument(documentId)
    rehydrate(model, document)
  }

  import scala.collection.JavaConversions._

  private def rehydrate[M<:Model](model: M, document: JsonObject): M = {
    val array: JsonArray = document.getArray(EVENTS)
    array.toList.toList.foreach { entry =>
      val json = from(entry.asInstanceOf[java.util.HashMap[String, JsonObject]])
      applyEvent(model, deserialiseEvent(json))
    }
    model
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

  private def applyEvent[M<:Model](model: M, event: EventBase) {
    eventMapping(event.getClass).apply(model, event)
  }

  def setMapping[E <: EventBase, M, Return](clazz: Class[E], fn: (M, E) => Return) {
    eventMapping += clazz -> fn.asInstanceOf[(Model, EventBase) => AnyVal]
  }

}
