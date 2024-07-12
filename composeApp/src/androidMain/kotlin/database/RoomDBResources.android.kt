package database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import core.Values

actual class RoomDBResources(
    private val appContext: Context
) {
    actual fun builder(): RoomDatabase.Builder<NoteDataBase> {
        val appContext = appContext.applicationContext
        val dbFile = appContext.getDatabasePath(Values.ROOM_DB_NAME)

        return Room.databaseBuilder<NoteDataBase>(
            context = appContext,
            name = dbFile.absolutePath
        ).setDriver(BundledSQLiteDriver())
    }
}