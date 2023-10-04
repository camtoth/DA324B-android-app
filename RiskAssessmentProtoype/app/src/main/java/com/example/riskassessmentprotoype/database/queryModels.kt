package com.example.riskassessmentprotoype.database

import android.database.sqlite.SQLiteDatabase
import androidx.core.database.getFloatOrNull
import androidx.core.database.getIntOrNull
import androidx.core.database.getShortOrNull
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
    val parents: LinkedList<Parent>,
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

fun getCasesByUser(db: SQLiteDatabase, userId: Long): List<Case> {
    val caseList = LinkedList<Case>()
    // get case data
    val cursorCase = db.query("Cases", null, "user_id LIKE ?", arrayOf(userId.toString()), null, null, null)
    with (cursorCase) {
        while (moveToNext()) {
            var neglectRisk: Boolean? = null
            val neglectRiskInt = getIntOrNull(getColumnIndexOrThrow("neglect_risk"))
            if (neglectRiskInt != null) neglectRisk = neglectRiskInt == 1
            var pcaRisk: Boolean? = null
            val pcaRiskInt = getIntOrNull(getColumnIndexOrThrow("pca_risk"))
            if (pcaRiskInt != null) pcaRisk = pcaRiskInt == 1
            caseList.add(Case(
                id = getLong(getColumnIndexOrThrow("case_id")),
                personnr = getString(getColumnIndexOrThrow("personnr")),
                email = getString(getColumnIndexOrThrow("email")),
                gender = getString(getColumnIndexOrThrow("gender")),
                givenNames = getString(getColumnIndexOrThrow("given_names")),
                lastName = getString(getColumnIndexOrThrow("last_name")),
                neglectRisk = neglectRisk,
                neglectScore = getFloatOrNull(getColumnIndex("neglect_score")),
                neglectEstimation = getFloatOrNull(getColumnIndex("neglect_estimation")),
                pcaRisk = pcaRisk,
                pcaScore = getFloatOrNull(getColumnIndex("pca_score")),
                pcaEstimation = getFloatOrNull(getColumnIndex("pca_estimation")),
                parents = LinkedList<Parent>()
            ))
        }
    }
    // get Parents
    for (case in caseList) {
        val cursorParents = db.rawQuery(
            "SELECT Parents.parent_id, Parents.given_names, Parents.last_name, Parents.gender " +
                    "FROM Parent_to_Case " +
                    "INNER JOIN Parents ON Parent_to_Case.parent_id=Parents.parent_id " +
                    "WHERE Parent_to_Case.case_id LIKE ?",
            arrayOf(case.id.toString())
        )
        with (cursorParents) {
            while (moveToNext()) {
                case.parents.add(Parent(
                    getLong(getColumnIndexOrThrow("parent_id")),
                    getString(getColumnIndexOrThrow("given_names")),
                    getString(getColumnIndexOrThrow("last_name")),
                    getString(getColumnIndexOrThrow("gender")),
                ))
            }
        }
    }
    // add list of parents
    return caseList
}

fun getAllUsernames(db: SQLiteDatabase): List<String> {
    val usernameList = LinkedList<String>()
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
