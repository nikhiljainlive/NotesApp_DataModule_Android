package com.nikhiljain.fundoonotes.data

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.nikhiljain.fundoonotes.data.note.NoteDao
import com.nikhiljain.fundoonotes.data.note.NoteEntity
import com.nikhiljain.fundoonotes.data.user.UserDao
import com.nikhiljain.fundoonotes.data.user.UserEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.properties.Delegates

class NotesTest {
    private lateinit var testDatabase: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var noteDao: NoteDao
    private var userId by Delegates.notNull<Long>()

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        testDatabase = Room.inMemoryDatabaseBuilder(
            appContext,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        userDao = testDatabase.userDao
        noteDao = testDatabase.noteDao

        // setUp User For Notes
        val user = UserEntity(
            firstName = "Nikhil", lastName = "Jain",
            email = "nikhil@gmail.com", password = "1234567890",
            mobileNumber = "3456782131"
        )
        runBlocking {
            userId = userDao.insert(user)
        }
    }

    @Test
    fun shouldBeAbleToAddOneNote() {
        runBlocking {
            val note = NoteEntity(
                title = "Note Title",
                description = "This is note description",
                userId = userId, reminder = null
            )
            val noteId = noteDao.insert(note)
            val insertedNote = note.copy(noteId)
            val expectedNote = noteDao.getNote(noteId, userId)
            Assert.assertEquals(1, noteDao.getNotes(userId).size)
            Assert.assertEquals(expectedNote, insertedNote)
        }
    }

    @Test
    fun shouldBeAbleToAddNotes() {
        runBlocking {
            val beforeInsertionSize =
                noteDao.getNotes(userId).size
            val firstNote = NoteEntity(
                title = "First Note Title",
                description = "This is note description",
                userId = userId, reminder = null
            )
            noteDao.insert(firstNote)
            noteDao.insert(firstNote)

            val afterInsertionSize =
                noteDao.getNotes(userId).size
            Assert.assertEquals(afterInsertionSize, beforeInsertionSize + 2)
        }
    }

    @Test
    fun shouldBeAbleToUpdateNote() {
        runBlocking {
            val note = NoteEntity(
                title = "Note Title",
                description = "This is note description",
                userId = userId, reminder = null
            )
            val noteId = noteDao.insert(note)
            val insertedNote = noteDao.getNote(noteId, userId)
            Assert.assertNotNull(insertedNote)

            val noteToUpdate = insertedNote!!.copy(
                title = "Note Title Updated",
                description = "This is updated note description"
            )
            noteDao.updateWithModifiedDate(noteToUpdate)

            val updatedNote = noteDao.getNote(noteId, userId)
            Assert.assertEquals(noteToUpdate, updatedNote)
        }
    }

    @Test
    fun shouldBeAbleToDeleteNote() {
        runBlocking {
            val note = NoteEntity(
                title = "Note Title",
                description = "This is note description",
                userId = userId, reminder = null
            )
            val noteId = noteDao.insert(note)
            val insertedNote = noteDao.getNote(noteId, userId)
            Assert.assertNotNull(insertedNote)

            noteDao.delete(insertedNote!!)
            val foundUser = noteDao.getNote(noteId, userId)
            Assert.assertNull(foundUser)
        }
    }

    @Test
    fun shouldDeleteAllNotesOfUser_WhenThatUserIsDeleted() {
        runBlocking {
            val firstNote = NoteEntity(
                title = "First Note Title",
                description = "This is first note description",
                userId = userId, reminder = null
            )
            val secondNote = NoteEntity(
                title = "Second Note Title",
                description = "This is second note description",
                userId = userId, reminder = null
            )
            val notesSizeBeforeInsertion = noteDao.getNotes(userId).size
            noteDao.insert(firstNote)
            noteDao.insert(secondNote)
            val notesSizeAfterInsertion = noteDao.getNotes(userId).size
            Assert.assertEquals(notesSizeAfterInsertion, notesSizeBeforeInsertion + 2)

            val currentUser = userDao.getUser(userId)
            Assert.assertNotNull(currentUser)
            userDao.delete(currentUser!!)

            val foundUser = userDao.getUser(userId)
            Assert.assertNull(foundUser)
            val notesSizeAfterUserDeleted = noteDao.getNotes(userId).size
            Assert.assertEquals(0, notesSizeAfterUserDeleted)
        }
    }

    @After
    fun tearDown() {
        testDatabase.clearAllTables()
    }
}