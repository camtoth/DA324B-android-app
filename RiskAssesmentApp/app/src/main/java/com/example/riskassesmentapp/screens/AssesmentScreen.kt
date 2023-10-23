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
import kotlinx.coroutines.launch
import java.net.IDN
import java.util.LinkedList

class AssessmentScreen(private val navController: NavController, private val dbConnection: SQLiteDatabase) {
    private var questionsList = LinkedList<Question>()
    @Composable
    fun Content() {
        var scope = rememberCoroutineScope()
        questionsList.add(Question(id = 0, titleEn = "Question title", textEn = "To what extent do you see the child as a problem?", titleSe = "no hablo espaNol", textSe = "Swedish lul"))
        questionsList.add(Question(id = 1, titleEn = "Question title", textEn = "Was the pregnancy unplanned?", titleSe = "no hablo espaNol", textSe = "Swedish lul", rNeglect = 0.2f,  rPca = 0.7f))
        questionsList.add(Question(id = 2, titleEn = "Question title", textEn = "Does the cooperation between you and the child work well?", titleSe = "no hablo espaNol", textSe = "Swedish lul", rNeglect = 0.2f,  rPca = 0.7f))
        questionsList.add(Question(id = 3, titleEn = "Question title", textEn = "Do you use physical punishment in parenting?", titleSe = "no hablo espaNol", textSe = "Swedish lul", rNeglect = 0.2f,  rPca = 0.7f))
        //FetchQuestionsFromDB(scope)
        Assessment(questionsList, 0, dbConnection) //TODO: get questions from DB and parentId from previous screen
    }

    private fun FetchQuestionsFromDB (scope: CoroutineScope){
        scope.launch {
            questionsList = getAllQuestions(dbConnection)
        }
    }
}
