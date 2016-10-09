package model.apple.music

import model.apple.AppleBase

/**
  * Created by pabloperezgarcia on 27/8/16.
  */
class Song(trackName: String,
           val previewUrl: String,
           val trackPrice: String,
           val trackViewUrl: String
          ) extends AppleBase(null, trackName) {

  var videoClip: VideoClip = null

  def addVideoClip(videoClip: VideoClip): Song = {
    this.videoClip = videoClip
    this
  }

}
