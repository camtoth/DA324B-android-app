package com.example.riskassessmentprotoype.databaseTest

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.riskassessmentprotoype.database.DatabaseContract
import com.example.riskassessmentprotoype.database.InsertAnswer
import com.example.riskassessmentprotoype.database.InsertCase
import com.example.riskassessmentprotoype.database.InsertParent
import com.example.riskassessmentprotoype.database.InsertQuestion
import com.example.riskassessmentprotoype.database.InsertUser
import com.example.riskassessmentprotoype.database.UpdateCase
import com.example.riskassessmentprotoype.database.insertNewAnswer
import com.example.riskassessmentprotoype.database.insertNewCase
import com.example.riskassessmentprotoype.database.insertNewQuestion
import com.example.riskassessmentprotoype.database.insertNewUser
import com.example.riskassessmentprotoype.database.updateCase

class MockDBHelper(context: Context): SQLiteOpenHelper(context, "MockDatabase.db", null, 1) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DatabaseContract.SQL_CREATE_USERS)
        db.execSQL(DatabaseContract.SQL_CREATE_QUESTIONS)
        db.execSQL(DatabaseContract.SQL_CREATE_PARENTS)
        db.execSQL(DatabaseContract.SQL_CREATE_CASES)
        db.execSQL(DatabaseContract.SQL_CREATE_ANSWERS)
        db.execSQL(DatabaseContract.SQL_CREATE_PARENT_TO_CASE)
        val adminId = insertNewUser(db, InsertUser("admin", "admin", "admin", "admin", true))
        // Everything below for testing
        val testUserId = insertNewUser(db, InsertUser("test1","pw1", "Test1", "Test1", false))
        val testUserId2 = insertNewUser(db, InsertUser("test2","pw2", "Test2", "Test2", false))
        val firstQID = insertNewQuestion(db, InsertQuestion(textEn = "To what extent do you see the child as a problem?", textSe = "", rNeglect = 0.41f, rPca = 0.3f, weightYesNeglect = 1f, weightMiddleNeglect = 0.5f, weightNoNeglect = 0f, weightYesPca = 1f, weightMiddlePca = 0.5f, weightNoPca = 0f))
        val secondQId = insertNewQuestion(db, InsertQuestion(textEn = "Was the pregnancy unplanned?", textSe =  "", rPca = 0.28f, weightYesPca = 1f, weightMiddlePca = 0.5f, weightNoPca = 0f))
        val parent1 = InsertParent("P1", "P1", "f")
        val parent2 = InsertParent("P2", "P2", "m")
        val newCase = InsertCase("1", "1", "m", "1", "1", listOf(parent1, parent2))
        val newCaseId = insertNewCase(db, newCase, testUserId)
        val parent3 = InsertParent("P3", "P3", "m")
        val parent4 = InsertParent("P4", "P4", "f")
        val newCase2 = InsertCase("2", "2", "f", "2", "2", listOf(parent3, parent4))
        val newCaseId2 = insertNewCase(db, newCase2, testUserId2)
        val parent5 = InsertParent("P5", "P5", "m")
        val parent6 = InsertParent("P6", "P6", "f")
        val newCase3 = InsertCase("3", "3", "f", "3", "3", listOf(parent5, parent6))
        val newCaseId3 = insertNewCase(db, newCase3, testUserId)
        /*val updated = updateCase(db, UpdateCase(newCaseId2, pcaRisk = true, pcaScore = 0.6f, pcaEstimation = 0.7f))
        val answerOne = InsertAnswer(true, false,false)
        val answerTwo = InsertAnswer(false, false,true)
        insertNewAnswer(db,answerOne, firstQID, newCaseId  )
        insertNewAnswer(db, answerTwo, secondQId, newCaseId)*/
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DatabaseContract.SQL_CREATE_USERS)
        db.execSQL(DatabaseContract.SQL_CREATE_QUESTIONS)
        db.execSQL(DatabaseContract.SQL_CREATE_PARENTS)
        db.execSQL(DatabaseContract.SQL_CREATE_CASES)
        db.execSQL(DatabaseContract.SQL_CREATE_ANSWERS)
        db.execSQL(DatabaseContract.SQL_CREATE_PARENT_TO_CASE)
    }
}