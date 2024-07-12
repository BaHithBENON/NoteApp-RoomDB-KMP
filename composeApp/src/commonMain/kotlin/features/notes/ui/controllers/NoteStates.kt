package features.notes.ui.controllers

import features.notes.data.models.ModelNote

data class NoteStates (
    val notes: List<ModelNote> = emptyList(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isFailure: Boolean = false,
)

data class UpsertNoteStates (
    val note: ModelNote = ModelNote.empty,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isFailure: Boolean = false,
)

data class GetNoteStates (
    val note: ModelNote = ModelNote.empty,
    val noteId: Int? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isFailure: Boolean = false,
)