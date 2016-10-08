import factories.AlbumFactory
import implicits.Utils.jsonArrayUtils
import mocks.ItunesMock
import model.apple.music.Discography
import org.junit.Test
/**
  * Created by pabloperezgarcia on 18/8/16.
  */
class AlbumTest {

  @Test def mergeAlbums(): Unit = {
    val jsonArray = ItunesMock.mockMusic()
    val albums = Discography.albums(jsonArray)
    val album = AlbumFactory.create(jsonArray.first)
    val mergedAlbums = Discography.isAlbumMerged(album, albums)
    assert(mergedAlbums)
    assert(albums.head.songs.size == 16)


  }

}
