package com.example.riskassesmentapp.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

data class UpdateUser(
    val id: Long,
    val pw: String? = null,
    val givenNames: String? = null,
    val lastName: String? = null
)

data class UpdateQuestion(
    val id: Long,
    val titleEn: String? = null,
    val textEn: String? = null,
    val titleSe: String? = null,
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
    val highRisk: Boolean? = null,
)

data class UpdateParent(
    val id: Long,
    val givenNames: String? = null,
    val lastName: String? = null,
    val gender: String? = null,
    val highRiskPca: Boolean? = null,
    val highRiskNeglect: Boolean? = null,
    val estHighRiskPca: Boolean? = null,
    val estHighRiskNeglect: Boolean? = null,
    val lastChanged: String? = null
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
    return db.update("Users", newValues, "user_id LIKE ?", arrayOf(curUser.id.toString())) == 1
}

fun updateQuestion(db: SQLiteDatabase, curQuestion: UpdateQuestion): Boolean {
    val newValues = ContentValues().apply {
        if (curQuestion.titleEn != null) put("title_en", curQuestion.titleEn)
        if (curQuestion.textEn != null) put("text_en", curQuestion.textEn)
        if (curQuestion.titleSe != null) put("title_se", curQuestion.titleSe)
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
    return db.update("Questions", newValues, "question_id LIKE ?", arrayOf(curQuestion.id.toString())) == 1
}

fun updateCase(db: SQLiteDatabase,curCase: UpdateCase): Boolean {
    val newValues = ContentValues().apply {
        if (curCase.email != null) put("email", curCase.email)
        if (curCase.gender != null) put("gender", curCase.gender)
        if (curCase.givenNames != null) put("given_names", curCase.givenNames)
        if (curCase.lastName != null) put("last_name", curCase.lastName)
        if (curCase.highRisk != null) put("high_risk", curCase.highRisk)
    }
    return db.update("Cases", newValues, "case_id LIKE ?", arrayOf(curCase.id.toString())) == 1
}

fun updateParent(db: SQLiteDatabase, parent: UpdateParent): Boolean {
    val newValues = ContentValues().apply {
        if (parent.givenNames != null) put("given_names", parent.givenNames)
        if (parent.lastName != null) put("last_name", parent.lastName)
        if (parent.gender != null) put("gender", parent.gender)
        if (parent.highRiskPca != null) put("high_risk_pca", parent.highRiskPca)
        if (parent.highRiskNeglect != null) put("high_risk_neglect", parent.highRiskNeglect)
        if (parent.estHighRiskPca != null) put("est_high_risk_pca", parent.estHighRiskPca)
        if (parent.estHighRiskNeglect != null) put("est_high_risk_neglect", parent.estHighRiskNeglect)
        if (parent.lastChanged != null) put("last_changed", parent.lastChanged)
    }
    return db.update("Parents", newValues, "parent_id LIKE ?", arrayOf(parent.id.toString())) == 1
}

@RequiresApi(Build.VERSION_CODES.O)
fun updateAnswer(db: SQLiteDatabase, answer: UpdateAnswer, parentId: Long): Boolean {
    val newValues = ContentValues().apply {
        put("opt_yes", answer.optYes)
        put("opt_no", answer.optNo)
        put("opt_middle", answer.optMiddle)
    }
    val updatedRows = db.update("Answers", newValues, "answer_id LIKE ?", arrayOf(answer.id.toString()))
    updateParent(db, UpdateParent(id = parentId, lastChanged = LocalDate.now().toString()))
    return (updatedRows == 1)
}

fun setAdmin(db: SQLiteDatabase, newAdminId: Long): Boolean {
    val newValues = ContentValues().apply {
        put("is_admin", true)
    }
    return db.update("Users", newValues, "user_id LIKE ?", arrayOf(newAdminId.toString())) == 1
}

fun resetAdmin(db: SQLiteDatabase, oldAdminId: Long): Boolean {
    val newValues = ContentValues().apply {
        put("is_admin", false)
    }
    return db.update("Users", newValues, "user_id LIKE ? AND username NOT LIKE 'admin'", arrayOf(oldAdminId.toString())) == 1
}