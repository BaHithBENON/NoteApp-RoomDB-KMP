package features.notes.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import core.Values
import features.notes.domain.entities.EntityNote

@Entity(tableName = Values.NOTES_TABLE)
data class ModelNote(

    @PrimaryKey(autoGenerate = true)
    override val id: Int? = 0,

    @ColumnInfo(name = "title")
    override val title: String,
    
    @ColumnInfo(name = "description")
    override val description: String,

    @ColumnInfo(name = "content")
    override val content: String,

    @ColumnInfo(name = "addedAt")
    override val addedAt: Long
)   : EntityNote(id, title, content, description, addedAt) {
    
    fun toEntity() : EntityNote =  
        EntityNote(id = id, title = title, content = content, description=description, addedAt = addedAt)
    
    companion object {
        fun fromEntity(note : EntityNote) : ModelNote =
            ModelNote(id = note.id, title = note.title, content = note.content,
                      description = note.description, addedAt = note.addedAt)
        
        var empty = ModelNote(id = null, title = "", content = "", description = "", addedAt = 0L)
    }
    
}