package implicits

import play.api.cache.CacheApi

import scala.reflect.ClassTag
import scala.util.parsing.json.{JSONArray, JSONObject}

/**
  * Created by pabloperezgarcia on 27/8/16.
  */
object Utils {


  implicit class anyUtils(any: Any) {
    def asStringMap: Map[String, Any] = any.asInstanceOf[Map[String, Any]]
  }

  implicit class jsonArrayUtils(jsonArray: JSONArray) {
    def firstJson: JSONObject = new JSONObject(jsonArray.list.head.asInstanceOf[Map[String, Any]])
  }

  implicit class optionUtils(o: Option[Any]) {
    def asString: String = String.valueOf(o.get)
  }

  implicit class cacheUtils[T:ClassTag](cache:CacheApi){
    def getVal(key:String): T = cache.get(key).get
  }

}
