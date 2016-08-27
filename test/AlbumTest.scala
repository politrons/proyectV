import implicits.Utils.jsonArrayUtils
import mocks.Itunes
import model.{AlbumFactory, Discography}
import org.junit.Test
/**
  * Created by pabloperezgarcia on 18/8/16.
  */
class AlbumTest {

  @Test def mergeAlbums(): Unit = {
    val jsonArray = Itunes.mockItunes()
    val albums = Discography.create(jsonArray)
    val album = AlbumFactory.create(jsonArray.asJson)
    val mergedAlbums = Discography.mergeAlbums(album, albums)
    assert(mergedAlbums.head.trackNames.size == 2)

  }

}
