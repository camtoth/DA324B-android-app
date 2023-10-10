package com.example.riskassesmentapp.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import androidx.annotation.RequiresApi

data class UpdateUser(
    val id: Long,
    val pw: String? = null,
    val givenNames: String? = null,
    val lastName: String? = null
)

data class UpdateQuestion(
    val id: Long,
    val textEn: String? = null,
    val textSe: String? = null,
    val rNeglect: Float? = null,
    val rPca: Float? = null,
    val weightYesNeglect: Float? = null,
    val weightMiddleNeglect: Float? = null,
    val weightNoNeglect: Float? = null,
    val weightYesPca: Float? = null,
    val weightMiddlePca: Float? = null,
    val weightNoPca: Float? = null
)

data class UpdateCase(
    val id: Long,
    val email: String? = null,
    val gender: String? = null,
    val givenNames: String? = null,
    val lastName: String? = null,
    val lastChanged: String? = null,
    val neglectRisk: Boolean? = null,
    val neglectScore: Float? = null,
    val neglectEstimation: Float? = null,
    val pcaRisk: Boolean? = null,
    val pcaScore: Float? = null,
    val pcaEstimation: Float? = null,
)

data class UpdateParent(
    val id: Long,
    val givenNames: String? = null,
    val lastName: String? = null,
    val gender: String? = null
)

data class UpdateAnswer(
    val id: Long,
    val optYes: Boolean,
    val optMiddle: Boolean,
    val optNo: Boolean
)

fun updateUser(db: SQLiteDatabase, curUser: UpdateUser): Boolean {
    val newValues = ContentValues().apply {
        if (curUser.pw != null) put("pw", curUser.pw)
        if (curUser.givenNames != null) put("given_names", curUser.givenNames)
        if (curUser.lastName != null) put("last_name", curUser.lastName)
    }
    val updatedRows = db.update("Users", newValues, "user_id LIKE ?", arrayOf(curUser.id.toString()))
    return (updatedRows == 1)
}

fun updateQuestion(db: SQLiteDatabase, curQuestion: UpdateQuestion): Boolean {
    val newValues = ContentValues().apply {
        if (curQuestion.textEn != null) put("text_en", curQuestion.textEn)
        if (curQuestion.textSe != null) put("text_se", curQuestion.textSe)
        if (curQuestion.rNeglect != null) put("r_neglect", curQuestion.rNeglect)
        if (curQuestion.rPca != null) put("r_pca", curQuestion.rPca)
        if (curQuestion.weightYesNeglect != null) put("weight_yes_neglect", curQuestion.weightYesNeglect)
        if (curQuestion.weightNoNeglect != null) put("weight_no_neglect", curQuestion.weightNoNeglect)
        if (curQuestion.weightMiddleNeglect != null) put("weight_middle_neglect", curQuestion.weightMiddleNeglect)
        if (curQuestion.weightYesPca != null) put("weight_yes_pca", curQuestion.weightYesPca)
        if (curQuestion.weightNoPca != null) put("weight_no_pca", curQuestion.weightNoPca)
        if (curQuestion.weightMiddlePca != null) put("weight_middle_pca", curQuestion.weightMiddlePca)
    }
    val updatedRows = db.update("Questions", newValues, "question_id LIKE ?", arrayOf(curQuestion.id.toString()))
    return (updatedRows == 1)
}

fun updateCase(db: SQLiteDatabase,curCase: UpdateCase): Boolean {
    val newValues = ContentValues().apply {
        if (curCase.email != null) put("email", curCase.email)
        if (curCase.gender != null) put("gender", curCase.gender)
        if (curCase.givenNames != null) put("given_names", curCase.givenNames)
        if (curCase.lastName != null) put("last_name", curCase.lastName)
        if (curCase.neglectRisk != null) put("neglect_risk", curCase.neglectRisk)
        if (curCase.neglectScore != null) put("neglect_score", curCase.neglectScore)
        if (curCase.neglectEstimation != null) put("neglect_estimation", curCase.neglectEstimation)
        if (curCase.pcaRisk != null) put("pca_risk", curCase.pcaRisk)
        if (curCase.pcaScore != null) put("pca_score", curCase.pcaScore)
        if (curCase.pcaEstimation != null) put("pca_estimation", curCase.pcaEstimation)
        if (curCase.lastChanged != null) put("last_changed", curCase.lastChanged.toString())
    }
    val updatedRows = db.update("Cases", newValues, "case_id LIKE ?", arrayOf(curCase.id.toString()))
    return (updatedRows == 1)
}

fun updateParent(db: SQLiteDatabase, curParent: UpdateParent): Boolean {
    val newValues = ContentValues().apply {
        if (curParent.givenNames != null) put("given_names", curParent.givenNames)
        if (curParent.lastName != null) put("last_name", curParent.lastName)
        if (curParent.gender != null) put("gender", curParent.gender)
    }
    val updatedRows = db.update("Parents", newValues, "parent_id LIKE ?", arrayOf(curParent.id.toString()))
    return (updatedRows == 1)
}

@RequiresApi(Build.VERSION_CODES.O)
fun updateAnswer(db: SQLiteDatabase, curAnswer: UpdateAnswer): Boolean {
    val newValues = ContentValues().apply {
        put("opt_yes", curAnswer.optYes)
        put("opt_no", curAnswer.optNo)
        put("opt_middle", curAnswer.optMiddle)
    }
    val updatedRows = db.update("Answers", newValues, "answer_id LIKE ?", arrayOf(curAnswer.id.toString()))
    // Update date in case
    return (updatedRows == 1)
}

fun setAdmin(db: SQLiteDatabase, newAdminId: Long): Boolean {
    val newValues = ContentValues().apply {
        put("is_admin", true)
    }
    val updatedRows = db.update("Users", newValues, "user_id LIKE ?", arrayOf(newAdminId.toString()))
    return (updatedRows == 1)
}

fun resetAdmin(db: SQLiteDatabase, oldAdminId: Long): Boolean {
    val newValues = ContentValues().apply {
        put("is_admin", false)
    }
    val updatedRows = db.update("Users", newValues, "user_id LIKE ? AND username NOT LIKE 'admin'", arrayOf(oldAdminId.toString()))
    return (updatedRows == 1)
}