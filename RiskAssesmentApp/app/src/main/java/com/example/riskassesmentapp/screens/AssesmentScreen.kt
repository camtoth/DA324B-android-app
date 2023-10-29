package com.example.riskassesmentapp.screens

import android.database.sqlite.SQLiteDatabase
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.riskassesmentapp.db.Question
import com.example.riskassesmentapp.db.QuestionWithAnswer
import com.example.riskassesmentapp.db.getAllQuestions
import com.example.riskassesmentapp.ui.composables.Assessment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.IDN
import java.util.LinkedList

class AssessmentScreen(private val navController: NavController, private val dbConnection: SQLiteDatabase, private val parentId: Long) {
    private var questionsList = LinkedList<Question>()
    @Composable
    fun Content() {
        FetchQuestionsFromDB()
        println(parentId)
        Assessment(questionsList, parentId, dbConnection) //TODO: get parentId from previous screen
    }

    private fun FetchQuestionsFromDB (){
        runBlocking {
            questionsList = getAllQuestions(dbConnection)
        }
    }
}
