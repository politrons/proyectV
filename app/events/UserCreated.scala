package events

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}


class UserCreated @JsonCreator() (@JsonProperty("userName")var userName: String,
                                  @JsonProperty("password")var password: String) extends EventBase{


  @JsonProperty("userName") def getUserName: String = {
    userName
  }

  @JsonProperty("password") def getPassword: String = {
    password
  }
}
