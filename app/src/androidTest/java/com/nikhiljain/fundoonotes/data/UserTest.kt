package com.nikhiljain.fundoonotes.data

import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nikhiljain.fundoonotes.data.user.UserDao
import com.nikhiljain.fundoonotes.data.user.UserEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserTest {
    private lateinit var testDatabase: AppDatabase
    private lateinit var userDao: UserDao

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        testDatabase = Room.inMemoryDatabaseBuilder(
            appContext,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        userDao = testDatabase.userDao
    }

    @Throws(Exception::class)
    @Test
    fun shouldAddUser() {
        val user = UserEntity(
            firstName = "Nikhil", lastName = "Jain",
            email = "nikhiljain@gmail.com", password = "1234567890",
            mobileNumber = "8871152221"
        )
        runBlocking {
            val userId = userDao.insert(user)
            val expectedUser = user.copy(userId)
            val insertedUser = userDao.getUser(userId)
            Assert.assertEquals(expectedUser, insertedUser)
        }
    }

    @Test(expected = SQLiteConstraintException::class)
    fun shouldNotAddUsersWithSameEmail() {
        val firstUser = UserEntity(
            firstName = "Nikhil", lastName = "Jain",
            email = "nikhil@gmail.com", password = "1234567890",
            mobileNumber = "2676276737"
        )
        val secondUser = UserEntity(
            firstName = "Nikhil", lastName = "Singh",
            email = "nikhil@gmail.com", password = "2752372155",
            mobileNumber = "9456738944"
        )
        runBlocking {
            userDao.insert(firstUser)
            userDao.insert(secondUser)
        }
    }

    @Throws(Exception::class)
    @Test
    fun shouldUpdateUser() {
        val user = UserEntity(
            firstName = "Nikhil", lastName = "Jain",
            email = "nikhil@gmail.com", password = "1234567890",
            mobileNumber = "0123456789"
        )
        runBlocking {
            val userId = userDao.insert(user)
            val insertedUser = userDao.getUser(userId)
            Assert.assertNotNull(insertedUser)

            val userToUpdate = insertedUser!!.copy(
                firstName = "Kamal", lastName = "Panjwani",
                password = "0123456789", mobileNumber = "1234567890",
                email = "kamal@gmail.com"
            )
            userDao.updateWithModifiedDate(userToUpdate)
            val updatedUser = userDao.getUser(insertedUser.id)
            Assert.assertEquals(userToUpdate, updatedUser)
        }
    }

    @Throws(Exception::class)
    @Test
    fun shouldDeleteUser() {
        val user = UserEntity(
            firstName = "Nikhil", lastName = "Jain",
            email = "nikhil@gmail.com", password = "1234567890",
            mobileNumber = "0123456789"
        )
        runBlocking {
            val sizeBeforeInsertion = userDao.getAllUsers().size
            val userId = userDao.insert(user)
            val insertedUser = userDao.getUser(userId)
            val sizeAfterInsertion = userDao.getAllUsers().size
            Assert.assertNotNull(insertedUser)
            Assert.assertEquals(sizeAfterInsertion, sizeBeforeInsertion + 1)

            userDao.delete(insertedUser!!)
            val sizeAfterDeletion = userDao.getAllUsers().size
            Assert.assertNull(userDao.getUser(userId))
            Assert.assertEquals(sizeAfterDeletion, sizeAfterInsertion - 1)
        }
    }

    @Throws(Exception::class)
    @Test
    fun shouldGetAllUsers() {
        val userOne = UserEntity(
            firstName = "Nikhil", lastName = "Jain",
            email = "nikhil@gmail.com", password = "1234567890",
            mobileNumber = "0123456789"
        )
        val userTwo = UserEntity(
            firstName = "Ajay", lastName = "Singh",
            email = "ajay@gmail.com", password = "65367346756",
            mobileNumber = "8387682462"
        )
        val userThree = UserEntity(
            firstName = "Aditya", lastName = "Singh",
            email = "aditya@gmail.com", password = "2868236663",
            mobileNumber = "9083786343"
        )
        runBlocking {
            userDao.insert(userOne)
            userDao.insert(userTwo)
            userDao.insert(userThree)

            val expected = 3
            val sizeAfterInsertions = userDao.getAllUsers().size
            Assert.assertEquals(expected, sizeAfterInsertions)
        }
    }

    @After
    fun tearDown() {
        testDatabase.clearAllTables()
        testDatabase.close()
    }
}