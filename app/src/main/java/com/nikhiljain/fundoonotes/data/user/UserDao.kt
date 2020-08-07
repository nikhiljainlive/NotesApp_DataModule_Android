package com.nikhiljain.fundoonotes.data.user

import androidx.room.Dao
import androidx.room.Query
import com.nikhiljain.fundoonotes.data.base.BaseDao
import java.util.*

@Dao
abstract class UserDao : BaseDao<UserEntity> {

    suspend fun updateWithModifiedDate(user: UserEntity) {
        update(user.apply {
            modifiedAt = Date()
        })
    }

    @Query("SELECT * FROM user_table ORDER BY datetime(created_at) DESC")
    abstract suspend fun getAllUsers(): List<UserEntity>

    @Query("SELECT * FROM user_table WHERE _id = :id")
    abstract suspend fun getUser(id: Long): UserEntity?

    @Query("SELECT * FROM user_table WHERE email = :email")
    abstract suspend fun getUser(email: String): UserEntity?
}