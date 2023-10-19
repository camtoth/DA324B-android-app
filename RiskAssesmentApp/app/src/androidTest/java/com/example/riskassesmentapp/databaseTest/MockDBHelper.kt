package com.example.riskassesmentapp.databaseTest

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.rememberCoroutineScope
import com.example.riskassesmentapp.db.DatabaseContract
import com.example.riskassesmentapp.db.InsertAnswer
import com.example.riskassesmentapp.db.InsertCase
import com.example.riskassesmentapp.db.InsertParent
import com.example.riskassesmentapp.db.InsertQuestion
import com.example.riskassesmentapp.db.InsertUser
import com.example.riskassesmentapp.db.UpdateCase
import com.example.riskassesmentapp.db.insertNewAnswer
import com.example.riskassesmentapp.db.insertNewCase
import com.example.riskassesmentapp.db.insertNewQuestion
import com.example.riskassesmentapp.db.insertNewUser
import com.example.riskassesmentapp.db.updateCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MockDBHelper(context: Context): SQLiteOpenHelper(context, "MockDatabase.db", null, 1) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DatabaseContract.SQL_CREATE_QUESTIONS)
        db.execSQL(DatabaseContract.SQL_CREATE_USERS)
        db.execSQL(DatabaseContract.SQL_CREATE_CASES)
        db.execSQL(DatabaseContract.SQL_CREATE_PARENTS)
        db.execSQL(DatabaseContract.SQL_CREATE_ANSWERS)
        db.execSQL("INSERT INTO Users " +
                "(username, pw, given_names, last_name, is_admin) " +
                "VALUES " +
                "(?, ?, ?, ?, ?)",
            arrayOf("admin", "admin", "admin", "admin", "1")
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
}