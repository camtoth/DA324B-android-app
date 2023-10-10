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

class TestDatabaseInserts {
    // Set up test database
    companion object {
        @BeforeClass
        @JvmStatic
        fun setupTestDb() {
            resetTestDB()
            val setupDb = MockDBHelper(InstrumentationRegistry.getInstrumentation().targetContext).writableDatabase
            val testUserId =
                insertNewUser(setupDb, InsertUser("test1", "pw1", "Test1", "Test1", false))
            val testUserId2 =
                insertNewUser(setupDb, InsertUser("test2", "pw2", "Test2", "Test2", false))
            val firstQID = insertNewQuestion(
                setupDb,
                InsertQuestion(
                    textEn = "Q1",
                    textSe = "Q1",
                    rNeglect = 0.5f,
                    rPca = 0.5f,
                    weightYesNeglect = 1f,
                    weightMiddleNeglect = 0.5f,
                    weightNoNeglect = 0f,
                    weightYesPca = 1f,
                    weightMiddlePca = 0.5f,
                    weightNoPca = 0f
                )
            )
            val secondQId = insertNewQuestion(
                setupDb,
                InsertQuestion(
                    textEn = "Q2",
                    textSe = "Q2",
                    rPca = 0.5f,
                    weightYesPca = 1f,
                    weightMiddlePca = 0.5f,
                    weightNoPca = 0f
                )
            )
            val thirdQId = insertNewQuestion(
                setupDb,
                InsertQuestion(
                    textEn = "Q3",
                    textSe = "Q3",
                    rNeglect = 0.5f,
                    weightYesNeglect = 0f,
                    weightMiddleNeglect = 0.5f,
                    weightNoNeglect = 1f
                )
            )
            val parent1 = InsertParent("P1", "P1", "f")
            val parent2 = InsertParent("P2", "P2", "m")
            val newCase = InsertCase("1", "1", "m", "1", "1", listOf(parent1, parent2))
            val case1Id = insertNewCase(setupDb, newCase, testUserId)
            val parent3 = InsertParent("P3", "P3", "m")
            val parent4 = InsertParent("P4", "P4", "f")
            val newCase2 = InsertCase("2", "2", "f", "2", "2", listOf(parent3, parent4))
            val case2Id = insertNewCase(setupDb, newCase2, testUserId2)
            val parent5 = InsertParent("P5", "P5", "m")
            val parent6 = InsertParent("P6", "P6", "f")
            val newCase3 = InsertCase("3", "3", "f", "3", "3", listOf(parent5, parent6))
            val case3Id = insertNewCase(setupDb, newCase3, testUserId)
            val answer1 = InsertAnswer(optYes = false, optMiddle = false, optNo = true, parentNo = 1)
            val answer2 = InsertAnswer(optYes = false, optMiddle = true, optNo = false, parentNo = 1)
            val answer3 = InsertAnswer(optYes = true, optMiddle = false, optNo = false, parentNo = 2)
            insertNewAnswer(db = setupDb, newAnswer = answer1, curCaseId = 1, curQuestionId = 1)
            insertNewAnswer(db = setupDb, newAnswer = answer2, curCaseId = 1, curQuestionId = 2)
            insertNewAnswer(db = setupDb, newAnswer = answer3, curCaseId = 1, curQuestionId = 3)
            setupDb.close()
        }

        @AfterClass
        @JvmStatic
        fun resetTestDB() {
            val teardownDb = MockDBHelper(InstrumentationRegistry.getInstrumentation().targetContext).writableDatabase
            teardownDb.rawQuery("DELETE FROM Answers;", null)
            teardownDb.rawQuery("DELETE FROM Questions;", null)
            teardownDb.rawQuery("DELETE FROM Parents;", null)
            teardownDb.rawQuery("DELETE FROM Parent_to_Case;", null)
            teardownDb.rawQuery("DELETE FROM Cases;", null)
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
}