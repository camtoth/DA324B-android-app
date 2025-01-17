package com.example.riskassesmentapp.db

import android.database.sqlite.SQLiteDatabase
import androidx.core.database.getFloatOrNull
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

data class Case(
    val id: Long,
    val personnr: String,
    val caseNr: String,
    val email: String,
    val gender: String,
    val givenNames: String,
    val lastName: String,
    val highRisk: Boolean? = null,
)

data class Parent(
    val id: Long,
    val personnr: String,
    val givenNames: String,
    val lastName: String,
    val gender: String,
    val highRiskPca: Boolean? = null,
    val highRiskNeglect: Boolean? = null,
    val estHighRiskPca: Boolean? = null,
    val estHighRiskNeglect: Boolean? = null,
    val lastChanged: String? = null,
    val caseId: Long
)

data class QuestionWithAnswer (
    val parentId: Long,
    val questionId: Long,
    val titleEn: String,
    val textEn: String,
    val titleSe: String,
    val textSe: String,
    val answerId: Long,
    val optYes: Boolean,
    val optMiddle: Boolean,
    val optNo: Boolean,
    val rNeglect: Float? = null,
    val rPca: Float? = null,
    val weightYesNeglect: Float? = null,
    val weightMiddleNeglect: Float? = null,
    val weightNoNeglect: Float? = null,
    val weightYesPca: Float? = null,
    val weightMiddlePca: Float? = null,
    val weightNoPca: Float? = null,
)

suspend fun getQuestionsWithAnswerByParent(db: SQLiteDatabase, parentId: Long): LinkedList<QuestionWithAnswer> {
    return withContext(Dispatchers.IO) {
        val questionsAnswersList = LinkedList<QuestionWithAnswer>()
        val cursorAnswers = db.rawQuery(
            "SELECT Answers.question_id, Answers.opt_yes, Answers.opt_middle, Answers.opt_no, " +
                    "Answers.answer_id, Answers.parent_id, Questions.title_en, Questions.text_en, " +
                    "Questions.title_se, Questions.text_se, Questions.r_neglect, Questions.r_pca, " +
                    "Questions.weight_yes_neglect, Questions.weight_middle_neglect, " +
                    "Questions.weight_no_neglect, Questions.weight_yes_pca, " +
                    "Questions.weight_middle_pca, Questions.weight_no_pca " +
                    "FROM Answers " +
                    "INNER JOIN Questions ON Answers.question_id == Questions.question_id " +
                    "WHERE Answers.parent_id LIKE ?",
            arrayOf(parentId.toString())
        )
        with (cursorAnswers) {
            while (moveToNext()) {
                questionsAnswersList.add(
                    QuestionWithAnswer(
                        parentId = parentId,
                        questionId = getLong(getColumnIndexOrThrow("question_id")),
                        titleEn = getString(getColumnIndexOrThrow("title_en")),
                        textEn = getString(getColumnIndexOrThrow("text_en")),
                        titleSe = getString(getColumnIndexOrThrow("title_se")),
                        textSe = getString(getColumnIndexOrThrow("text_se")),
                        answerId = getLong(getColumnIndexOrThrow("answer_id")),
                        optYes = getInt(getColumnIndexOrThrow("opt_yes")) == 1,
                        optMiddle = getInt(getColumnIndexOrThrow("opt_middle")) == 1,
                        optNo = getInt(getColumnIndexOrThrow("opt_no")) == 1,
                        rNeglect = getFloatOrNull(getColumnIndexOrThrow("r_neglect")),
                        rPca = getFloatOrNull(getColumnIndexOrThrow("r_pca")),
                        weightYesNeglect = getFloatOrNull(getColumnIndexOrThrow("weight_yes_neglect")),
                        weightMiddleNeglect = getFloatOrNull(getColumnIndexOrThrow("weight_middle_neglect")),
                        weightNoNeglect = getFloatOrNull(getColumnIndexOrThrow("weight_no_neglect")),
                        weightYesPca = getFloatOrNull(getColumnIndexOrThrow("weight_yes_pca")),
                        weightMiddlePca = getFloatOrNull(getColumnIndexOrThrow("weight_middle_pca")),
                        weightNoPca = getFloatOrNull(getColumnIndexOrThrow("weight_no_pca")),
                    )
                )
            }
        }
        questionsAnswersList
    }
}

suspend fun getAllQuestions(db: SQLiteDatabase): LinkedList<Question> {
    return withContext(Dispatchers.IO) {
        val questionList = LinkedList<Question>()
        val cursorQuestions = db.rawQuery(
            "SELECT * FROM Questions;",
            null
        )
        with (cursorQuestions) {
            while (moveToNext()) {
                questionList.add(
                    Question(
                        id = getLong(getColumnIndexOrThrow("question_id")),
                        titleEn = getString(getColumnIndexOrThrow("title_en")),
                        textEn = getString(getColumnIndexOrThrow("text_en")),
                        titleSe = getString(getColumnIndexOrThrow("title_se")),
                        textSe = getString(getColumnIndexOrThrow("text_se")),
                        rNeglect = getFloatOrNull(getColumnIndexOrThrow("r_neglect")),
                        rPca = getFloatOrNull(getColumnIndexOrThrow("r_pca")),
                        weightYesNeglect = getFloatOrNull(getColumnIndexOrThrow("weight_yes_neglect")),
                        weightMiddleNeglect = getFloatOrNull(getColumnIndexOrThrow("weight_middle_neglect")),
                        weightNoNeglect = getFloatOrNull(getColumnIndexOrThrow("weight_no_neglect")),
                        weightYesPca = getFloatOrNull(getColumnIndexOrThrow("weight_yes_pca")),
                        weightMiddlePca = getFloatOrNull(getColumnIndexOrThrow("weight_middle_pca")),
                        weightNoPca = getFloatOrNull(getColumnIndexOrThrow("weight_no_pca")),
                    )
                )
            }
        }
        questionList
    }
}

suspend fun getCasesByUser(db: SQLiteDatabase, userId: Long): List<Case> {
    return withContext(Dispatchers.IO) {
        val caseList = LinkedList<Case>()
        val cursorCase = db.rawQuery(
            "SELECT * FROM Cases WHERE user_id LIKE ?;"
            , arrayOf(userId.toString())
        )
        with (cursorCase) {
            while (moveToNext()) {
                var highRisk: Boolean? = null
                val highRiskInt = getIntOrNull(getColumnIndexOrThrow("high_risk"))
                if (highRiskInt != null) highRisk = highRiskInt == 1
                caseList.add(
                    Case(
                        id = getLong(getColumnIndexOrThrow("case_id")),
                        personnr = getString(getColumnIndexOrThrow("personnr")),
                        caseNr = getString(getColumnIndexOrThrow("case_nr")),
                        email = getString(getColumnIndexOrThrow("email")),
                        gender = getString(getColumnIndexOrThrow("gender")),
                        givenNames = getString(getColumnIndexOrThrow("given_names")),
                        lastName = getString(getColumnIndexOrThrow("last_name")),
                        highRisk = highRisk,
                    ))
            }
        }
        caseList
    }
}

suspend fun getParentsByCase(db: SQLiteDatabase, caseId: Long): LinkedList<Parent> {
    return withContext(Dispatchers.IO) {
        val parentsList = LinkedList<Parent>()
        val cursorParents = db.rawQuery(
            "SELECT * " +
                    "FROM Parents " +
                    "WHERE Parents.case_id LIKE ?",
            arrayOf(caseId.toString())
        )
        with (cursorParents) {
            while (moveToNext()) {
                var highRiskPca: Boolean? = null
                val highRiskPcaInt = getIntOrNull(getColumnIndexOrThrow("high_risk_pca"))
                if (highRiskPcaInt != null) highRiskPca = highRiskPcaInt == 1
                var highRiskNeglect: Boolean? = null
                val highRiskNeglectInt = getIntOrNull(getColumnIndexOrThrow("high_risk_neglect"))
                if (highRiskNeglectInt != null) highRiskNeglect = highRiskNeglectInt == 1
                var estHighRiskPca: Boolean? = null
                val estHighRiskPcaInt = getIntOrNull(getColumnIndexOrThrow("est_high_risk_pca"))
                if (estHighRiskPcaInt != null) estHighRiskPca = estHighRiskPcaInt == 1
                var estHighRiskNeglect: Boolean? = null
                val estHighRiskNeglectInt = getIntOrNull(getColumnIndexOrThrow("est_high_risk_neglect"))
                if (estHighRiskNeglectInt != null) estHighRiskNeglect = estHighRiskNeglectInt == 1
                parentsList.add(
                    Parent(
                        id = getLong(getColumnIndexOrThrow("parent_id")),
                        personnr = getString(getColumnIndexOrThrow("personnr")),
                        givenNames = getString(getColumnIndexOrThrow("given_names")),
                        lastName = getString(getColumnIndexOrThrow("last_name")),
                        gender = getString(getColumnIndexOrThrow("gender")),
                        highRiskPca = highRiskPca,
                        highRiskNeglect= highRiskNeglect,
                        estHighRiskPca = estHighRiskPca,
                        estHighRiskNeglect = estHighRiskNeglect,
                        lastChanged = getStringOrNull(getColumnIndexOrThrow("last_changed")),
                        caseId = caseId
                    ))
            }
        }
        parentsList
    }
}

suspend fun loadCase(db: SQLiteDatabase, caseId: Long): Case? {
    return withContext(Dispatchers.IO) {
        var loadedCase: Case? = null
        val cursor = db.rawQuery(
            "SELECT * FROM Cases WHERE case_id = ?;",
            arrayOf(caseId.toString())
        )
        with(cursor) {
            if (moveToFirst()) {
                var highRisk: Boolean? = null
                val highRiskInt = getIntOrNull(getColumnIndexOrThrow("high_risk"))
                if (highRiskInt != null) highRisk = highRiskInt == 1

                loadedCase = Case(
                    id = getLong(getColumnIndexOrThrow("case_id")),
                    personnr = getString(getColumnIndexOrThrow("personnr")),
                    caseNr = getString(getColumnIndexOrThrow("case_nr")),
                    email = getString(getColumnIndexOrThrow("email")),
                    gender = getString(getColumnIndexOrThrow("gender")),
                    givenNames = getString(getColumnIndexOrThrow("given_names")),
                    lastName = getString(getColumnIndexOrThrow("last_name")),
                    highRisk = highRisk,
                )
            }
        }
        loadedCase
    }
}


suspend fun getAllUsernames(db: SQLiteDatabase): List<String> {
    return withContext(Dispatchers.IO) {
        val usernameList = LinkedList<String>()
        val cursor = db.rawQuery("SELECT username FROM Users;", null)
        with (cursor) {
            while (cursor.moveToNext()) {
                usernameList.add(getString(getColumnIndexOrThrow("username")))
            }
        }
        usernameList
    }
}

suspend fun getPwByUsername(db: SQLiteDatabase, username: String): String? {
    return withContext(Dispatchers.IO) {
        var pw: String? = null
        val cursor = db.rawQuery(
            "SELECT pw FROM Users " +
                    "WHERE username LIKE ?;",
            arrayOf(username)
        )
        with (cursor) {
            while (cursor.moveToNext()) {
                pw = (getString(getColumnIndexOrThrow("pw")))
            }
        }
        pw
    }
}

suspend fun getUserByUsername(db: SQLiteDatabase, username: String): User? {
    return withContext(Dispatchers.IO) {
        var user: User? = null
        val cursor = db.rawQuery(
            "SELECT * FROM Users " +
                    "WHERE username LIKE ?;",
            arrayOf(username)
        )
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
        user
    }
}
