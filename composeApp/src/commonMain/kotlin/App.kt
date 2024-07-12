import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import commons.di.appModule
import commons.navigation.AppNavHost
import database.RoomDBResources
import database.getRoomDatabase
import features.notes.data.datasources.NoteLocalDataSource
import features.notes.ui.NotesOverviewUI
import features.onboard.ui.OnboardUI
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import noteapproomdb.composeapp.generated.resources.Res
import noteapproomdb.composeapp.generated.resources.compose_multiplatform
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bind
import org.kodein.di.compose.withDI
import org.kodein.di.singleton

@Composable
@Preview
fun App(roomDBResources: RoomDBResources) {
    
    // val noteLocalDataSource =  DataSourceSingleton.getInstance(roomDBResources)
    
    /*
    val di by DI.lazy {
        DI.Module() {
            bind<RoomDBResources>() with singleton {
                roomDBResources
            }
        }
    }
    */
    
    println("Instance de Room : ${roomDBResources?: null}")
    
    launcherContent(roomDBResources)
}
