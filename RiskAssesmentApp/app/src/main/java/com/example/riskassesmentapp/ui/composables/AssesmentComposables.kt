package com.example.riskassesmentapp.ui.composables

import android.database.sqlite.SQLiteDatabase
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.riskassesmentapp.db.InsertAnswer
import com.example.riskassesmentapp.db.Question
import com.example.riskassesmentapp.db.QuestionWithAnswer
import com.example.riskassesmentapp.db.insertNewAnswer
import com.example.riskassesmentapp.ui.theme.RiskAssesmentAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.LinkedList

@Composable
fun Assessment(questionsList : LinkedList<Question>, parentId: Long, dbConnection: SQLiteDatabase) {
    val showDialog = remember { mutableStateOf(false) }
    val answersMap = HashMap<Long, InsertAnswer>()
    var scope = rememberCoroutineScope()

    RiskAssesmentAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            if (showDialog.value) {
                DialogExamples(showDialog)
            }
            Column {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(questionsList.size){ i ->
                        QuestionCard(questionsList[i], answersMap)
                    }
                    item {
                        Button(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .height(40.dp)
                                .fillMaxWidth(1f),
                            onClick = {
                                showDialog.value = true
                                SendAnswersToDB(dbConnection, scope, answersMap, parentId)
                            }) {
                            Text(text = "Show result")
                        }
                    }
                }
            }
        }
    }
}

fun SendAnswersToDB(dbConnection: SQLiteDatabase, scope: CoroutineScope, answersMap: HashMap<Long, InsertAnswer>, parentId: Long) {
    for(answer in answersMap) {
        scope.launch {
            insertNewAnswer(dbConnection, answer.value, answer.key, parentId)
        }
    }
}

@Composable
fun QuestionCard(question: Question, answersMap: HashMap<Long, InsertAnswer>,modifier: Modifier = Modifier) {
    Card(modifier = modifier
        .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ){
        Column {
            Text(
                text = question.textEn,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineLarge
            )
            Row {
                RadioButton(question.id, answersMap)
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

@Composable
fun RadioButton(questionId: Long, answersMap: HashMap<Long, InsertAnswer>) {
    val radioOptions = listOf("Yes", "Middle", "No")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0] ) }
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
                    onClick = {
                        onOptionSelected(text)
                        answersMap[questionId] = AnswerStringToInsertAnswer(text)
                    }
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

fun AnswerStringToInsertAnswer(stringAnswer: String) : InsertAnswer{ //TODO: remove hardcoded stringAnswer and use the correctly localized version
    val newInsertAnswer: InsertAnswer = when (stringAnswer) {
        "Yes" -> InsertAnswer(optYes = true, optMiddle = false, optNo = false)
        "Middle" -> InsertAnswer(optYes = false, optMiddle = true, optNo = false)
        else -> InsertAnswer(optYes = false, optMiddle = false, optNo = true)
    }
    return newInsertAnswer
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
    ) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Composable
fun DialogExamples(dialogIsOpen: MutableState<Boolean>) {
    // ...
    when {
        // ...
        dialogIsOpen.value -> {
            AlertDialogExample(
                onDismissRequest = { dialogIsOpen.value = false },
                onConfirmation = {
                    dialogIsOpen.value = false
                    println("Confirmation registered") // Add logic here to handle confirmation.
                },
                dialogTitle = "Alert dialog example",
                dialogText = "This is an example of an alert dialog with buttons.",
                icon = Icons.Default.Info
            )
        }
    }
}
