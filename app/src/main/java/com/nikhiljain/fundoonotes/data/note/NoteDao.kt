package com.nikhiljain.fundoonotes.data.note

import androidx.room.Dao
import androidx.room.Query
import com.nikhiljain.fundoonotes.data.base.BaseDao

@Dao
interface NoteDao : BaseDao<NoteEntity> {

    @Query("SELECT * FROM note_table")
    suspend fun getAllNotes(): List<NoteEntity>


}