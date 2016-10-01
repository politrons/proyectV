package implicits

import play.api.cache.CacheApi

import scala.util.parsing.json.{JSONArray, JSONObject}

/**
  * Created by pabloperezgarcia on 27/8/16.
  */
object Utils {

  implicit class anyUtils(any: Any) {
    def asStringMap: Map[String, Any] = any.asInstanceOf[Map[String, Any]]
  }

  implicit class optionUtils(o: Option[Any]) {

    def asString: String = String.valueOf(o.get)

    def toInt: Int = o.get.asInstanceOf[Double].toInt

    def asStringMap: Map[String, Any] = o.get.asInstanceOf[Map[String, Any]]
  }

  implicit class jsonArrayUtils(jsonArray: JSONArray) {

    def first: JSONObject = new JSONObject(jsonArray.list.head.asInstanceOf[Map[String, Any]])

    def asFirstStringMap: Map[String, Any] = jsonArray.list.head.asInstanceOf[Map[String, Any]]
  }

  implicit class jsonObjectUtils(jsonObject: JSONObject) {
    def asStringMap: Map[String, Any] = jsonObject.obj
  }

  implicit class cacheUtils[T](cache: CacheApi) {

    def getVal[T](key: String): T = cache.get(key).get

    def jsonArraySize(key:String): Int = getVal[JSONArray](key).list.size

  }

}
