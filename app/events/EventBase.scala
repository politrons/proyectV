package events

import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.{JsonSubTypes, JsonTypeInfo}

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes(Array(
  new Type(value = classOf[UserCreated], name = "created")))
abstract class EventBase {

//  implicit val formats = org.json4s.DefaultFormats

  def encode: String = {

//    val objectMapper = new ObjectMapper with ScalaObjectMapper
//    objectMapper.registerModule(DefaultScalaModule)
//
//    val jsonResponse = objectMapper.writeValueAsString(this)
//    val jsonResponse = Serialization.write(this)
//    jsonResponse
    ""
  }
}
