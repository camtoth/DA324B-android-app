package com.example.riskassesmentapp.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.riskassesmentapp.db.QuestionWithAnswer
import com.example.riskassesmentapp.ui.composables.Assessment
import java.util.LinkedList

class AssessmentScreen(private val navController: NavController) {
    private val questionsList = LinkedList<QuestionWithAnswer>()
    @Composable
    fun Content() {
        questionsList.add(QuestionWithAnswer(questionId = 34134, textEn = "To what extent do you see the child as a problem?", textSe = "Swedish lul", rNeglect = 0.2f,  rPca = 0.7f))
        questionsList.add(QuestionWithAnswer(questionId = 45554, textEn = "Was the pregnancy unplanned?", textSe = "Swedish lul", rNeglect = 0.2f,  rPca = 0.7f))
        questionsList.add(QuestionWithAnswer(questionId = 77134, textEn = "Does the cooperation between you and the child work well?", textSe = "Swedish lul", rNeglect = 0.2f,  rPca = 0.7f))
        questionsList.add(QuestionWithAnswer(questionId = 22168, textEn = "Do you use physical punishment in parenting?", textSe = "Swedish lul", rNeglect = 0.2f,  rPca = 0.7f))
        questionsList.add(QuestionWithAnswer(questionId = 98765, textEn = "Are you stressed about your parenting?", textSe = "Swedish lul", rNeglect = 0.2f,  rPca = 0.7f))
        Assessment(questionsList)
    }
}
