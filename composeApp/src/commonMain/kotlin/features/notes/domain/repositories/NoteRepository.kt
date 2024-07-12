package features.notes.domain.repositories

import features.notes.domain.entities.EntityNote

interface NoteRepository {
    
    suspend fun insert(entityNote: EntityNote): Result<Long>
    
    suspend fun update(entityNote: EntityNote): Result<Int>
    
    suspend fun getById(id: Int): Result<EntityNote>
    
    suspend fun getAll(): Result<List<EntityNote>>
    
    suspend fun getN(n: Int): Result<List<EntityNote>>
    
    suspend fun delete(note: EntityNote): Result<Boolean>
}