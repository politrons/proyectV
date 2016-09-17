import factories.MovieFactory
import implicits.Utils.jsonArrayUtils
import mocks.AppleTvMock
import model.apple.app.AppleStore
import org.junit.Test

/**
  * Created by pabloperezgarcia on 18/8/16.
  */
class MovieTest {

  @Test def testMovie(): Unit = {
    val jsonArray = AppleTvMock.mockApps()
    val movies = AppleStore.applications(jsonArray)
    val movie = MovieFactory.create(jsonArray.firstJson)
    assert(movie != null)
    assert(movies.head.artistName.equals("Top Free Games"))


  }

}
