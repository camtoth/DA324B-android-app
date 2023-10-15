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
       Assessment(questionsList)
    }
}
