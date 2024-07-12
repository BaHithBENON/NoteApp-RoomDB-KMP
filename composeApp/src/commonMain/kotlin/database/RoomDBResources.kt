package database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.internal.synchronized

expect class RoomDBResources {
    fun builder(): RoomDatabase.Builder<NoteDataBase>
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<NoteDataBase>
) : NoteDataBase {
    val dispatcher: CoroutineDispatcher = Dispatchers.IO
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(dispatcher)
        .build()
}