package features.notes.domain.usecases

import core.interfaces.UseCase
import features.notes.domain.entities.EntityNote
import features.notes.domain.repositories.NoteRepository

class GetAllNoteUsecase(
    private val noteRepository: NoteRepository
) : UseCase<List<EntityNote>, Unit> {
    override suspend fun invoke(params: Unit): Result<List<EntityNote>>
        = noteRepository.getAll()
}