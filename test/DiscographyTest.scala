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
  }


}
