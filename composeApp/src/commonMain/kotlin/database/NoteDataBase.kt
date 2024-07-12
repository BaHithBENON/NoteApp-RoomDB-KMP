package database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import features.notes.data.datasources.NoteLocalDataSource
import features.notes.data.models.ModelNote
import kotlinx.coroutines.*
import kotlinx.coroutines.internal.synchronized
import kotlin.concurrent.Volatile

@Database(
    entities = [
        ModelNote::class
    ],
    version = 1
)
abstract class NoteDataBase : RoomDatabase () {
    abstract fun noteDao(): NoteLocalDataSource
    
    companion object {
        
        @Volatile
        private var dbInstance : NoteDataBase? = null
        
        fun getRoomDatabase(
            builder: RoomDatabase.Builder<NoteDataBase>
        ) : NoteDataBase {
            val dispatcher: CoroutineDispatcher = Dispatchers.IO

            return dbInstance ?: 
                builder
                    .setDriver(BundledSQLiteDriver())
                    .setQueryCoroutineContext(dispatcher)
                    .build()
                    .also { dbInstance = it }
        }
        
    }
}