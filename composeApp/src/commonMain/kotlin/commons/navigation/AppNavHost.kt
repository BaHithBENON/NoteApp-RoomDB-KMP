package commons.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.SlideTransition
import features.alerts.ui.AlertUI
import features.notes.data.datasources.NoteLocalDataSource
import features.notes.ui.NoteDetailsUI
import features.notes.ui.NotesOverviewUI
import features.notes.ui.UpdateNoteUI
import features.notes.ui.UpsertNoteUI
import features.onboard.ui.OnboardUI

class AppNavHost {
    
    companion object {
        @Composable
        fun handleNavigator(
            navController: NavHostController = rememberNavController()
        ) : Unit {
            
            Navigator(
                screen = NotesOverviewUI
            ) {navigator ->
                SlideTransition(navigator)
            }
            
            // val navController = rememberNavController()
            
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            
            NavHost(
                navController = navController,
                startDestination = AppRoutesValues.OVERVIEW_NOTES
            ) {
                // Overview Notes
                composable(
                    route = AppRoutesValues.OVERVIEW_NOTES
                ) {
                    NotesOverviewUI(
                        onDetails = { noteId ->
                            navController.navigate("${AppRoutesValues.NOTE_DETAILS}/$noteId") {
                                this.popUpToRoute
                                popUpTo(navController.graph.findStartDestination().navigatorName) {
                                    saveState = true
                                }
                            }
                        },
                        onAddNote = {
                            navController.navigate(AppRoutesValues.UPDATE_NOTE)
                        }
                    )
                }
                //  Note Details
                composable(
                    route = "${AppRoutesValues.NOTE_DETAILS}/{noteId}",
                    arguments = listOf(
                        navArgument("noteId") {
                            type = NavType.IntType
                        }
                    )
                ) {
                    val noteId = it.arguments?.getInt("noteId") ?: 0 
                    NoteDetailsUI(noteId = noteId)
                }
                // Upsert Note
                composable(
                    route = "${AppRoutesValues.UPDATE_NOTE}/{noteId}",
                    arguments = listOf(
                        navArgument("noteId") {
                            type = NavType.IntType
                        }
                    )
                ) {
                    val noteId = it.arguments?.getInt("noteId") 
                    UpsertNoteUI(noteId = noteId)
                }
                
                composable(
                    route = AppRoutesValues.UPDATE_NOTE,
                ) {
                    UpsertNoteUI(noteId = null)
                }
            }
        }
    }
}