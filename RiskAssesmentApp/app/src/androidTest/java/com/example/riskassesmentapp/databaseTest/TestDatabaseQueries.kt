package com.example.riskassesmentapp.databaseTest

import android.database.sqlite.SQLiteDatabase
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.riskassesmentapp.db.Case
import com.example.riskassesmentapp.db.InsertAnswer
import com.example.riskassesmentapp.db.InsertCase
import com.example.riskassesmentapp.db.InsertParent
import com.example.riskassesmentapp.db.InsertQuestion
import com.example.riskassesmentapp.db.InsertUser
import com.example.riskassesmentapp.db.Parent
import com.example.riskassesmentapp.db.Question
import com.example.riskassesmentapp.db.QuestionWithAnswer
import com.example.riskassesmentapp.db.User
import com.example.riskassesmentapp.db.getAllQuestions
import com.example.riskassesmentapp.db.getAllUsernames
import com.example.riskassesmentapp.db.getCasesByUser
import com.example.riskassesmentapp.db.getParentsByCase
import com.example.riskassesmentapp.db.getPwByUsername
import com.example.riskassesmentapp.db.getQuestionsWithAnswerByParent
import com.example.riskassesmentapp.db.getUserByUsername
import com.example.riskassesmentapp.db.insertNewAnswer
import com.example.riskassesmentapp.db.insertNewCase
import com.example.riskassesmentapp.db.insertNewParent
import com.example.riskassesmentapp.db.insertNewQuestion
import com.example.riskassesmentapp.db.insertNewUser
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.AfterClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import java.time.LocalDate


@RunWith(AndroidJUnit4::class)
class TestDatabaseQueries {
    // Set up test database
    companion object {
        @BeforeClass
        @JvmStatic
        fun setupTestDb() = runTest {
            resetTestDB()
            val setupDb = MockDBHelper(InstrumentationRegistry.getInstrumentation().targetContext).writableDatabase
            val testUserId =
                insertNewUser(setupDb, InsertUser("test1", "pw1", "Test1", "Test1", false))
            val testUserId2 =
                insertNewUser(setupDb, InsertUser("test2", "pw2", "Test2", "Test2", false))
            val firstQID = insertNewQuestion(
                setupDb,
                InsertQuestion(
                    titleEn = "T1E",
                    textEn = "Q1E",
                    titleSe = "T1S",
                    textSe = "Q1S",
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
                    titleEn = "T2E",
                    textEn = "Q2E",
                    titleSe = "T2S",
                    textSe = "Q2S",
                    rPca = 0.5f,
                    weightYesPca = 1f,
                    weightMiddlePca = 0.5f,
                    weightNoPca = 0f
                )
            )
            val thirdQId = insertNewQuestion(
                setupDb,
                InsertQuestion(
                    titleEn = "T3E",
                    textEn = "Q3E",
                    titleSe = "T3S",
                    textSe = "Q3S",
                    rNeglect = 0.5f,
                    weightYesNeglect = 0f,
                    weightMiddleNeglect = 0.5f,
                    weightNoNeglect = 1f
                )
            )
            val newCase = InsertCase("1", "1",  "1", "m", "1", "1")
            val case1Id = insertNewCase(setupDb, newCase, testUserId)
            val newCase2 = InsertCase("2", "2","2", "f", "2", "2")
            val case2Id = insertNewCase(setupDb, newCase2, testUserId2)
            val newCase3 = InsertCase("3", "3","3", "f", "3", "3")
            val case3Id = insertNewCase(setupDb, newCase3, testUserId)
            val parent1 = InsertParent("P1", "P1", "P1", "f")
            val parent2 = InsertParent("P2", "P2", "P2", "m")
            val parent3 = InsertParent("P3", "P3", "P3", "m")
            val parent4 = InsertParent("P4", "P4", "P4", "f")
            val parent5 = InsertParent("P5", "P5", "P5", "m")
            val parent6 = InsertParent("P6", "P6", "P6", "f")
            val parent1Id = insertNewParent(setupDb, parent1, case1Id)
            insertNewParent(setupDb, parent2, case1Id)
            insertNewParent(setupDb, parent3, case2Id)
            val parent4Id = insertNewParent(setupDb, parent4, case2Id)
            insertNewParent(setupDb, parent5, case3Id)
            insertNewParent(setupDb, parent6, case3Id)


            val answer1 = InsertAnswer(optYes = false, optMiddle = false, optNo = true)
            val answer2 = InsertAnswer(optYes = false, optMiddle = true, optNo = false)
            val answer3 = InsertAnswer(optYes = true, optMiddle = false, optNo = false)
            insertNewAnswer(db = setupDb, newAnswer = answer1, questionId = firstQID, parentId = parent1Id)
            insertNewAnswer(db = setupDb, newAnswer = answer2, questionId = secondQId, parentId = parent1Id)
            insertNewAnswer(db = setupDb, newAnswer = answer3, questionId = thirdQId, parentId = parent4Id)
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
    fun testGetParentsByCase() = runTest {
        val parentsCase1 = getParentsByCase(mockDb, caseId = 1L)
        assertEquals(2, parentsCase1.size)
        val parent1 = Parent(id = 1L, personnr = "P1", givenNames = "P1", lastName = "P1", gender = "f", caseId = 1L, lastChanged = LocalDate.now().toString())
        val parent2 = Parent(id = 2L, personnr = "P2", givenNames = "P2", lastName = "P2", gender = "m", caseId = 1L)
        assertEquals(parent1, parentsCase1[0])
        assertEquals(parent2, parentsCase1[1])

        val parentsCase2 = getParentsByCase(mockDb, caseId = 2L)
        assertEquals(2, parentsCase2.size)
        val parent3 = Parent(id = 3L, personnr = "P3", givenNames = "P3", lastName = "P3", gender = "m", caseId = 2L)
        val parent4 = Parent(id = 4L, personnr = "P4", givenNames = "P4", lastName = "P4", gender = "f", caseId = 2L, lastChanged = LocalDate.now().toString())
        assertEquals(parent3, parentsCase2[0])
        assertEquals(parent4, parentsCase2[1])

        val parentsCase3 = getParentsByCase(mockDb, caseId = 3L)
        assertEquals(2, parentsCase3.size)
        val parent5 = Parent(id = 5L, personnr = "P5", givenNames = "P5", lastName = "P5", gender = "m", caseId = 3L)
        val parent6 = Parent(id = 6L, personnr = "P6", givenNames = "P6", lastName = "P6", gender = "f", caseId = 3L)
        assertEquals(parent5, parentsCase3[0])
        assertEquals(parent6, parentsCase3[1])
    }

    @Test
    fun testGetQuestionsWithAnswerByParentId() = runTest {
        val questionsAnswersParent1 = getQuestionsWithAnswerByParent(mockDb, parentId = 1L)
        assertEquals(2, questionsAnswersParent1.size)
        assertEquals(QuestionWithAnswer(
            questionId = 1L,
            titleEn = "T1E",
            textEn = "Q1E",
            titleSe = "T1S",
            textSe = "Q1S",
            rNeglect = 0.5f,
            rPca = 0.5f,
            weightYesNeglect = 1f,
            weightMiddleNeglect = 0.5f,
            weightNoNeglect = 0f,
            weightYesPca = 1f,
            weightMiddlePca = 0.5f,
            weightNoPca = 0f,
            optYes = false,
            optMiddle = false,
            optNo = true,
            answerId = 1L,
            parentId = 1L
        ), questionsAnswersParent1[0])
        assertEquals(QuestionWithAnswer(
            questionId = 2L,
            titleEn = "T2E",
            textEn = "Q2E",
            titleSe = "T2S",
            textSe = "Q2S",
            rPca = 0.5f,
            weightYesPca = 1f,
            weightMiddlePca = 0.5f,
            weightNoPca = 0f,
            optYes = false,
            optMiddle = true,
            optNo = false,
            answerId = 2L,
            parentId = 1L,
        ), questionsAnswersParent1[1])

        val questionsAnswersParent2 = getQuestionsWithAnswerByParent(mockDb, parentId = 2L)
        assert(questionsAnswersParent2.isEmpty())

        val questionsAnswersParent3 = getQuestionsWithAnswerByParent(mockDb, parentId = 3L)
        assert(questionsAnswersParent3.isEmpty())

        val questionsAnswersParent4 = getQuestionsWithAnswerByParent(mockDb, parentId = 4L)
        assertEquals(1, questionsAnswersParent4.size)
        assertEquals(QuestionWithAnswer(
            questionId = 3L,
            titleEn = "T3E",
            textEn = "Q3E",
            titleSe = "T3S",
            textSe = "Q3S",
            rNeglect = 0.5f,
            weightYesNeglect = 0f,
            weightMiddleNeglect = 0.5f,
            weightNoNeglect = 1f,
            optYes = true,
            optMiddle = false,
            optNo = false,
            answerId = 3L,
            parentId = 4L,
        ), questionsAnswersParent4[0])

        val questionsAnswersParent5 = getQuestionsWithAnswerByParent(mockDb, parentId = 5L)
        assert(questionsAnswersParent5.isEmpty())

        val questionsAnswersParent6 = getQuestionsWithAnswerByParent(mockDb, parentId = 6L)
        assert(questionsAnswersParent6.isEmpty())
    }

    @Test
    fun testGetAllQuestions() = runTest {
        val allQuestions = getAllQuestions(mockDb)
        assertEquals(Question(
            id = 1L,
            titleEn = "T1E",
            textEn = "Q1E",
            titleSe = "T1S",
            textSe = "Q1S",
            rNeglect = 0.5f,
            rPca = 0.5f,
            weightYesNeglect = 1f,
            weightMiddleNeglect = 0.5f,
            weightNoNeglect = 0f,
            weightYesPca = 1f,
            weightMiddlePca = 0.5f,
            weightNoPca = 0f
        ), allQuestions[0])
        assertEquals(Question(
            id = 2L,
            titleEn = "T2E",
            textEn = "Q2E",
            titleSe = "T2S",
            textSe = "Q2S",
            rPca = 0.5f,
            weightYesPca = 1f,
            weightMiddlePca = 0.5f,
            weightNoPca = 0f
        ), allQuestions[1])
        assertEquals(Question(
            id = 3L,
            titleEn = "T3E",
            textEn = "Q3E",
            titleSe = "T3S",
            textSe = "Q3S",
            rNeglect = 0.5f,
            weightYesNeglect = 0f,
            weightMiddleNeglect = 0.5f,
            weightNoNeglect = 1f
        ), allQuestions[2])
    }

    @Test
    fun testGetAllUsernames() = runTest {
        val userList = getAllUsernames(mockDb)
        assert(userList.size == 3)
        assert(userList.contains("admin"))
        assert(userList.contains("test1"))
        assert(userList.contains("test2"))
    }

    @Test
    fun testGetUserByUsername() = runTest {
        val admin = getUserByUsername(mockDb, "admin")
        val adminExp = User(1, "admin", "admin", "admin",  true)
        assertNotNull(admin)
        assertEquals(adminExp, admin)
        val test1 = getUserByUsername(mockDb, "test1")
        val test1exp = User(2, "test1", "Test1", "Test1", false)
        assertNotNull(test1)
        assertEquals(test1exp, test1)
        val test2 = getUserByUsername(mockDb, "test2")
        val test2exp = User(3, "test2", "Test2", "Test2", false)
        assertNotNull(test2)
        assertEquals(test2exp, test2)
        val nonExistent = getUserByUsername(mockDb, "non_existent")
        assertNull(nonExistent)
    }

    @Test
    fun testGetPwByUsername() = runTest {
        val adminPw = getPwByUsername(mockDb, "admin")
        val adminPwExp = "admin"
        assertEquals(adminPwExp, adminPw)
        val test1Pw = getPwByUsername(mockDb, "test1")
        val test1PwExp = "pw1"
        assertEquals(test1Pw, test1PwExp)
        val test2Pw = getPwByUsername(mockDb, "test2")
        val test2PwExp = "pw2"
        assertEquals(test2Pw, test2PwExp)
        val nonExistentPw = getPwByUsername(mockDb, "non_existent")
        assertNull(nonExistentPw)
    }

    @Test
    fun testGetCasesByUser() = runTest {
        val casesAdmin = getCasesByUser(mockDb, userId = 1)
        assert(casesAdmin.isEmpty())
        val casesUser1 = getCasesByUser(mockDb, userId = 2)
        assert(casesUser1.size == 2)
        val case1User1 = casesUser1[0]
        val case1User1Exp = Case(
            id = 1,
            personnr = "1",
            caseNr = "1",
            email = "1",
            gender = "m",
            givenNames = "1",
            lastName = "1"
        )
        assertEquals(case1User1Exp, case1User1)
        val case2User1 = casesUser1[1]
        val case2User1Exp = Case(
            id = 3,
            personnr = "3",
            caseNr = "3",
            email = "3",
            gender = "f",
            givenNames = "3",
            lastName = "3",
        )
        assertEquals(case2User1Exp, case2User1)
        val casesUser2 = getCasesByUser(mockDb, userId = 3)
        assert(casesUser2.size == 1)
        val case1User2 = casesUser2[0]
        val case1User2Exp = Case(
            id = 2,
            personnr = "2",
            caseNr = "2",
            email = "2",
            gender = "f",
            givenNames = "2",
            lastName = "2",
        )
        assertEquals(case1User2Exp,case1User2)
    }
}