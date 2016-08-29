package model.music

/**
  * Created by pabloperezgarcia on 27/8/16.
  */
class Song(val trackName: String,
           val previewUrl: String,
           val trackPrice: String,
           val trackViewUrl: String
          ) {

  var videoClip: VideoClip = null

  def addVideoClip(videoClip: VideoClip) {
    this.videoClip = videoClip
  }

}
