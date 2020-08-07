package com.nikhiljain.fundoonotes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nikhiljain.fundoonotes.data.note.NoteDao
import com.nikhiljain.fundoonotes.data.note.NoteEntity
import com.nikhiljain.fundoonotes.data.user.UserDao
import com.nikhiljain.fundoonotes.data.user.UserEntity

@Database(
    entities = [UserEntity::class, NoteEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val userDao: UserDao

    abstract val noteDao: NoteDao

    companion object {
        private const val DATABASE_NAME = "AppDatabase.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context, AppDatabase::class.java,
                        DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}