import mocks.ItunesMock
import appleSearch.model.music.DiscographyF
import org.junit.Test


/**
  * Created by pabloperezgarcia on 18/8/16.
  */
class DiscographyTest {


  @Test def createDiscography(): Unit = {
    val jsonArray = ItunesMock.mockMusic()
    val albums = DiscographyF.albums(jsonArray)
    assert(albums.size == 49)
    assert(albums.head.songs.size == 1)
  }

  @Test def createDiscographyWithVideos(): Unit = {
    val arrayMusic = ItunesMock.mockMusic()
    val albums = DiscographyF.albums(arrayMusic)
    val arrayVideo = ItunesMock.mockVideos()
    DiscographyF.attachVideos(arrayVideo, albums)
    assert(albums.head.songs.last.videoClip != null)
  }

}
