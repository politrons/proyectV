package scalaHydration

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

@JsonTypeInfo(
  use = JsonTypeInfo.Id.CLASS,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
abstract class EventBase {

  def encode: String = {

    val objectMapper = new ObjectMapper with ScalaObjectMapper
    objectMapper.registerModule(DefaultScalaModule)

    val jsonResponse = objectMapper.writeValueAsString(this)
    jsonResponse
  }
}
