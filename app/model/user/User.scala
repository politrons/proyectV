package model.user

class User(var userName: String = "") {

  /**
    * Rehydrate method for event sourcing
    */
  def loadUserName(username: String) {
    this.userName = username
  }

}


