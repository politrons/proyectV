import appleSearch.factory.ApplicationFactory
import implicits.Utils.jsonArrayUtils
import mocks.AppleStoreMock
import appleSearch.model.app.AppleStore
import org.junit.Test

/**
  * Created by pabloperezgarcia on 18/8/16.
  */
class ApplicationTest {

  @Test def testApplication(): Unit = {
    val jsonArray = AppleStoreMock.mockApps()
    val apps = AppleStore.applications(jsonArray)
    val app = ApplicationFactory.create(jsonArray.first)
    assert(app != null)
    assert(apps.head.artistName.equals("Top Free Games"))


  }

}
