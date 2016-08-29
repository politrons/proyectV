import mocks.Itunes
import model.Discography
import org.junit.Test


/**
  * Created by pabloperezgarcia on 18/8/16.
  */
class DiscographyTest {


  @Test def createDiscography(): Unit = {
    val jsonArray = Itunes.mockMusic()
    val albums = Discography.albums(jsonArray)
    assert(albums.size == 49)
    assert(albums.head.songs.size == 1)
  }

  @Test def createDiscographyWithVideos(): Unit = {
    val arrayMusic = Itunes.mockMusic()
    val albums = Discography.albums(arrayMusic)
    val arrayVideo = Itunes.mockVideos()
    Discography.attachVideos(arrayVideo, albums)
    assert(albums.head.songs.last.videoClip != null)
  }

}
