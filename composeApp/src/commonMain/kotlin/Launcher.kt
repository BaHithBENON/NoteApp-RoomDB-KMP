import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import commons.di.appModule
import commons.navigation.AppNavHost
import core.theme.darkScheme
import core.theme.lightScheme
import database.RoomDBResources
import org.kodein.di.compose.withDI

@Composable
fun launcherContent(
    roomDBResources: RoomDBResources,
    navController: NavHostController = rememberNavController(),
    darkTheme: Boolean = isSystemInDarkTheme(),
) = withDI(
    appModule(roomDBResources, navController)
) {
    val colorScheme =
        if (!darkTheme) lightScheme
        else darkScheme
    
    MaterialTheme (
        colorScheme = colorScheme,
    ) {
        AppNavHost.handleNavigator(navController)
    }
}