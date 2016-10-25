package model.user

import scalaHydration.Model

class User(var userName: String = "",
           var password: String = "") extends Model{

  /**
    * Rehydrate method for event sourcing
    */
  def loadUserName(username: String, password:String) {
    this.userName = username
    this.password =password
  }

}


