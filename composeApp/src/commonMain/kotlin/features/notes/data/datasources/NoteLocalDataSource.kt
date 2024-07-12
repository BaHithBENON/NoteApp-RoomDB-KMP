package features.notes.data.datasources

import androidx.room.*
import core.Values
import features.notes.data.models.ModelNote
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteLocalDataSource {
    
    @Insert
    suspend fun insert(modelNote: ModelNote) : Long
    
    @Update
    suspend fun update(modelNote: ModelNote) : Int
    
    @Query("SELECT * FROM ${Values.NOTES_TABLE} WHERE id = :id LIMIT 1")
    fun getById(id: Int): Flow<ModelNote>
    
    @Query("SELECT * FROM ${Values.NOTES_TABLE} ORDER BY addedAt ASC")
    fun getAll(): Flow<List<ModelNote>>
    
    @Query("SELECT * FROM ${Values.NOTES_TABLE} ORDER BY addedAt LIMIT :n")
    fun getN(n: Int): Flow<List<ModelNote>>
    
    @Delete
    suspend fun delete(note: ModelNote)
}