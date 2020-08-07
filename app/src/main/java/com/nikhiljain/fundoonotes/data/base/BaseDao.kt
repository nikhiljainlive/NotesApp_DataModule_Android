package com.nikhiljain.fundoonotes.data.base

import androidx.room.*

@Dao
interface BaseDao<E> {

    @Insert
    suspend fun insert(entity: E): Long

    @Update
    suspend fun update(entity: E)

    @Delete
    suspend fun delete(entity: E)
}