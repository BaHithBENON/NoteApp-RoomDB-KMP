package features.notes.domain.usecases

import core.interfaces.UseCase
import features.notes.domain.entities.EntityNote
import features.notes.domain.repositories.NoteRepository

class GetNoteByIdUsecase (
    private val noteRepository: NoteRepository
) : UseCase<EntityNote, GetNoteByIdUsecaseParams> {
    override suspend fun invoke(params: GetNoteByIdUsecaseParams): Result<EntityNote> =
        noteRepository.getById(id = params.id)
}

data class GetNoteByIdUsecaseParams(val id: Int)