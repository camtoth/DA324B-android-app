package com.example.riskassessmentprotoype.databaseTest

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.riskassessmentprotoype.database.Case
import com.example.riskassessmentprotoype.database.Parent
import com.example.riskassessmentprotoype.database.User
import com.example.riskassessmentprotoype.database.getAllUsernames
import com.example.riskassessmentprotoype.database.getCasesByUser
import com.example.riskassessmentprotoype.database.getPwByUsername
import com.example.riskassessmentprotoype.database.getUserByUsername
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import java.util.LinkedList


@RunWith(AndroidJUnit4::class)
class TestDatabaseQueries {
    val mockDb = MockDBHelper(InstrumentationRegistry.getInstrumentation().targetContext).writableDatabase

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
        assert(casesAdmin.size == 0)
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