package com.example.riskassessmentprotoype.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi

class DatabaseOpenHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DatabaseContract.SQL_CREATE_USERS)
        db.execSQL(DatabaseContract.SQL_CREATE_QUESTIONS)
        db.execSQL(DatabaseContract.SQL_CREATE_PARENTS)
        db.execSQL(DatabaseContract.SQL_CREATE_CASES)
        db.execSQL(DatabaseContract.SQL_CREATE_ANSWERS)
        db.execSQL(DatabaseContract.SQL_CREATE_PARENT_TO_CASE)
        val newUserId = insertNewUser(db, InsertUser("admin", "admin", "admin", "admin", true))
        val firstQID = insertNewQuestion(db, InsertQuestion(textEn = "To what extent do you see the child as a problem?", textSe = "", rNeglect = 0.41f, rPca = 0.3f, weightYesNeglect = 1f, weightMiddleNeglect = 0.5f, weightNoNeglect = 0f, weightYesPca = 1f, weightMiddlePca = 0.5f, weightNoPca = 0f))
        val secondQId = insertNewQuestion(db, InsertQuestion(textEn = "Was the pregnancy unplanned?", textSe =  "", rPca = 0.28f, weightYesPca = 1f, weightMiddlePca = 0.5f, weightNoPca = 0f))
        val parent1 = InsertParent("Olga", "One", "f")
        val parent2 = InsertParent("Tom", "Two", "m")
        val newCase = InsertCase("123", "123@hotmail.com", "m", "Terry", "Three", listOf(parent1, parent2))
        val newCaseId = insertNewCase(db, newCase, newUserId)
        val answerOne = InsertAnswer(true, false,false)
        val answerTwo = InsertAnswer(false, false,true)
        insertNewAnswer(db,answerOne, firstQID, newCaseId  )
        insertNewAnswer(db, answerTwo, secondQId, newCaseId)
        val newUser = insertNewUser(db, InsertUser("fabian","pw", "Fabian", "Fröschl", false))
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DatabaseContract.SQL_CREATE_USERS)
        db.execSQL(DatabaseContract.SQL_CREATE_QUESTIONS)
        db.execSQL(DatabaseContract.SQL_CREATE_PARENTS)
        db.execSQL(DatabaseContract.SQL_CREATE_CASES)
        db.execSQL(DatabaseContract.SQL_CREATE_ANSWERS)
        db.execSQL(DatabaseContract.SQL_CREATE_PARENT_TO_CASE)
    }
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "RiskAssessmentPrototype.db"
    }
}

