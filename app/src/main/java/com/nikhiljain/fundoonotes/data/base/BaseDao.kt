package com.nikhiljain.fundoonotes.data.base

import androidx.room.*

@Dao
interface BaseDao<E> {

    @Insert
    suspend fun insert(entity: E): Boolean

    @Update
    suspend fun update(entity: E): Boolean

    @Delete
    suspend fun delete(entity: E): Boolean
}