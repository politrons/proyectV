package model.apple.music

import model.apple.AppleBase

/**
  * Created by pabloperezgarcia on 27/8/16.
  */
class VideoClip(artistName: String,
                trackName: String,
                val previewUrl: String,
                val artWorkUrl: String
               ) extends AppleBase(artistName, trackName) {}
