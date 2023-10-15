package com.example.riskassesmentapp.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.riskassesmentapp.db.QuestionWithAnswer
import com.example.riskassesmentapp.ui.theme.RiskAssesmentAppTheme
import java.util.LinkedList

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

@Composable
fun Assessment(questionsList : LinkedList<QuestionWithAnswer>) {
    RiskAssesmentAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(5) { i ->
                        QuestionCard()
                    }
                    item {
                        Button(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .height(40.dp)
                                .fillMaxWidth(1f),
                            onClick = {
                                /*TODO*/
                            }) {
                            Text(text = "Show result")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RadioButton() {
    val radioOptions = listOf("A", "B", "C")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[1] ) }
    Column {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                        }
                    )
                    .padding(horizontal = 16.dp)
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = { onOptionSelected(text) }
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

@Composable
fun QuestionCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier
        .padding(16.dp)
    ){
        Column {
            Text(
                text = "Example question",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineLarge
            )
            Row(){
                RadioButton()
            }
        }
    }
}

