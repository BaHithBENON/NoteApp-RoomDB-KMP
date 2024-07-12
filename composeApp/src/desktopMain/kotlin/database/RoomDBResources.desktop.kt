package database

import androidx.room.Room
import androidx.room.RoomDatabase
import core.Values
import java.io.File

actual class RoomDBResources {
    actual fun builder(): RoomDatabase.Builder<NoteDataBase> {
        val dbFile = File(System.getProperty("java.io.tmpdir"), Values.ROOM_DB_NAME)
        return Room.databaseBuilder<NoteDataBase>(
            name = dbFile.absolutePath,
        )
    }
}