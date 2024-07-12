package features.notes.ui.controllers

import features.notes.data.models.ModelNote

sealed interface NoteEvents {
    data class UpsertNote(val note: ModelNote): NoteEvents
    data class DeleteNote(val note: ModelNote): NoteEvents
    data class GetNote(val noteId: Int): NoteEvents
    object ListNotes: NoteEvents
}