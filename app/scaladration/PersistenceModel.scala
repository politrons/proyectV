package scaladration

import java.io.IOException

import com.couchbase.client.java.document.json.JsonObject.{from, _}
import com.couchbase.client.java.document.json.{JsonArray, JsonObject}
import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

import scala.reflect._

object PersistenceModel {

  private val EVENTS: String = "events"
  private val mapper: ObjectMapper = new ObjectMapper
  private val eventMapping = collection.mutable.Map[Class[_ <: Event], (Model, Event) => AnyVal]()

  def initialize[M <: Model : ClassTag](persistenceDAO: PersistenceDAO): M = {
    val model = classTag[M].runtimeClass.newInstance.asInstanceOf[M]
    persistenceDAO.init()
    model.setPersistence(persistenceDAO)
    model
  }

  implicit class model(model: Model) {

    /**
      *
      * @param clazz className to be used as key
      * @param fn    funtion to be used in rehydrate
      * @tparam E      Event type to be used as generic
      * @tparam M      Model to be used as function argument
      * @tparam Return Type to be used in function
      */
    def setMapping[E <: Event, M <: Model, Return](clazz: Class[E], fn: (M, E) => Return) {
      eventMapping += clazz -> fn.asInstanceOf[(Model, Event) => AnyVal]
    }

    /**
      * This method will create the document where all events for that documentId.
      */
    def createDocument(documentId: String): String = {
      val userDocument: JsonObject = create.put(EVENTS, JsonArray.create)
      model.dao.insert(documentId, userDocument.toString)
    }

    /**
      * This method will append event in the previous document created.
      */
    def appendEvent(documentId: String, event: Event) {
      val document = model.dao.getDocument(documentId)
      val jsonDocument = JsonObject.fromJson(document)
      jsonDocument.getArray(EVENTS).add(fromJson(event.encode))
      model.dao.replace(documentId, jsonDocument.toString)
    }

    /**
      * Get the document from Couchbase and rehydrate the User from the events.
      */
    def rehydrate(documentId: String) = {
      val document = model.dao.getDocument(documentId)
      val jsonDocument = JsonObject.fromJson(document)
      deserialiseEvents(model, jsonDocument)
    }

    import scala.collection.JavaConversions._

    private def deserialiseEvents(model: Model, document: JsonObject): model = {
      val array: JsonArray = document.getArray(EVENTS)
      array.toList.toList.foreach { entry =>
        val json = from(entry.asInstanceOf[java.util.HashMap[String, JsonObject]])
        applyEvent(model, deserialiseEvent(json))
      }
      model
    }

    private def deserialiseEvent(event: JsonObject): Event = {
      try {
        mapper.readValue(event.toString, new TypeReference[Event]() {
        })
      }
      catch {
        case e: IOException =>
          throw new IllegalArgumentException("Exception parsing JSON: " + event, e)
      }
    }

    private def applyEvent(model: Model, event: Event) {
      eventMapping(event.getClass).apply(model, event)
    }

  }

}

