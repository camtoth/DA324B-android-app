package com.example.riskassesmentapp.db

import android.database.sqlite.SQLiteDatabase
import androidx.core.database.getFloatOrNull
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
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
    val parents: LinkedList<Parent>,
    val highRisk: Boolean? = null,
)

data class Parent(
    val id: Long,
    val givenNames: String,
    val lastName: String,
    val gender: String
)

data class QuestionWithAnswer (
    val questionId: Long,
    val titleEn: String,
    val textEn: String,
    val titleSe: String,
    val textSe: String,
    val answerId: Long,
    val optYes: Boolean,
    val optMiddle: Boolean,
    val optNo: Boolean,
    val parentNo: Int,
    val rNeglect: Float? = null,
    val rPca: Float? = null,
    val weightYesNeglect: Float? = null,
    val weightMiddleNeglect: Float? = null,
    val weightNoNeglect: Float? = null,
    val weightYesPca: Float? = null,
    val weightMiddlePca: Float? = null,
    val weightNoPca: Float? = null,
)

data class Answer(
    val id: Long,
    val optYes: Boolean,
    val optMiddle: Boolean,
    val optNo: Boolean,
    val lastChanged: Date,
    val parentNo: Int,
    val caseId: Int,
    val questionId: Int
)

fun getQuestionsWithAnswerByCaseId(db: SQLiteDatabase, caseId: Long): LinkedList<QuestionWithAnswer> {
    val questionsAnswersList = LinkedList<QuestionWithAnswer>()
    val cursorAnswers = db.rawQuery(
        "SELECT Answers.question_id, Answers.opt_yes, Answers.opt_middle, Answers.opt_no, " +
                "Answers.answer_id, Answers.parent_no, " +
                "Questions.text_en, Questions.text_se, Questions.r_neglect, Questions.r_pca, " +
                "Questions.weight_yes_neglect, Questions.weight_middle_neglect, Questions.weight_no_neglect, " +
                "Questions.weight_yes_pca, Questions.weight_middle_pca, Questions.weight_no_pca " +
                "FROM Answers " +
                "INNER JOIN Questions ON Answers.question_id == Questions.question_id " +
                "WHERE Answers.case_id LIKE ?",
        arrayOf(caseId.toString())
    )
    with (cursorAnswers) {
        while (moveToNext()) {
            questionsAnswersList.add(
                QuestionWithAnswer(
                questionId = getLong(getColumnIndexOrThrow("question_id")),
                titleEn = getString(getColumnIndexOrThrow("title_en")),
                textEn = getString(getColumnIndexOrThrow("text_en")),
                titleSe = getString(getColumnIndexOrThrow("title_se")),
                textSe = getString(getColumnIndexOrThrow("text_se")),
                answerId = getLong(getColumnIndexOrThrow("answer_id")),
                optYes = getInt(getColumnIndexOrThrow("opt_yes")) == 1,
                optMiddle = getInt(getColumnIndexOrThrow("opt_middle")) == 1,
                optNo = getInt(getColumnIndexOrThrow("opt_no")) == 1,
                parentNo = getInt(getColumnIndexOrThrow("parent_no")),
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
    return questionsAnswersList
}

fun getAllQuestions(db: SQLiteDatabase): LinkedList<Question> {
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
    return questionList
}

fun getCasesByUser(db: SQLiteDatabase, userId: Long): List<Case> {
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
                parents = LinkedList<Parent>(),
            )
            )
        }
    }
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
                case.parents.add(
                    Parent(
                    getLong(getColumnIndexOrThrow("parent_id")),
                    getString(getColumnIndexOrThrow("given_names")),
                    getString(getColumnIndexOrThrow("last_name")),
                    getString(getColumnIndexOrThrow("gender")),
                )
                )
            }
        }
    }
    return caseList
}

fun getAllUsernames(db: SQLiteDatabase): List<String> {
    val usernameList = LinkedList<String>()
    val cursor = db.rawQuery("SELECT username FROM Users;", null)
    with (cursor) {
        while (cursor.moveToNext()) {
            usernameList.add(getString(getColumnIndexOrThrow("username")))
        }
    }
    return usernameList
}

fun getPwByUsername(db: SQLiteDatabase, username: String): String? {
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
    return pw
}

fun getUserByUsername(db: SQLiteDatabase, username: String): User? {
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
    return user
}
