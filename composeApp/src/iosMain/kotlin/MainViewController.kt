import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import database.RoomDBResources

fun MainViewController() = ComposeUIViewController {
    val dao = remember { RoomDBResources() }
    App(roomDBResources = dao)
}