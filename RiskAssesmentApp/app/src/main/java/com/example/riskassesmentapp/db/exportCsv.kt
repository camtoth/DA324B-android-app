package com.example.riskassesmentapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.LinkedList



suspend fun exportDataAsCsv(db: SQLiteDatabase, userId: Long, context: Context) {
    withContext(Dispatchers.IO) {
        var values = getCaseDataAsCsv(db, userId)
        values.addFirst(arrayOf(
            "caseNr",
            "casePersonnr",
            "highRisk",
            "parentPersonnr",
            "highRiskPca",
            "estHighRiskPca",
            "highRiskNeglect",
            "estHighRiskNeglect",
            "lastChanged",
            "questionTitle",
            "questionText",
            "rPca",
            "rNeglect",
            "weightYesPca",
            "weightMiddlePca",
            "weightNoPca",
            "weightYesNeglect",
            "weightMiddleNeglect",
            "weightNoNeglect",
            "answerOptYes",
            "answerOptMiddle",
            "answerOptNo"
        ).joinToString(separator = ", "))
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "riskAssessmentData.csv")
            put(MediaStore.MediaColumns.MIME_TYPE, "text/csv")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }
        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
        if (uri != null) {
            resolver.openOutputStream(uri).use { output ->
                output?.writer()?.buffered()?.use { it.write(values.joinToString(separator = "\n")) }
            }
        }
    }
}

suspend fun getCaseDataAsCsv(db: SQLiteDatabase, userId: Long): LinkedList<String> {
    val csvRows = LinkedList<String>()
    return withContext(Dispatchers.IO) {
        var cases = getCasesByUser(db, userId)
        for (case in cases) {
            var parents = getParentsByCase(db, case.id)
            for (parent in parents) {
                var qWithA = getQuestionsWithAnswerByParent(db, parent.id)
                for (question in qWithA) {
                    csvRows.add(
                        arrayOf(
                            case.caseNr,
                            case.personnr,
                            case.highRisk,
                            parent.personnr,
                            parent.highRiskPca,
                            parent.estHighRiskPca,
                            parent.highRiskNeglect,
                            parent.estHighRiskNeglect,
                            parent.lastChanged,
                            question.titleSe,
                            question.textSe,
                            question.rPca,
                            question.rNeglect,
                            question.weightYesPca,
                            question.weightMiddlePca,
                            question.weightNoPca,
                            question.weightYesNeglect,
                            question.weightMiddleNeglect,
                            question.weightNoNeglect,
                            question.optYes,
                            question.optMiddle,
                            question.optNo
                        ).joinToString(separator = ", ")
                    )
                }
            }
        }
    csvRows
    }
}