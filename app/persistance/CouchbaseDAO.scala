package persistance

import com.couchbase.client.java.document.JsonDocument
import com.couchbase.client.java.document.json.JsonObject
import com.couchbase.client.java.{AsyncBucket, CouchbaseAsyncCluster}
import rx.Observable


object CouchbaseDAO {

  private var bucket: AsyncBucket = _

  /**
    * Initialize the cluster and get the bucket to be used for the API
    */
  def init() = {
    if (bucket == null) {
      val cluster = CouchbaseAsyncCluster.create("localhost")
      val bucket = cluster.openBucket("projectV", "politron").toBlocking.first()
      setBucket(bucket)
    }
  }

  /**
    * Find the Document by Id and map it into JsonObject
    */
  def getDocument(documentId: String): JsonObject = {
    val loaded = bucket.get(documentId).toBlocking.first()
    loaded.content()
  }

  /**
    * Receive a JsonDocument and insert into the bucket
    */
  def insert(document: JsonDocument): Observable[JsonDocument] = {
    bucket.insert(document)
  }

  /**
    * Receive a JsonDocument and replace a previous document by this new one
    */
  def replace(document: JsonDocument): Observable[JsonDocument] = {
    bucket.replace(document)
  }


  private def setBucket(bucket: AsyncBucket) {
    this.bucket = bucket
  }
}
