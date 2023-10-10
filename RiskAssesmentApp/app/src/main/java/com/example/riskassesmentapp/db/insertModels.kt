package com.example.riskassesmentapp.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

data class InsertUser(
    val username: String,
    val pw: String,
    val givenNames: String,
    val lastName: String,
    val isAdmin: Boolean
)

data class InsertQuestion(
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

data class InsertCase(
    val personnr: String,
    val email: String,
    val gender: String,
    val givenNames: String,
    val lastName: String,
    val parents: List<InsertParent>,
    val neglectRisk: Boolean? = null,
    val neglectScore: Float? = null,
    val neglectEstimation: Float? = null,
    val pcaRisk: Boolean? = null,
    val pcaScore: Float? = null,
    val pcaEstimation: Float? = null
)

data class InsertParent(
    val givenNames: String,
    val lastName: String,
    val gender: String
)

data class InsertAnswer(
    val optYes: Boolean,
    val optMiddle: Boolean,
    val optNo: Boolean,
    val parentNo: Int
)

fun insertNewUser(db: SQLiteDatabase, user: InsertUser): Long {
    val userValues = ContentValues().apply {
        put("username", user.username)
        put("pw", user.pw)
        put("given_names", user.givenNames)
        put("last_name", user.lastName)
        put("is_admin", user.isAdmin)
    }
    val newUserId = db.insert("Users", null, userValues)
    return newUserId
}

fun insertNewQuestion(db: SQLiteDatabase, question: InsertQuestion): Long {
    val questionValues = ContentValues().apply {
        put("text_en", question.textEn)
        put("text_se", question.textSe)
        if (question.rNeglect != null) {
            put("r_neglect", question.rNeglect)
            put("weight_yes_neglect", question.weightYesNeglect)
            put("weight_middle_neglect", question.weightMiddleNeglect)
            put("weight_no_neglect", question.weightNoNeglect)
        }
        if (question.rPca != null) {
            put("r_pca", question.rPca)
            put("weight_yes_pca", question.weightYesPca)
            put("weight_middle_pca", question.weightMiddlePca)
            put("weight_no_pca", question.weightNoPca)
        }
    }
    val newQuestionId = db.insert("Questions", null, questionValues)
    return newQuestionId
}

fun insertNewCase(db: SQLiteDatabase, case: InsertCase, currentUserId: Long): Long {
    val parentKeys = ArrayList<Long>()
    for (parent in case.parents) {
        val parentValues = ContentValues().apply {
            put("given_names", parent.givenNames)
            put("last_name", parent.lastName)
            put("gender", parent.gender)
        }
        val newParentId = db.insert("Parents", null, parentValues)
        if (newParentId.compareTo(-1) != 0) parentKeys.add(newParentId)
    }
    val caseValues = ContentValues().apply {
        put("personnr", case.personnr)
        put("email", case.email)
        put("gender", case.gender)
        put("given_names", case.givenNames)
        put("last_name", case.lastName)
        put("user_id", currentUserId)
    }
    val newCaseId = db.insert("Cases", null, caseValues)
    if (newCaseId.compareTo(-1) == 0) return -1
    for (parentId in parentKeys) {
        val parentToCaseValues = ContentValues().apply {
            put("parent_id", parentId)
            put("case_id", newCaseId)
        }
        db.insert("Parent_to_Case", null, parentToCaseValues)
    }
    return newCaseId
}

@RequiresApi(Build.VERSION_CODES.O)
fun insertNewAnswer(db: SQLiteDatabase, newAnswer: InsertAnswer, curQuestionId: Long, curCaseId: Long): Long {
    val answerValues = ContentValues().apply {
        put("opt_yes", newAnswer.optYes)
        put("opt_no", newAnswer.optNo)
        put("opt_middle", newAnswer.optMiddle)
        put("parent_no", newAnswer.parentNo)
        put("case_id", curCaseId)
        put("question_id", curQuestionId)
    }
    val newAnswerId = db.insert("Answers", null, answerValues)
    // Update date in Case instead put("last_changed", LocalDate.now().toString())
    updateCase(db, UpdateCase(id = curCaseId, lastChanged = LocalDate.now().toString()))
    return newAnswerId
}