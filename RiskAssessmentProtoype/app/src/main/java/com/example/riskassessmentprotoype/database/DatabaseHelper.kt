package com.example.riskassessmentprotoype.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseOpenHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DatabaseContract.SQL_CREATE_USERS)
        db.execSQL(DatabaseContract.SQL_CREATE_QUESTIONS)
        db.execSQL(DatabaseContract.SQL_CREATE_PARENTS)
        db.execSQL(DatabaseContract.SQL_CREATE_CASES)
        db.execSQL(DatabaseContract.SQL_CREATE_ANSWERS)
        db.execSQL(DatabaseContract.SQL_CREATE_PARENT_TO_CASE)
        insertNewUser(db, InsertUser("admin", "admin", "admin", "admin", true))
        insertNewQuestion(db, InsertQuestion(textEn = "To what extent do you see the child as a problem?", textSe = "", rNeglect = 0.41f, rPca = 0.3f, weightYesNeglect = 1f, weightNoNeglect = 0f, weightYesPca = 1f, weightNoPca = 0f))
        insertNewQuestion(db, InsertQuestion(textEn = "Was the pregnancy unplanned?", textSe =  "", rPca = 0.28f, weightYesPca = 1f, weightNoPca = 0f))
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

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
    }
}

