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
        insertNewUser(db, InsertUser("admin", "admin", "admin", "admin", true))
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