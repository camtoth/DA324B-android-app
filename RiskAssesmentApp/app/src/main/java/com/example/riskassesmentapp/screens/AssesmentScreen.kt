package com.example.riskassesmentapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.riskassesmentapp.ui.composables.QuestionAndAnswers
import com.example.riskassesmentapp.ui.composables.Title


class AssessmentScreen(private val navController: NavController) {
    @Composable
    fun Content() {
        ActualScreen()
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ActualScreen() {
        Title(title = "Risk Assessment", modifier = Modifier.padding(20.dp))

        LazyColumn {
            items((1..3).toList()) { questionNumber ->
                QuestionAndAnswers(question = "Question $questionNumber")
            }

            item {
                Button(
                    onClick = { /* Handle Submit Assessment */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Submit Assessment")
                }
            }
        }
    }


}