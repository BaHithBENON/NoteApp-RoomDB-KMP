package features.notes.data.repositories

import features.notes.data.datasources.NoteLocalDataSource
import features.notes.data.models.ModelNote
import features.notes.domain.entities.EntityNote
import features.notes.domain.repositories.NoteRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl (
    private val noteLocalDataSource: NoteLocalDataSource
) : NoteRepository {
    override suspend fun insert(entityNote: EntityNote): Result<Long> {
        try {
            val result: Long = noteLocalDataSource.insert(modelNote = ModelNote.fromEntity(entityNote))
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun update(entityNote: EntityNote): Result<Int> {
        return runCatching {
            noteLocalDataSource.update(modelNote = ModelNote.fromEntity(entityNote))
        }
    }

    override suspend fun getById(id: Int): Result<EntityNote> =
        runCatching {
            noteLocalDataSource.getById(id)
                .first()
                .toEntity()
    }

    override suspend fun getAll(): Result<List<EntityNote>> {
        return runCatching {
            val entityList = mutableListOf<EntityNote>()
            val e = noteLocalDataSource.getAll()
                .map {it-> it.map { el -> el.toEntity() } }
                .first()
            entityList.addAll(e)
            println("All ----> ${entityList.count()}")
            entityList
        }
    }

    override suspend fun getN(n: Int): Result<List<EntityNote>> {
        return runCatching {
            val entityList = mutableListOf<EntityNote>()
            val e = noteLocalDataSource.getN(n = n)
                .map {it-> it.map { el -> el.toEntity() } }
                .first()
            entityList.addAll(e)
            println("All N ----> ${entityList.count()}")
            entityList
        }
    }

    override suspend fun delete(note: EntityNote): Result<Boolean> =
        runCatching {
            noteLocalDataSource.delete(note = ModelNote.fromEntity(note))
            return Result.success(true)
    }

}