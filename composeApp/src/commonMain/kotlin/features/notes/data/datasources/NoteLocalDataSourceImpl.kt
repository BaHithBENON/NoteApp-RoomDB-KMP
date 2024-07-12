package features.notes.data.datasources

import features.notes.data.models.ModelNote
import kotlinx.coroutines.flow.Flow

class NoteLocalDataSourceImpl(private val noteDao: NoteLocalDataSource) : NoteLocalDataSource {
    
    override suspend fun insert(modelNote: ModelNote): Long {
        try {
            val result : Long = noteDao.insert(modelNote = modelNote);
            return result;
        } catch (e: Exception) {
            throw Exception(e)
        }
    }
    
    override suspend fun update(modelNote: ModelNote): Int {
        try {
            val result : Int = noteDao.update(modelNote = modelNote);
            return result;
        } catch (e: Exception) {
            throw Exception(e)
        }
    }
    
    override fun getById(id: Int): Flow<ModelNote> {
        try {
            return noteDao.getById(id = id);
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    override fun getAll(): Flow<List<ModelNote>> {
        try {
            return noteDao.getAll();
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    override fun getN(n: Int): Flow<List<ModelNote>> {
        try {
            return noteDao.getN(n = n);
        } catch (e: Exception) {
            throw Exception(e)
        }
    }
    
    override suspend fun delete(note: ModelNote): Unit {
        try {
            noteDao.delete(note);
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

}