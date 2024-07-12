package features.notes.domain.usecases

import core.interfaces.UseCase
import features.notes.domain.entities.EntityNote
import features.notes.domain.repositories.NoteRepository

class GetNNoteUsecase(
    private val noteRepository: NoteRepository
) : UseCase<List<EntityNote>, GetNNoteUsecaseParams> {
    override suspend fun invoke(params: GetNNoteUsecaseParams): Result<List<EntityNote>>
        = noteRepository.getN(n = params.n)
}

data class GetNNoteUsecaseParams(val n: Int)