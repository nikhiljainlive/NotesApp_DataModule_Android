package com.nikhiljain.fundoonotes.data.note

import androidx.room.*
import com.nikhiljain.fundoonotes.data.user.UserEntity
import java.util.*

@Entity(
    tableName = "note_table",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["_id"],
        childColumns = ["user_id"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("user_id")]
)
data class NoteEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val noteId: Long = 0L,

    @ColumnInfo(name = "note_title")
    var title: String?,

    @ColumnInfo(name = "note_description")
    var description: String?,

    @ColumnInfo(name = "created_at")
    val createdAt: Date = Date(),

    @ColumnInfo(name = "modified_at")
    var modifiedAt: Date = createdAt,

    @ColumnInfo(name = "reminder")
    var reminder: Date?,

    @ColumnInfo(name = "is_pinned")
    var isPinned: Boolean = false,

    @ColumnInfo(name = "is_archived")
    var isArchived: Boolean = false,

    @ColumnInfo(name = "is_trashed")
    var isTrashed: Boolean = false,

    @ColumnInfo(name = "user_id")
    var userId: Long
)