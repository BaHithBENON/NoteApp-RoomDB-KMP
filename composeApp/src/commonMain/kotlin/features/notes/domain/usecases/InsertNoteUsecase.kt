package features.notes.domain.usecases

import core.interfaces.UseCase
import features.notes.domain.entities.EntityNote
import features.notes.domain.repositories.NoteRepository

class InsertNoteUsecase (
    private val noteRepository: NoteRepository
) : UseCase<Long, InsertNoteUsecaseParams> {
    override suspend fun invoke(params: InsertNoteUsecaseParams): Result<Long> {
        return noteRepository.insert(entityNote = params.entityNote)
    }
}

data class InsertNoteUsecaseParams(val entityNote: EntityNote) 