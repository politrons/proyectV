import mocks.Itunes
import model.Discography
import org.junit.Test


/**
  * Created by pabloperezgarcia on 18/8/16.
  */
class DiscographyTest {


  @Test def createDiscography(): Unit = {
    val jsonArray = Itunes.mockItunes()
    val albums = Discography.create(jsonArray)
    assert(albums.size == 49)
    assert(albums.head.trackNames.size == 1)
    assert(albums.head.previewUrls.size == 1)
  }

}
