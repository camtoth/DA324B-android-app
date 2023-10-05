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
        // Insert questions here
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "RiskAssessmentPrototype.db"
    }
}

