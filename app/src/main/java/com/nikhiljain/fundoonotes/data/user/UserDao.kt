package com.nikhiljain.fundoonotes.data.user

import androidx.room.Dao
import androidx.room.Query
import com.nikhiljain.fundoonotes.data.base.BaseDao

@Dao
interface UserDao : BaseDao<UserEntity> {

    @Query("SELECT * FROM user_table")
    suspend fun getAllUsers(): List<UserEntity>

    @Query("SELECT * FROM user_table WHERE _id = :id")
    suspend fun getUser(id: Long)

    @Query("SELECT * FROM user_table WHERE email = :email")
    suspend fun getUser(email: String)
}