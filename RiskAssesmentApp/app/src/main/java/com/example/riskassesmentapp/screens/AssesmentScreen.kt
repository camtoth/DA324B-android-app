package com.example.riskassesmentapp.screens

import android.database.sqlite.SQLiteDatabase
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.riskassesmentapp.db.Question
import com.example.riskassesmentapp.db.getAllQuestions
import com.example.riskassesmentapp.ui.composables.Assessment
import kotlinx.coroutines.runBlocking
import java.util.LinkedList

class AssessmentScreen(
    private val navController: NavController,
    private val dbConnection: SQLiteDatabase,
    private val parentId: Long) {
    private var questionsList = LinkedList<Question>()
    @Composable
    fun Content() {
        fetchQuestionsFromDB()
        Assessment(questionsList, parentId, dbConnection, navController)
    }

    private fun fetchQuestionsFromDB (){
        runBlocking {
            questionsList = getAllQuestions(dbConnection)
        }
    }
}
