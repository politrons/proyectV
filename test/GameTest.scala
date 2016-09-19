import mocks.SteamStoreMock
import model.steam.SteamStore
import org.junit.Test

/**
  * Created by pabloperezgarcia on 18/8/16.
  */
class GameTest {

  @Test def testGame(): Unit = {
    val jsonGame = SteamStoreMock.mockGame()
    val game = SteamStore.game("60",jsonGame)
    assert(game != null)
    assert(game.name.equals("Ricochet"))


  }

}
