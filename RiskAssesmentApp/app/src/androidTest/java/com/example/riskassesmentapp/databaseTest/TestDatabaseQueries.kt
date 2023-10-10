package com.example.riskassesmentapp.databaseTest

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
import com.example.riskassesmentapp.db.getPwByUsername
import com.example.riskassesmentapp.db.getQuestionsWithAnswerByCaseId
import com.example.riskassesmentapp.db.getUserByUsername
import com.example.riskassesmentapp.db.insertNewAnswer
import com.example.riskassesmentapp.db.insertNewCase
import com.example.riskassesmentapp.db.insertNewQuestion
import com.example.riskassesmentapp.db.insertNewUser
import org.junit.After
import org.junit.AfterClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import java.time.LocalDate
import java.util.LinkedList


@RunWith(AndroidJUnit4::class)
class TestDatabaseQueries {
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

    @Test
    fun testGetQuestionsWithAnswerByCaseId() {
        val questionsAnswersCase1 = getQuestionsWithAnswerByCaseId(mockDb, caseId = 1)
        assertEquals(QuestionWithAnswer(
            questionId = 1L,
            textEn = "Q1",
            textSe = "Q1",
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
            answerId = 1,
            parentNo = 1
        ), questionsAnswersCase1[0])
        assertEquals(QuestionWithAnswer(
            questionId = 2L,
            textEn = "Q2",
            textSe = "Q2",
            rPca = 0.5f,
            weightYesPca = 1f,
            weightMiddlePca = 0.5f,
            weightNoPca = 0f,
            optYes = false,
            optMiddle = true,
            optNo = false,
            answerId = 2,
            parentNo = 1
        ), questionsAnswersCase1[1])
        assertEquals(QuestionWithAnswer(
            questionId = 3L,
            textEn = "Q3",
            textSe = "Q3",
            rNeglect = 0.5f,
            weightYesNeglect = 0f,
            weightMiddleNeglect = 0.5f,
            weightNoNeglect = 1f,
            optYes = true,
            optMiddle = false,
            optNo = false,
            answerId = 3,
            parentNo = 2
        ), questionsAnswersCase1[2])

        val questionsAnswersCase2 = getQuestionsWithAnswerByCaseId(mockDb, caseId = 2)
        assert(questionsAnswersCase2.isEmpty())
    }

    @Test
    fun testGetAllQuestions() {
        val allQuestions = getAllQuestions(mockDb)
        assertEquals(Question(
            id = 1L,
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
        ), allQuestions[0])
        assertEquals(Question(
            id = 2L,
            textEn = "Q2",
            textSe = "Q2",
            rPca = 0.5f,
            weightYesPca = 1f,
            weightMiddlePca = 0.5f,
            weightNoPca = 0f
        ), allQuestions[1])
        assertEquals(Question(
            id = 3L,
            textEn = "Q3",
            textSe = "Q3",
            rNeglect = 0.5f,
            weightYesNeglect = 0f,
            weightMiddleNeglect = 0.5f,
            weightNoNeglect = 1f
        ), allQuestions[2])
    }

    @Test
    fun testGetAllUsernames() {
        val userList = getAllUsernames(mockDb)
        assert(userList.size == 3)
        assert(userList.contains("admin"))
        assert(userList.contains("test1"))
        assert(userList.contains("test2"))
    }

    @Test
    fun testGetUserByUsername() {
        val admin = getUserByUsername(mockDb, "admin")
        val admin_exp = User(1, "admin", "admin", "admin",  true)
        assertNotNull(admin)
        assertEquals(admin_exp, admin)
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
    fun testGetPwByUsername() {
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
    fun testGetCasesByUser() {
        val casesAdmin = getCasesByUser(mockDb, userId = 1)
        assert(casesAdmin.isEmpty())
        val casesUser1 = getCasesByUser(mockDb, userId = 2)
        assert(casesUser1.size == 2)
        val case1User1 = casesUser1[0]
        val case1User1Exp = Case(
            id = 1,
            personnr = "1",
            email = "1",
            gender = "m",
            givenNames = "1",
            lastName = "1",
            lastChanged = LocalDate.now().toString(),
            parents = LinkedList<Parent>().apply {
                add(Parent(id = 1, givenNames = "P1", lastName = "P1", gender = "f"))
                add(Parent(id = 2, givenNames = "P2", lastName = "P2", gender = "m"))
            }
        )
        assertEquals(case1User1Exp, case1User1)
        val case2User1 = casesUser1[1]
        val case2User1Exp = Case(
            id = 3,
            personnr = "3",
            email = "3",
            gender = "f",
            givenNames = "3",
            lastName = "3",
            parents = LinkedList<Parent>().apply {
                add(Parent(id = 5, givenNames = "P5", lastName = "P5", gender = "m"))
                add(Parent(id = 6, givenNames = "P6", lastName = "P6", gender = "f"))
            }
        )
        assertEquals(case2User1Exp, case2User1)
        val casesUser2 = getCasesByUser(mockDb, userId = 3)
        assert(casesUser2.size == 1)
        val case1User2 = casesUser2[0]
        val case1User2Exp = Case(
            id = 2,
            personnr = "2",
            email = "2",
            gender = "f",
            givenNames = "2",
            lastName = "2",
            parents = LinkedList<Parent>().apply {
                add(Parent(id = 3, givenNames = "P3", lastName = "P3", gender = "m"))
                add(Parent(id = 4, givenNames = "P4", lastName = "P4", gender = "f"))
            }
        )
        assertEquals(case1User2Exp,case1User2)
    }
}