package com.example.riskassesmentapp.databaseTest

import androidx.test.platform.app.InstrumentationRegistry
import com.example.riskassesmentapp.db.InsertAnswer
import com.example.riskassesmentapp.db.InsertCase
import com.example.riskassesmentapp.db.InsertParent
import com.example.riskassesmentapp.db.InsertQuestion
import com.example.riskassesmentapp.db.InsertUser
import com.example.riskassesmentapp.db.insertNewAnswer
import com.example.riskassesmentapp.db.insertNewCase
import com.example.riskassesmentapp.db.insertNewQuestion
import com.example.riskassesmentapp.db.insertNewUser
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.Assert.*


class TestDatabaseInserts {
    // Set up test database
    companion object {
        @BeforeClass
        @JvmStatic
        fun setupTestDb() {
            resetTestDB()
            val setupDb = MockDBHelper(InstrumentationRegistry.getInstrumentation().targetContext).writableDatabase

            setupDb.close()
        }

        @AfterClass
        @JvmStatic
        fun resetTestDB() {
            val teardownDb = MockDBHelper(InstrumentationRegistry.getInstrumentation().targetContext).writableDatabase
            teardownDb.rawQuery("DELETE FROM Answers;", null)
            teardownDb.rawQuery("DELETE FROM Parents;", null)
            teardownDb.rawQuery("DELETE FROM Cases;", null)
            teardownDb.rawQuery("DELETE FROM Questions;", null)
            teardownDb.rawQuery("DELETE FROM Users WHERE user_id NOT LIKE 1;", null)
            teardownDb.close()
        }
    }

    var mockDb = MockDBHelper(InstrumentationRegistry.getInstrumentation().targetContext).writableDatabase


    @Before
    fun openTestDb() {
        mockDb.close()
        mockDb = MockDBHelper(InstrumentationRegistry.getInstrumentation().targetContext).writableDatabase
    }

    @After
    fun closeTestDB() {
        mockDb.close()
    }

    @Test
    fun testInsertNewUser() {
        val user2id = insertNewUser(mockDb, InsertUser("test1", "pw1", "Test1", "Test1", false))
        assertEquals(user2id, 2L)
        val user3Id = insertNewUser(mockDb, InsertUser("test2", "pw2", "Test2", "Test2", false))
        assertEquals(user3Id, 3L)
    }
}