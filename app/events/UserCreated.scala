package events

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import persistance.EventBase


class UserCreated @JsonCreator() (@JsonProperty("userName")var userName: String) extends EventBase{


  @JsonProperty("userName") def getUserName: String = {
    userName
  }
}
