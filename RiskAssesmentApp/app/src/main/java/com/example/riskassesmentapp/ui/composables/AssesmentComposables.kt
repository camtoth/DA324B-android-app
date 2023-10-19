package com.example.riskassesmentapp.ui.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssessmentScreen() {
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

@Composable
fun QuestionAndAnswers(question: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = question,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val answerOptions = listOf("Answer 1", "Answer 2", "Answer 3")

        for (answer in answerOptions) {
            AnswerChoice(answer)
        }
    }
}

@Composable
fun AnswerChoice(answer: String) {
    var isChecked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { isChecked = it },
            modifier = Modifier.padding(end = 8.dp)
        )

        Text(text = answer)
    }
}

fun onResubmitClick() {
    TODO("Not yet implemented")
}
