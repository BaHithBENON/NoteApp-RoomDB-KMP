package features.notes.domain.usecases

import core.interfaces.UseCase
import features.notes.domain.entities.EntityNote
import features.notes.domain.repositories.NoteRepository

class UpdateNoteUsecase (
    private val noteRepository: NoteRepository
) : UseCase<Int, UpdateNoteUsecaseParams> {
    override suspend fun invoke(params: UpdateNoteUsecaseParams): Result<Int> {
        return noteRepository.update(entityNote = params.entityNote)
    }
}

data class UpdateNoteUsecaseParams(val entityNote: EntityNote)