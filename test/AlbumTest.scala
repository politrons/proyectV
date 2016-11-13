import appleSearch.factory.AlbumFactory
import appleSearch.model.music.{Album, DiscographyF}
import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import implicits.Utils.jsonArrayUtils
import mocks.ItunesMock
import org.junit.Test
/**
  * Created by pabloperezgarcia on 18/8/16.
  */
class AlbumTest {

  @Test def mergeAlbums(): Unit = {
    val jsonArray = ItunesMock.mockMusic()
    val albums = DiscographyF.albums(jsonArray)
    val album = AlbumFactory.create(jsonArray.first)
    val mergedAlbums = DiscographyF.isAlbumMerged(album, albums)
    assert(mergedAlbums)
    assert(albums.head.songs.size == 16)
  }

  val objectMapper = new ObjectMapper with ScalaObjectMapper
  objectMapper.registerModule(DefaultScalaModule)


  @Test def deserializeAlbums(): Unit = {
    val jsonAlbums = ItunesMock.mockMusicInJsonString()
    val albums =  objectMapper.readValue(jsonAlbums, new TypeReference[List[Album]]() {})
    println(albums)
  }


}
