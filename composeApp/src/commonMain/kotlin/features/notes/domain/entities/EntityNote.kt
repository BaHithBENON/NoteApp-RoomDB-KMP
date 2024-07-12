package features.notes.domain.entities

import androidx.room.Entity

@Entity
open class EntityNote (
    open val id: Int?,
    open val title: String,
    open val description: String,
    open val content: String,
    open val addedAt: Long
) {
    companion object {
        var empty = EntityNote(id = null, title = "", content = "", description = "", addedAt = 0L)
    }
}