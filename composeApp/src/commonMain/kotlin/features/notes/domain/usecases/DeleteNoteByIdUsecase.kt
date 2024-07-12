package features.notes.domain.usecases

import core.interfaces.UseCase
import features.notes.domain.entities.EntityNote
import features.notes.domain.repositories.NoteRepository

class DeleteNoteByIdUsecase(
    private val noteRepository: NoteRepository
) : UseCase<Boolean, DeleteNoteByIdUsecaseParams> {
    override suspend fun invoke(params: DeleteNoteByIdUsecaseParams): Result<Boolean> =
        noteRepository.delete(note = params.note)
}

data class DeleteNoteByIdUsecaseParams(val note: EntityNote)