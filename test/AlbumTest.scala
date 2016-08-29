import factories.AlbumFactory
import implicits.Utils.jsonArrayUtils
import mocks.Itunes
import model.music.Discography
import org.junit.Test
/**
  * Created by pabloperezgarcia on 18/8/16.
  */
class AlbumTest {

  @Test def mergeAlbums(): Unit = {
    val jsonArray = Itunes.mockMusic()
    val albums = Discography.albums(jsonArray)
    val album = AlbumFactory.create(jsonArray.firstJson)
    val mergedAlbums = Discography.mergeAlbum(album, albums)
    assert(mergedAlbums)
    assert(albums.head.songs.size == 16)


  }

}
