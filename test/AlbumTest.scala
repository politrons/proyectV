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
    val album = AlbumFactory.create(jsonArray.firstJson)
    val mergedAlbums = Discography.mergeAlbum(album, albums)
    assert(mergedAlbums)
    assert(albums.head.trackNames.size == 16)


  }

}
