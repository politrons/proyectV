package persistance

import com.couchbase.client.java.document.JsonDocument
import com.couchbase.client.java.document.json.JsonObject

/**
  * Created by pabloperezgarcia on 25/10/2016.
  */
trait PersistenceDAO {

  /**
    * Initialize the persistence layer
    */
  def init()

  /**
    * Find the Document by Id and map it into JsonObject
    */
  def getDocument(documentId: String): JsonObject

  /**
    * Receive a JsonDocument and insert into the bucket
    */
  def insert(document: JsonDocument): String

  /**
    * Receive a JsonDocument and replace a previous document by this new one
    */
  def replace(document: JsonDocument)

}
