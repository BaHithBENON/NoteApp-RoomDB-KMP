package commons.navigation

import androidx.navigation.NavArgument
import cafe.adriel.voyager.core.screen.Screen
import features.alerts.ui.AlertUI
import features.notes.ui.NoteDetailsUI
import features.notes.ui.NotesOverviewUI
import features.notes.ui.UpdateNoteUI
import features.onboard.ui.OnboardUI

class AppRoutesValues {
    companion object {
        const val ONBOARD : String = "onboard"
        const val OVERVIEW_NOTES : String = "overview_notes"
        const val UPDATE_NOTE : String = "update_note"
        const val NOTE_DETAILS : String = "note_details"
        const val ALERT : String = "alert"
        
        val AlertUi : Screen = AlertUI
        val OnboardUi : Screen = OnboardUI
        val OverviewUi : Screen = NotesOverviewUI
        val UpdateNoteUi : Screen = UpdateNoteUI
        val NoteDetailsUi : Screen = NoteDetailsUI
        
        fun to(routeValue: AppRoutes, argument: NavArgument) : Screen {
            return when(routeValue) {
                AppRoutes.OnboardUI -> OnboardUi
                AppRoutes.OverviewUI -> OverviewUi
                AppRoutes.UpdateNoteUI -> UpdateNoteUi
                AppRoutes.NoteDetailsUI -> NoteDetailsUi
                else -> AlertUI
            }
        }
    }
}
enum class AppRoutes(title: String) {
    OnboardUI(title = AppRoutesValues.ONBOARD),
    OverviewUI(title = AppRoutesValues.OVERVIEW_NOTES),
    UpdateNoteUI(title = AppRoutesValues.UPDATE_NOTE),
    NoteDetailsUI(title = AppRoutesValues.NOTE_DETAILS),
    AlertUI(title = AppRoutesValues.ALERT)
}