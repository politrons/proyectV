package implicits

import scala.util.parsing.json.{JSONObject, JSONArray}

/**
  * Created by pabloperezgarcia on 27/8/16.
  */
object Utils {

  implicit class anyUtils(any: Any) {
    def asStringMap: Map[String, Any] = any.asInstanceOf[Map[String, Any]]
  }

  implicit class jsonArrayUtils(jsonArray: JSONArray) {
    def asJson: JSONObject = new JSONObject(jsonArray.asInstanceOf[Map[String, Any]])
  }

  implicit class optionUtils(o: Option[Any]) {
    def asString: String = String.valueOf(o.get)
  }

}
