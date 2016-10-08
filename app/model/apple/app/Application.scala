package model.apple.app

import model.apple.AppleBase

/**
  * Created by pabloperezgarcia on 29/8/16.
  */
class Application(artistName: String,
                  trackName: String,
                  val artistViewUrl: String,
                  val trackViewUrl: String,
                  val price: String,
                  val artworkUrl: String
                 ) extends AppleBase(artistName, trackName) {

}

