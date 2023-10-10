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
    val titleEn: String,
    val textEn: String,
    val titleSe: String,
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
    val caseNr: String,
    val email: String,
    val gender: String,
    val givenNames: String,
    val lastName: String,
)

data class InsertParent(
    val personnr: String,
    val givenNames: String,
    val lastName: String,
    val gender: String,
)

data class InsertAnswer(
    val optYes: Boolean,
    val optMiddle: Boolean,
    val optNo: Boolean
)

fun insertNewUser(db: SQLiteDatabase, user: InsertUser): Long {
    val userValues = ContentValues().apply {
        put("username", user.username)
        put("pw", user.pw)
        put("given_names", user.givenNames)
        put("last_name", user.lastName)
        put("is_admin", user.isAdmin)
    }
    return db.insert("Users", null, userValues)
}

fun insertNewQuestion(db: SQLiteDatabase, question: InsertQuestion): Long {
    val questionValues = ContentValues().apply {
        put("text_en", question.textEn)
        put("title_en", question.titleEn)
        put("text_se", question.textSe)
        put("title_se", question.titleSe)
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
    return db.insert("Questions", null, questionValues)
}

fun insertNewParent(db: SQLiteDatabase, parent: InsertParent, curCaseId: Long): Long {
    val parentValues = ContentValues().apply {
        put("personnr", parent.personnr)
        put("given_names", parent.givenNames)
        put("last_name", parent.lastName)
        put("gender", parent.gender)
        put("case_id", curCaseId)
    }
    return db.insert("Parents", null, parentValues)
}

fun insertNewCase(db: SQLiteDatabase, case: InsertCase, currentUserId: Long): Long {
    val caseValues = ContentValues().apply {
        put("personnr", case.personnr)
        put("case_nr", case.caseNr)
        put("email", case.email)
        put("gender", case.gender)
        put("given_names", case.givenNames)
        put("last_name", case.lastName)
        put("user_id", currentUserId)
    }
    return db.insert("Cases", null, caseValues)
}

@RequiresApi(Build.VERSION_CODES.O)
fun insertNewAnswer(db: SQLiteDatabase, newAnswer: InsertAnswer, questionId: Long, parentId: Long): Long {
    val answerValues = ContentValues().apply {
        put("opt_yes", newAnswer.optYes)
        put("opt_no", newAnswer.optNo)
        put("opt_middle", newAnswer.optMiddle)
        put("parent_id", parentId)
        put("question_id", questionId)
    }
    val newAnswerId = db.insert("Answers", null, answerValues)
    updateParent(db, UpdateParent(id = parentId, lastChanged = LocalDate.now().toString()))
    return newAnswerId
}