package com.nikhiljain.fundoonotes.data.note

import androidx.room.Dao
import androidx.room.Query
import com.nikhiljain.fundoonotes.data.base.BaseDao
import java.util.*

@Dao
abstract class NoteDao : BaseDao<NoteEntity> {

    suspend fun updateWithModifiedDate(note: NoteEntity) {
        update(note.apply {
            modifiedAt = Date()
        })
    }

    @Query(
        "SELECT * FROM note_table WHERE user_id = :userId " +
                "AND is_pinned = 0 AND is_archived = 0 AND is_trashed = 0 " +
                "ORDER BY datetime(created_at) DESC"
    )
    abstract suspend fun getNotes(userId: Long): List<NoteEntity>

    @Query(
        "SELECT * FROM note_table WHERE user_id = :userId " +
                "AND reminder IS NOT NULL AND reminder != '' " +
                "AND is_trashed = 0 ORDER BY datetime(created_at) DESC"
    )
    abstract suspend fun getReminders(userId: Long): List<NoteEntity>

    @Query(
        "SELECT * FROM note_table WHERE user_id = :userId " +
                "AND is_pinned = 1 AND is_archived = 0 " +
                "AND is_trashed = 0 ORDER BY datetime(created_at) DESC"
    )
    abstract suspend fun getPinnedNotes(userId: Long): List<NoteEntity>

    @Query(
        "SELECT * FROM note_table WHERE user_id = :userId " +
                "AND is_pinned = 0 AND is_archived = 1 " +
                "AND is_trashed = 0 ORDER BY datetime(created_at) DESC"
    )
    abstract suspend fun getArchivedNotes(userId: Long): List<NoteEntity>

    @Query(
        "SELECT * FROM note_table WHERE user_id = :userId " +
                "AND is_trashed = 1 ORDER BY datetime(created_at) DESC"
    )
    abstract suspend fun getTrashedNotes(userId: Long): List<NoteEntity>

    @Query("SELECT * FROM note_table WHERE _id = :noteId AND user_id = :userId")
    abstract suspend fun getNote(noteId: Long, userId: Long): NoteEntity?
}