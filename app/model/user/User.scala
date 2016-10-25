package model.user

import persistance.Model

class User(var userName: String = "") extends Model{

  /**
    * Rehydrate method for event sourcing
    */
  def loadUserName(username: String) {
    this.userName = username
  }

}


