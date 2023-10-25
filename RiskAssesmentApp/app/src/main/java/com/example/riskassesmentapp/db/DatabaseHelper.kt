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
                titleSe = "Föräldern ser barnet som ett problem",
                textEn = "To what extent do you see the child as a problem?",
                textSe = "I vilken utsträckning ser du barnet som ett problem? (Sällan[0], Ibland[0.5], Ofta[1]",
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
                titleSe = "Oplanerad graviditet",
                textEn = "Was the pregnancy unplanned?",
                textSe = "Var graviditeten oplanerad? Nej[0], Delvis[0.5], Ja[1]",
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
                titleSe = "Relation mellan förälder och barn",
                textEn = "Does the cooperation between you and the child work well?",
                textSe = "Fungerar samarbetet mellan dig och barnet bra? Ja[1], Delvis[.0.5], Nej[0]",
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
                titleSe = "Användning av aga",
                textEn = "Do you use physical punishment in parenting?",
                textSe = "Använder du fysisk bestraffning i uppfostran? Ja[1], Ibland[0.5], Nej[0]",
                rNeglect = null,
                rPca = 0.26f,
                weightYesNeglect = null,
                weightMiddleNeglect = null,
                weightNoNeglect = null,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            /*insertNewQuestion(db, InsertQuestion(
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
            ))*/
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Stress over parenting",
                titleSe = "Stress över föräldraskap",
                textEn = "Are you stressed about your parenting?",
                textSe = "Är du stressad över ditt föräldraskap? Ja[1], Ibland[0.5], Nej[0]",
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
                titleSe = "Ilska/Hyper-reaktivitet",
                textEn = "Do you have a hot temper?",
                textSe = "Har du ett hett temperament? Ja[1], Ibland[0.5], Nej[0]",
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
                titleSe = "Ångest",
                textEn = "Do you have a mental illness or psychiatric diagnosis?",
                textSe = "Har du en psykisk sjukdom eller psykiatrisk diagnos? Ja[1], Ibland[0.5], Nej[0]",
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
                titleEn = "Psychopathology",
                titleSe = "Psykopatologi ",
                textEn = "Do you have a mental illness or psychiatric diagnosis?",
                textSe = "Har du en psykisk sjukdom eller psykiatrisk diagnos? Ja[1], Ibland[0.5], Nej[0]",
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
                textSe = "Har du en psykisk sjukdom eller psykiatrisk diagnos? Ja[1], Ibland[0.5], Nej[0]",
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
                titleSe = "Självkänsla",
                textEn = "Do you have good self-esteem?",
                textSe = "Har du bra självkänsla? Ja[1], Ibland[0.5], Nej[0]",
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
                titleSe = "Dålig relation med egna föräldrar",
                textEn = "Do you have a bad relationship with your parents?",
                textSe = "Har du en dålig relation till dina föräldrar? Ja[1], Ibland[0.5], Nej[0]",
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
                titleSe = "Förälder utsatt för vanvård som barn",
                textEn = "Were you exposed to neglect as a child?",
                textSe = "Har du som barn blivit utsatt för vanvård? Ja[1], Ibland[0.5], Nej[0]",
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
                titleSe = "Kriminalitet",
                textEn = "Have you been punished for any crime in the last three years?",
                textSe = "Har du blivit straffad för något brott de senaste tre åren? Ja[1], Ibland[0.5], Nej[0]",
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
                titleSe = "Personlig stress",
                textEn = "Are you generally stressed in life?",
                textSe = "Är du i allmänhet stressad i livet? Ja[1], Ibland[0.5], Nej[0]",
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
                titleSe = "Socialt stöd",
                textEn = "Do you have social support in your parenting?",
                textSe = "Har du ett socialt stöd i ditt föräldraskap? Ja[1], Ibland[0.5], Nej[0]",
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
                titleSe = "Alkoholmissbruk",
                textEn = "Has a partner, friend or colleague criticized you for drinking too much alcohol in the last three years?",
                textSe = "Har någon partner, vän eller kollega kritiserat dig för att du dricker för mycket alkohol de senaste tre åren? Ja[1], Ibland[0.5], Nej[0]",
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
                titleSe = "Arbetslöshet",
                textEn = "Are you unemployed?",
                textSe = "Är du arbetslös? Ja[1], Ibland[0.5], Nej[0]",
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
                titleSe = "Hantering och problemlösningsförmåga",
                textEn = "Do you think you have good problem-solving skills with the child?",
                textSe = "Anser du att du har en god problemlösningsförmåga med barnet? Ja[1], Ibland[0.5], Nej[0]",
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
                titleSe = "Ensam förälder",
                textEn = "Are you alone in your parenting?",
                textSe = "Är du ensam i ditt föräldraskap? Ja[1], Ibland[0.5], Nej[0]",
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
                titleSe = "Förälderns ålder",
                textEn = "How old are you? (If the parent is younger than 18, then yes; If the parent is between 18 and 22, then middle, otherwise no)",
                textSe = "Hur gammal är du? [Om föräldern är yngre än 18, så 1; Om föräldern är mellan 18 och 22, så 0.5, annars 0]",
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
                titleSe = "Drogproblem",
                textEn = "Has a partner, friend or colleague criticized you for using too many drugs in the last three years?",
                textSe = "Har någon partner, vän eller kollega kritiserat dig för att du brukar för mycket droger de senaste tre åren? Ja[1], Ibland[0.5], Nej[0]",
                rNeglect = null,
                rPca = 0.08f,
                weightYesNeglect = null,
                weightMiddleNeglect = null,
                weightNoNeglect = null,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            /*insertNewQuestion(db, InsertQuestion(
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
            ))*/
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Child social competence",
                titleSe = "Social kompetens",
                textEn = "Does the child have high social skills?",
                textSe = "Har barnet hög social förmåga? Ja[1], Ibland[0.5], Nej[0]",
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
                titleSe = "Utåtagerande beteende",
                textEn = "Does the child exhibit aggressive behavior?",
                textSe = "Har barnet ett utåtagerande beteende? Ja[1], Ibland[0.5], Nej[0]",
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
                titleSe = "Internaliserande beteende",
                textEn = "Does the child have internalizing behavior?",
                textSe = "Har barnet ett internaliserande beteende? Ja[1], Ibland[0.5], Nej[0]",
                rNeglect = 0.11f,
                rPca = 0.15f,
                weightYesNeglect = 1f,
                weightMiddleNeglect = 0.5f,
                weightNoNeglect = 0f,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            /*insertNewQuestion(db, InsertQuestion(
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
            ))*/
            insertNewQuestion(db, InsertQuestion(
                titleEn = "Family conflict",
                titleSe = "Familjekonflikter",
                textEn = "In the home where you now live, do conflicts often arise between the adults?",
                textSe = "I hemmet där du nu bor i, uppstår det ofta konflikter mellan de vuxna? Ja[1], Ibland[0.5], Nej[0]",
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
                titleSe = "Familjesammanhållning",
                textEn = "In the home where you now live, do you have good cohesion?",
                textSe = "I hemmet där du nu bor i, har ni god sammanhållning? Ja[1], Ibland[0.5], Nej[0]",
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
                titleSe = "Partnervåld",
                textEn = "In the home where you now live, has there been physical partner violence in the last three years?",
                textSe = "I hemmet där du nu bor i, har det förekommit fysiskt partnervåld de senaste tre åren? Ja[1], Ibland[0.5], Nej[0]",
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
                titleSe = "Tillfredsställelse i relationen",
                textEn = "Are you satisfied with the relationship with your current partner?",
                textSe = "Är du nöjd med relationen till din nuvarande partner? Ja[1], Ibland[0.5], Nej[0]",
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
                titleSe = "Familjestorlek",
                textEn = "Are there three or more children in the current family?",
                textSe = "Finns det tre eller flera barn i den nuvarande familjen? Ja[1], Ibland[0.5], Nej[0]",
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
                titleEn = "Socio-economic status",
                titleSe = "Socialekonomisk status",
                textEn = "Does the family belong to the middle or upper class?",
                textSe = "Tillhör familjen medel- eller överklass? Ja[1], Ibland[0.5], Nej[0]",
                rNeglect = -0.19f,
                rPca = -0.14f,
                weightYesNeglect = 1f,
                weightMiddleNeglect = 0.5f,
                weightNoNeglect = 0f,
                weightYesPca = 1f,
                weightMiddlePca = 0.5f,
                weightNoPca = 0f,
            ))
            /*insertNewQuestion(db, InsertQuestion(
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
            ))*/
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

