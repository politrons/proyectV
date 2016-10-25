package model.user

class User(var userName: String = "",
           var password: String = "") {

  /**
    * Rehydrate method for event sourcing
    */
  def loadUserName(username: String, password:String) {
    this.userName = username
    this.password =password
  }

}


