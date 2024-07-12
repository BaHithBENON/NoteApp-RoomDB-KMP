package database

import androidx.room.RoomDatabase

actual class RoomDBResources {
    actual fun builder(): RoomDatabase.Builder<NoteDataBase> {
        TODO("Not yet implemented")
        /*
        val dbFilePath = NSHomeDirectory() + "/${Values.ROOM_DB_NAME}"
        return Room.databaseBuilder<NoteDataBase>(
            name = dbFilePath,
            factory = { NoteDataBase::class.instantiateImpl() }
        ).setDriver(BundledSQLiteDriver())
         */
    }
}