package features.notes.ui.controllers

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import features.notes.data.datasources.NoteLocalDataSource
import features.notes.data.models.ModelNote
import features.notes.domain.usecases.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NoteViewModel(
    private val localDataSource: NoteLocalDataSource,
    private val insertNoteUsecase: InsertNoteUsecase,
    private val updateNoteUsecase: UpdateNoteUsecase,
    private val getAllNoteUsecase: GetAllNoteUsecase,
    private val getNoteByIdUsecase: GetNoteByIdUsecase,
    private val getNNoteUsecase: GetNNoteUsecase,
    private val deleteNoteByIdUsecase: DeleteNoteByIdUsecase
) : ScreenModel {
    
    private val _uiState = MutableStateFlow(NoteStates())
    val uiState: StateFlow<NoteStates> = _uiState.asStateFlow()
    
    private val _upsertUiState = MutableStateFlow(UpsertNoteStates())
    val upsertUiState: StateFlow<UpsertNoteStates> = _upsertUiState.asStateFlow()
    
    private val _detailsNoteUiState = MutableStateFlow(GetNoteStates())
    val detailsNoteUiState: StateFlow<GetNoteStates> = _detailsNoteUiState.asStateFlow()
    
    init {
        onUiEvent(NoteEvents.ListNotes)
    }
    
    fun onUiEvent(noteEvents: NoteEvents) {
        when(noteEvents) {
            is NoteEvents.ListNotes -> getAllNotes(event = noteEvents)
            is NoteEvents.UpsertNote -> upsertNote(event = noteEvents)
            is NoteEvents.DeleteNote -> deleteNote(event = noteEvents)
            is NoteEvents.GetNote -> getNoteByIdNote(event = noteEvents)
            else -> getAllNotes(event = NoteEvents.ListNotes)
        }
    }
    
    private fun upsertNote(event: NoteEvents.UpsertNote) {
        if(_upsertUiState.value.isLoading) return;
        
        _upsertUiState.update {
            it.copy(isLoading = true)
        }
        screenModelScope.launch {
            val note = event.note;
            if(note.id == null) {
                insertNoteUsecase.invoke(InsertNoteUsecaseParams(note))
                    .onSuccess { it ->
                        println("Insertion : (Success $it)")
                        _upsertUiState.update {
                            it.copy(isLoading = false, isFailure = false, isSuccess = true, note = it.note)
                        }
                    }
                    .onFailure {it ->
                        println("Insertion : (failure $it)")
                        _upsertUiState.update {
                            it.copy(isLoading = false, isFailure = true, isSuccess = false, note = it.note)
                        }
                    }
            } else {
                updateNoteUsecase.invoke(UpdateNoteUsecaseParams(note))
                    .onSuccess { it ->
                        println("Update : (Success $it)")
                        _upsertUiState.update {
                            it.copy(isLoading = false, isFailure = false, isSuccess = true, note = it.note)
                        }
                    }
                    .onFailure {it ->
                        println("Update : (failure $it)")
                        _upsertUiState.update {
                            it.copy(isLoading = false, isFailure = true, isSuccess = false, note = it.note)
                        }
                    }
            }
            onUiEvent(NoteEvents.ListNotes)
        }
    }
   
    private fun getAllNotes(event: NoteEvents.ListNotes) {
        if(_uiState.value.isLoading) return;
        _uiState.update { NoteStates() }
        screenModelScope.launch {
            getAllNoteUsecase.invoke(Unit)
                .onSuccess {entities ->
                    println("List : (Sucess ${entities.size} elements)")
                    _uiState.update {
                        val allNotes : List<ModelNote>  =  entities.map { entityNote -> ModelNote.fromEntity(entityNote) };
                        val currentNotes = it.notes // Récupérez les notes actuelles de l'état
                        
                        // Filtrer les notes actuelles qui ne sont plus présentes dans la nouvelle liste
                        val notesToRemove = currentNotes.filterNot { currentNote ->
                            allNotes.any { newNote -> newNote.id == currentNote.id }
                        }
                        
                        // Filtrer les nouvelles notes qui n'existent pas encore dans l'état actuel
                        val newNotesToBeAdded = allNotes.filter { newNote ->
                            !currentNotes.any { currentNote -> currentNote.id == newNote.id }
                        }
                        
                        // Mettre à jour l'état avec les notes restantes et les nouvelles notes ajoutées
                        it.copy(
                            isLoading = false,
                            isFailure = false,
                            isSuccess = true,
                            notes = currentNotes - notesToRemove + newNotesToBeAdded
                        )
                    }
                }
                .onFailure {
                    println("List retreiving failed")
                    _uiState.update { 
                        it.copy(isLoading = false, isFailure = true, isSuccess = false)
                    }
                }
            println("Debut List finished...")
        }
    }
    
    private fun deleteNote(event: NoteEvents.DeleteNote) {
        if(_uiState.value.isLoading) return;
        _uiState.update {
            it.copy(isLoading = true)
            NoteStates()
        }
        
        screenModelScope.launch {
            val note = event.note;
            deleteNoteByIdUsecase.invoke(DeleteNoteByIdUsecaseParams(note))
                .onSuccess {it ->
                    println("Deletion : (Sucess $it)")
                    _uiState.update {
                        it.copy(isLoading = false, isFailure = false, isSuccess = true)
                        NoteStates()
                    }
                }
                .onFailure { it ->
                    println("Deletion : (Failure $it)")
                    _uiState.update { 
                        it.copy(isLoading = false, isFailure = true, isSuccess = false)
                        NoteStates()
                    }
                }
            onUiEvent(NoteEvents.ListNotes)
            println("Debut List finished...")
        }
    }
    
    fun getNoteByIdNote(event: NoteEvents.GetNote) {
        if(_detailsNoteUiState.value.isLoading) return;
        _detailsNoteUiState.update {
            it.copy(isLoading = true)
            GetNoteStates()
        }
        
        screenModelScope.launch {
            val noteId = event.noteId;
            getNoteByIdUsecase.invoke(GetNoteByIdUsecaseParams(noteId))
                .onSuccess {entityNote ->
                    println("Getting Note : (Sucess $entityNote)")
                    println("Title => '${entityNote.title}'")
                    // Mettre à jour l'état de la note dans le ViewModel
                    _detailsNoteUiState.value = GetNoteStates(
                        note = ModelNote.fromEntity(entityNote),
                        noteId = entityNote.id,
                        isLoading = false,
                        isSuccess = true,
                        isFailure = false
                    )
                }
                .onFailure { it ->
                    println("Getting Note : (Failure $it)")
                    _detailsNoteUiState.update { 
                        it.copy(isLoading = false, isFailure = true, isSuccess = false)
                        GetNoteStates()
                    }
                }
            println("Get Note finished...")
        }
    }
}