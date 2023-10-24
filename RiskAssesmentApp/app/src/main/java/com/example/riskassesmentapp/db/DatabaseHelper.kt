package com.example.riskassesmentapp.db


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.riskassesmentapp.screens.hashPassword
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DatabaseOpenHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DatabaseContract.SQL_CREATE_QUESTIONS)
        db.execSQL(DatabaseContract.SQL_CREATE_USERS)
        db.execSQL(DatabaseContract.SQL_CREATE_CASES)
        db.execSQL(DatabaseContract.SQL_CREATE_PARENTS)
        db.execSQL(DatabaseContract.SQL_CREATE_ANSWERS)
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            insertNewUser(db, InsertUser("admin", hashPassword("admin"), "admin", "admin", true))
            // Insert questions here
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Parent perceives child as problem",
                titleSe = "Parent perceives child as problem",
                textEn = "To what extent do you see the child as a problem?",
                textSe = "To what extent do you see the child as a problem?",
                rNeglect = 0.41f,
                rPca = 0.3f,
                weightYesNeglect = 1f,
                weightMiddleNeglect = 0.5f,
                weightNoNeglect = 0f,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Unplanned pregnancy",
                titleSe = "Unplanned pregnancy",
                textEn = "Was the pregnancy unplanned?",
                textSe = "Was the pregnancy unplanned?",
                rNeglect = null,
                rPca = 0.28f,
                weightYesNeglect = null,
                weightMiddleNeglect = null,
                weightNoNeglect = null,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Parent–child relationships",
                titleSe = "Parent–child relationships",
                textEn = "Does the cooperation between you and the child work well?",
                textSe = "Does the cooperation between you and the child work well?",
                rNeglect = -0.48f,
                rPca = -0.27f,
                weightYesNeglect = 1f,
                weightMiddleNeglect = 0.5f,
                weightNoNeglect = 0f,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Parent use of corporal punishment",
                titleSe = "Parent use of corporal punishment",
                textEn = "Do you use physical punishment in parenting?",
                textSe = "Do you use physical punishment in parenting?",
                rNeglect = null,
                rPca = 0.26f,
                weightYesNeglect = null,
                weightMiddleNeglect = null,
                weightNoNeglect = null,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Parenting behavior",
                titleSe = "Parenting behavior",
                textEn = "MISSING",
                textSe = "MISSING",
                rNeglect = 0.18f,
                rPca = 0.17f,
                weightYesNeglect = 1f,
                weightMiddleNeglect = 0.5f,
                weightNoNeglect = 0f,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Stress over parenting",
                titleSe = "Stress over parenting",
                textEn = "Are you stressed about your parenting?",
                textSe = "Are you stressed about your parenting?",
                rNeglect = 0.14f,
                rPca = 0.07f,
                weightYesNeglect = 1f,
                weightMiddleNeglect = 0.5f,
                weightNoNeglect = 0f,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Anger/hyper-reactivity",
                titleSe = "Anger/hyper-reactivity",
                textEn = "Do you have a hot temper?",
                textSe = "Do you have a hot temper?",
                rNeglect = 0.35f,
                rPca = 0.34f,
                weightYesNeglect = 1f,
                weightMiddleNeglect = 0.5f,
                weightNoNeglect = 0f,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Anxiety",
                titleSe = "Anxiety",
                textEn = "Do you have a mental illness or psychiatric diagnosis?",
                textSe = "Do you have a mental illness or psychiatric diagnosis?",
                rNeglect = null,
                rPca = 0.29f,
                weightYesNeglect = null,
                weightMiddleNeglect = null,
                weightNoNeglect = null,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Psychopathology ",
                titleSe = "Psychopathology ",
                textEn = "Do you have a mental illness or psychiatric diagnosis?",
                textSe = "Do you have a mental illness or psychiatric diagnosis?",
                rNeglect = 0.25f,
                rPca = 0.28f,
                weightYesNeglect = 1f,
                weightMiddleNeglect = 0.5f,
                weightNoNeglect = 0f,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Depression",
                titleSe = "Depression",
                textEn = "Do you have a mental illness or psychiatric diagnosis?",
                textSe = "Do you have a mental illness or psychiatric diagnosis?",
                rNeglect = 0.21f,
                rPca = 0.27f,
                weightYesNeglect = 1f,
                weightMiddleNeglect = 0.5f,
                weightNoNeglect = 0f,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Self-esteem",
                titleSe = "Self-esteem",
                textEn = "Do you have good self-esteem?",
                textSe = "Do you have good self-esteem?",
                rNeglect = -0.33f,
                rPca = -0.24f,
                weightYesNeglect = 1f,
                weightMiddleNeglect = 0.5f,
                weightNoNeglect = 0f,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Poor relationship with own parents",
                titleSe = "Poor relationship with own parents",
                textEn = "Do you have a bad relationship with your parents?",
                textSe = "Do you have a bad relationship with your parents?",
                rNeglect = 0.19f,
                rPca = 0.22f,
                weightYesNeglect = 1f,
                weightMiddleNeglect = 0.5f,
                weightNoNeglect = 0f,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Parent experienced childhood abuse",
                titleSe = "Parent experienced childhood abuse",
                textEn = "Were you exposed to neglect as a child?",
                textSe = "Were you exposed to neglect as a child?",
                rNeglect = 0.15f,
                rPca = 0.21f,
                weightYesNeglect = 1f,
                weightMiddleNeglect = 0.5f,
                weightNoNeglect = 0f,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Criminal behaviors",
                titleSe = "Criminal behaviors",
                textEn = "Have you been punished for any crime in the last three years?",
                textSe = "Have you been punished for any crime in the last three years?",
                rNeglect = null,
                rPca = 0.21f,
                weightYesNeglect = null,
                weightMiddleNeglect = null,
                weightNoNeglect = null,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Personal stress",
                titleSe = "Personal stress",
                textEn = "Are you generally stressed in life?",
                textSe = "Are you generally stressed in life?",
                rNeglect = 0.38f,
                rPca = 0.19f,
                weightYesNeglect = 1f,
                weightMiddleNeglect = 0.5f,
                weightNoNeglect = 0f,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Social support",
                titleSe = "Social support",
                textEn = "Do you have social support in your parenting?",
                textSe = "Do you have social support in your parenting?",
                rNeglect = -0.16f,
                rPca = -0.18f,
                weightYesNeglect = 1f,
                weightMiddleNeglect = 0.5f,
                weightNoNeglect = 0f,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Alcohol abuse",
                titleSe = "Alcohol abuse",
                textEn = "Has a partner, friend or colleague criticized you for drinking too much alcohol in the last three years?",
                textSe = "Has a partner, friend or colleague criticized you for drinking too much alcohol in the last three years?",
                rNeglect = null,
                rPca = 0.17f,
                weightYesNeglect = null,
                weightMiddleNeglect = null,
                weightNoNeglect = null,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Unemployment",
                titleSe = "Unemployment",
                textEn = "Are you unemployed?",
                textSe = "Are you unemployed?",
                rNeglect = 0.25f,
                rPca = 0.15f,
                weightYesNeglect = 1f,
                weightMiddleNeglect = 0.5f,
                weightNoNeglect = 0f,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Parent coping and problem-solving skills",
                titleSe = "Parent coping and problem-solving skills",
                textEn = "Do you think you have good problem-solving skills with the child?",
                textSe = "Do you think you have good problem-solving skills with the child?",
                rNeglect = null,
                rPca = -0.14f,
                weightYesNeglect = null,
                weightMiddleNeglect = null,
                weightNoNeglect = null,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Single parenthood",
                titleSe = "Single parenthood",
                textEn = "Are you alone in your parenting?",
                textSe = "Are you alone in your parenting?",
                rNeglect = 0.08f,
                rPca = 0.12f,
                weightYesNeglect = 1f,
                weightMiddleNeglect = 0.5f,
                weightNoNeglect = 0f,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Parent age",
                titleSe = "Parent age",
                textEn = "How old are you? (If the parent is younger than 18, then yes; If the parent is between 18 and 22, then middle, otherwise no)",
                textSe = "How old are you? (If the parent is younger than 18, then yes; If the parent is between 18 and 22, then middle, otherwise no)",
                rNeglect = -0.12f,
                rPca = -0.1f,
                weightYesNeglect = 1f,
                weightMiddleNeglect = 0.5f,
                weightNoNeglect = 0f,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Drug abuse",
                titleSe = "Drug abuse",
                textEn = "Has a partner, friend or colleague criticized you for using too many drugs in the last three years?",
                textSe = "Has a partner, friend or colleague criticized you for using too many drugs in the last three years?",
                rNeglect = null,
                rPca = 0.08f,
                weightYesNeglect = null,
                weightMiddleNeglect = null,
                weightNoNeglect = null,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Health problems",
                titleSe = "Health problems",
                textEn = "Do you have problems with your health?",
                textSe = "Do you have problems with your health?",
                rNeglect = null,
                rPca = 0.11f,
                weightYesNeglect = null,
                weightMiddleNeglect = null,
                weightNoNeglect = null,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Parent gender",
                titleSe = "Parent gender",
                textEn = "Are you male?",
                textSe = "Are you male?",
                rNeglect = null,
                rPca = 0.7f,
                weightYesNeglect = null,
                weightMiddleNeglect = null,
                weightNoNeglect = null,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Approval of corporal punishment",
                titleSe = "Approval of corporal punishment",
                textEn = "Do you approve of physical punishment?",
                textSe = "Do you approve of physical punishment?",
                rNeglect = null,
                rPca = 0.05f,
                weightYesNeglect = null,
                weightMiddleNeglect = null,
                weightNoNeglect = null,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Child social competence",
                titleSe = "Child social competence",
                textEn = "Does the child have high social skills?",
                textSe = "Does the child have high social skills?",
                rNeglect = -0.3f,
                rPca = -0.26f,
                weightYesNeglect = 1f,
                weightMiddleNeglect = 0.5f,
                weightNoNeglect = 0f,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Child externalizing behaviors",
                titleSe = "Child externalizing behaviors",
                textEn = "Does the child exhibit aggressive behavior?",
                textSe = "Does the child exhibit aggressive behavior?",
                rNeglect = 0.22f,
                rPca = 0.23f,
                weightYesNeglect = 1f,
                weightMiddleNeglect = 0.5f,
                weightNoNeglect = 0f,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Child internalizing behaviors",
                titleSe = "Child internalizing behaviors",
                textEn = "Does the child have internalizing behavior?",
                textSe = "Does the child have internalizing behavior?",
                rNeglect = 0.11f,
                rPca = 0.15f,
                weightYesNeglect = 1f,
                weightMiddleNeglect = 0.5f,
                weightNoNeglect = 0f,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Child gender",
                titleSe = "Child gender",
                textEn = "Is the child male?",
                textSe = "Is the child male?",
                rNeglect = 0.01f,
                rPca = 0.04f,
                weightYesNeglect = 1f,
                weightMiddleNeglect = 0.5f,
                weightNoNeglect = 0f,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Prenatal or neonatal problems",
                titleSe = "Prenatal or neonatal problems",
                textEn = "Have there been problems before or shortly after childbirth?",
                textSe = "Have there been problems before or shortly after childbirth?",
                rNeglect = null,
                rPca = 0.04f,
                weightYesNeglect = null,
                weightMiddleNeglect = null,
                weightNoNeglect = null,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Child disability",
                titleSe = "Child disability",
                textEn = "Is the child disabled?",
                textSe = "Is the child disabled?",
                rNeglect = null,
                rPca = 0.01f,
                weightYesNeglect = null,
                weightMiddleNeglect = null,
                weightNoNeglect = null,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Child age",
                titleSe = "Child age",
                textEn = "How old is the child? (If below 10 pick yes; If between 10 and 14 pick middle; If above 14 pick no)",
                textSe = "How old is the child? (If below 10 pick yes; If between 10 and 14 pick middle; If above 14 pick no)",
                rNeglect = -0.01f,
                rPca = -0.02f,
                weightYesNeglect = 1f,
                weightMiddleNeglect = 0.5f,
                weightNoNeglect = 0f,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Family conflict",
                titleSe = "Family conflict",
                textEn = "In the home where you now live, do conflicts often arise between the adults?",
                textSe = "In the home where you now live, do conflicts often arise between the adults?",
                rNeglect = null,
                rPca = 0.39f,
                weightYesNeglect = null,
                weightMiddleNeglect = null,
                weightNoNeglect = null,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Family cohesion",
                titleSe = "Family cohesion",
                textEn = "In the home where you now live, do you have good cohesion?",
                textSe = "In the home where you now live, do you have good cohesion?",
                rNeglect = null,
                rPca = -0.32f,
                weightYesNeglect = null,
                weightMiddleNeglect = null,
                weightNoNeglect = null,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Spousal violence",
                titleSe = "Spousal violence",
                textEn = "In the home where you now live, has there been physical partner violence in the last three years?",
                textSe = "In the home where you now live, has there been physical partner violence in the last three years?",
                rNeglect = null,
                rPca = 0.22f,
                weightYesNeglect = null,
                weightMiddleNeglect = null,
                weightNoNeglect = null,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Marital satisfaction",
                titleSe = "Marital satisfaction",
                textEn = "Are you satisfied with the relationship with your current partner?",
                textSe = "Are you satisfied with the relationship with your current partner?",
                rNeglect = null,
                rPca = -0.16f,
                weightYesNeglect = null,
                weightMiddleNeglect = null,
                weightNoNeglect = null,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Family size",
                titleSe = "Family size",
                textEn = "Are there three or more children in the current family?",
                textSe = "Are there three or more children in the current family?",
                rNeglect = 0.26f,
                rPca = 0.15f,
                weightYesNeglect = 1f,
                weightMiddleNeglect = 0.5f,
                weightNoNeglect = 0f,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Socio-economic status ",
                titleSe = "Socio-economic status ",
                textEn = "Does the family belong to the middle or upper class?",
                textSe = "Does the family belong to the middle or upper class?",
                rNeglect = -0.19f,
                rPca = -0.14f,
                weightYesNeglect = 1f,
                weightMiddleNeglect = 0.5f,
                weightNoNeglect = 0f,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Non-biological parent in home",
                titleSe = "Non-biological parent in home",
                textEn = "In the home where you now live, does a non-biological parent live with the child?",
                textSe = "In the home where you now live, does a non-biological parent live with the child?",
                rNeglect = null,
                rPca = -0.03f,
                weightYesNeglect = null,
                weightMiddleNeglect = null,
                weightNoNeglect = null,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            val caseId = insertNewCase(db, InsertCase(
                personnr = "1",
                caseNr = "1",
                email = "1",
                gender = "M",
                givenNames = "1",
                lastName = "1"
            ), 1)
            val parent1 = insertNewParent(db, InsertParent(
                personnr = "2",
                givenNames = "2",
                lastName = "2",
                gender = "m"
            ), caseId)
            val parent2 = insertNewParent(db, InsertParent(
                personnr = "3",
                givenNames = "3",
                lastName = "3",
                gender = "f"
            ), caseId)
            insertNewAnswer(db, InsertAnswer(true, false, false), 1,parent1)
            insertNewAnswer(db, InsertAnswer(false, true, false), 2,parent1)
            insertNewAnswer(db, InsertAnswer(false, false, true), 1,parent2)
        }
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "RiskAssessmentPrototype.db"
    }
}

