package com.example.riskassesmentapp.db

import android.content.Context

class DatabaseProvider private constructor(context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: DatabaseOpenHelper? = null

        fun getInstance(context: Context): DatabaseOpenHelper {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DatabaseOpenHelper(context).also { INSTANCE = it }
            }
        }
    }
}
