package com.example.riskassessmentprotoype.database

import android.database.sqlite.SQLiteDatabase
import java.util.Date
import java.util.LinkedList

data class User(
    val id: Long,
    val username: String,
    val givenNames: String,
    val lastName: String,
    val isAdmin: Boolean
)

data class Question(
    val id: Long,
    val textEn: String,
    val textSe: String,
    val rNeglect: Float? = null,
    val rPca: Float? = null,
    val weightYesNeglect: Float? = null,
    val weightMiddleNeglect: Float? = null,
    val weightNoNeglect: Float? = null,
    val weightYesPca: Float? = null,
    val weightMiddlePca: Float? = null,
    val weightNoPca: Float? = null
)

data class Case(
    val id: Long,
    val personnr: String,
    val email: String,
    val gender: String,
    val givenNames: String,
    val lastName: String,
    val parents: List<InsertParent>,
    val userId: Long,
    val neglectRisk: Boolean? = null,
    val neglectScore: Float? = null,
    val neglectEstimation: Float? = null,
    val pcaRisk: Boolean? = null,
    val pcaScore: Float? = null,
    val pcaEstimation: Float? = null,
)

data class Parent(
    val id: Long,
    val givenNames: String,
    val lastName: String,
    val gender: String
)

data class Answer(
    val id: Long,
    val optYes: Boolean,
    val optMiddle: Boolean,
    val optNo: Boolean,
    val lastChanged: Date,
    val caseId: Int,
    val questionId: Int
)

fun getAllUsernames(db: SQLiteDatabase): List<String> {
    var usernameList = LinkedList<String>()
    val cursor = db.query("Users", arrayOf("username"), null, null, null, null, "username ASC")
    with (cursor) {
        while (cursor.moveToNext()) {
            usernameList.add(getString(getColumnIndexOrThrow("username")))
        }
    }
    return usernameList
}

fun getPwByUsername(db: SQLiteDatabase, username: String): String? {
    var pw: String? = null
    val cursor = db.query("Users", arrayOf("pw"), "username LIKE ?", arrayOf(username), null, null, null)
    with (cursor) {
        while (cursor.moveToNext()) {
            pw = (getString(getColumnIndexOrThrow("pw")))
        }
    }
    return pw
}
fun getUserByUsername(db: SQLiteDatabase, username: String): User? {
    var user: User? = null
    val cursor = db.query("Users", null, "username LIKE ?", arrayOf(username), null, null, null)
    with (cursor) {
        while (cursor.moveToNext()) {
            user = User(
                id = getLong(getColumnIndexOrThrow("user_id")),
                username = getString(getColumnIndexOrThrow("username")),
                givenNames = getString(getColumnIndexOrThrow("given_names")),
                lastName = getString(getColumnIndexOrThrow("last_name")),
                isAdmin = getInt(getColumnIndexOrThrow("is_admin")) == 1
            )
        }
    }
    return user
}