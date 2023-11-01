package com.example.riskassesmentapp.ui.composables

import android.database.sqlite.SQLiteDatabase
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.riskassesmentapp.db.InsertAnswer
import com.example.riskassesmentapp.db.InsertParent
import com.example.riskassesmentapp.db.Question
import com.example.riskassesmentapp.db.UpdateAnswer
import com.example.riskassesmentapp.db.UpdateParent
import com.example.riskassesmentapp.db.getQuestionsWithAnswerByParent
import com.example.riskassesmentapp.db.insertNewAnswer
import com.example.riskassesmentapp.db.updateAnswer
import com.example.riskassesmentapp.db.updateParent
import com.example.riskassesmentapp.ui.theme.RiskAssesmentAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.LinkedList

@Composable
fun Assessment(
    questionsList : LinkedList<Question>,
    parentId: Long,
    dbConnection: SQLiteDatabase,
    navController: NavController) {
    val showDialog = remember { mutableStateOf(false) }
    val answersLoaded = remember { mutableStateOf(false) }
    val answersMap = remember { mutableStateOf(HashMap<Long, UpdateAnswer>()) }
    var predPca by remember { mutableStateOf(false) }
    var predNeg by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    if (!answersLoaded.value) {
        runBlocking {
            answersMap.value = getAnswerMap(dbConnection, parentId, questionsList)
            answersLoaded.value = true
        }
    }

    RiskAssesmentAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            if (showDialog.value) {
                DialogExamples(showDialog,
                    answersMap = answersMap.value,
                    scope = scope,
                    parentId = parentId,
                    dbConnection = dbConnection,
                    navController = navController,
                    predPca = predPca,
                    predNeg = predNeg)
            }
            Column {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    item{
                        predPca = PredCard("rPca", predPca)
                        predNeg = PredCard("rNeg", predNeg)
                    }
                    items(questionsList.size){ i ->
                        QuestionCard(questionsList[i], answersMap.value)
                    }
                    item {
                        Button(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .height(40.dp)
                                .fillMaxWidth(1f),
                            onClick = {
                                showDialog.value = true
                            }) {
                            Text(text = "Spara och visa resultat")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PredCard(toEvaluate : String, previousAnswer : Boolean) : Boolean {
    val radioOptions = listOf("Ja", "Nej")
    var previousAnswerIndex : Int = if(previousAnswer) {
        0
    } else {
        1
    }
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[previousAnswerIndex] ) }
    var questionText : String = if (toEvaluate == "rPca"){
        "Tycker du att det finns högt risk för fysisk skada?"
    } else {
        "Tycker du att det finns högt risk för försummelse?"
    }

    Card(
        Modifier
        .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
        ){
        Column {
            Text(
                text = questionText,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineLarge
            )
            Row {
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
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.onPrimary,
                                    unselectedColor = MaterialTheme.colorScheme.onTertiary,
                                ),
                                onClick = {
                                    onOptionSelected(text)
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
        }
    }
    return (selectedOption=="Ja")
}

suspend fun getAnswerMap(
    dbConnection: SQLiteDatabase,
    parentId: Long,
    questionsList: LinkedList<Question>)
    : HashMap<Long, UpdateAnswer> {
    var answerMap = HashMap<Long, UpdateAnswer>()
    val questionsWithAnswers = getQuestionsWithAnswerByParent(dbConnection, parentId)

    if (questionsWithAnswers.size == 0) answerMap = handleNotAnsweredQuestions(dbConnection, parentId, questionsList)
    else {
        for (qWithA in questionsWithAnswers) {
            answerMap[qWithA.questionId] = UpdateAnswer(
                id = qWithA.answerId,
                optYes = qWithA.optYes,
                optMiddle = qWithA.optMiddle,
                optNo = qWithA.optNo
            )
        }
    }
    return answerMap
}

suspend fun insertPredictions(parent: UpdateParent, dbConnection: SQLiteDatabase){
    updateParent(dbConnection, parent)
}

suspend fun handleNotAnsweredQuestions(
    dbConnection: SQLiteDatabase,
    parentId: Long,
    questionsList: LinkedList<Question>
    ) : HashMap<Long, UpdateAnswer> {
    val answerMap = HashMap<Long, UpdateAnswer>()

    for (question in questionsList) {
        val newAnswerId = insertNewAnswer(
            db = dbConnection,
            newAnswer = InsertAnswer(optYes = false, optMiddle = true, optNo = false),
            questionId = question.id,
            parentId = parentId)
        answerMap[question.id] = UpdateAnswer(id = newAnswerId, optYes = false, optMiddle = true, optNo = false)
    }
    return answerMap
}

suspend fun sendAnswersToDB(
    dbConnection: SQLiteDatabase,
    answersMap: HashMap<Long,UpdateAnswer>,
    parentId: Long
    ) {
    for(answer in answersMap) {
        updateAnswer(dbConnection, answer.value, parentId)
    }
}

@Composable
fun QuestionCard(
    question: Question,
    answersMap: HashMap<Long, UpdateAnswer>,
    modifier: Modifier = Modifier
    ) {
    Card(modifier = modifier
        .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ){
        Column {
            Text(
                text = question.textSe,
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
fun RadioButton(
    questionId: Long,
    answersMap: HashMap<Long, UpdateAnswer>,
    selectedOptionIndex : Int = 1
    ) {

    val answerChanged = remember { mutableStateOf(false) }
    val radioOptions = listOf("-1", "0", "1")
    var selectedOptionFromMap = -1

    if(answersMap[questionId] != null && !answerChanged.value) {
        selectedOptionFromMap = getAnswerIndex(answersMap[questionId]);
    }
    if(selectedOptionFromMap == -1) {
        selectedOptionFromMap = selectedOptionIndex
    }
    //if(!answerChanged.value) answersMap[questionId] = answerStringToUpdateAnswer(radioOptions[selectedOptionFromMap], answersMap[questionId]!!.id)
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[selectedOptionFromMap] ) }
    Column {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            answersMap[questionId] =
                                answerStringToUpdateAnswer(text, answersMap[questionId]!!.id)
                            answerChanged.value = true
                        }
                    )
                    .padding(horizontal = 16.dp)
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedColor = MaterialTheme.colorScheme.onTertiary,
                    ),
                    onClick = {
                        onOptionSelected(text)
                        answersMap[questionId] = answerStringToUpdateAnswer(text, answersMap[questionId]!!.id)
                        answerChanged.value = true
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

fun answerStringToUpdateAnswer(stringAnswer: String, answerId: Long) : UpdateAnswer{
    val newUpdateAnswer: UpdateAnswer = when (stringAnswer) {
        "1" -> UpdateAnswer(id = answerId, optYes = true, optMiddle = false, optNo = false)
        "0" -> UpdateAnswer(id = answerId, optYes = false, optMiddle = true, optNo = false)
        else -> UpdateAnswer(id = answerId, optYes = false, optMiddle = false, optNo = true)
    }
    return newUpdateAnswer
}

fun getAnswerIndex(answer: UpdateAnswer?) : Int{
    if(answer == null) {
        return -1
    }
    val index: Int = if(answer.optNo) {
        0;
    } else if(answer.optYes) {
        2;
    } else {
        1;
    }
    return index
}

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
                Text("Bekräfta")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Avbruta")
            }
        }
    )
}

@Composable
fun DialogExamples(
    dialogIsOpen: MutableState<Boolean>,
    dbConnection: SQLiteDatabase,
    scope: CoroutineScope,
    answersMap: HashMap<Long, UpdateAnswer>,
    parentId: Long,
    predPca: Boolean,
    predNeg: Boolean,
    navController: NavController
    ) {
    // ...
    when {
        // ...
        dialogIsOpen.value -> {
            AlertDialogExample(
                onDismissRequest = { dialogIsOpen.value = false },
                onConfirmation = {
                    dialogIsOpen.value = false
                    scope.launch {
                        sendAnswersToDB(dbConnection, answersMap, parentId)
                        insertPredictions(UpdateParent(id = parentId, estHighRiskPca = predPca, estHighRiskNeglect = predNeg), dbConnection)
                        makeRiskAssessment(dbConnection, parentId)
                        navController.navigate("my_cases")
                    }
                },
                dialogTitle = "Spara svarar",
                dialogText = "Du redigeras till dina ärenden.",
                icon = Icons.Default.Info
            )
        }
    }
}


suspend fun makeRiskAssessment(dbConnection: SQLiteDatabase, parentId: Long) {
    val questionsWithAnswers = getQuestionsWithAnswerByParent(dbConnection, parentId)
    if (questionsWithAnswers.size == 0) return
    var countRiskFactorsPca = 0.0
    var countPositiveFactorsPca = 0.0
    var countRiskFactorsNeglect = 0.0
    var countPositiveFactorsNeglect = 0.0
    for (qWithA in questionsWithAnswers) {
        if (qWithA.rPca != null) {
            if (qWithA.rPca > 0.0 && qWithA.optYes) countRiskFactorsPca += qWithA.weightYesPca!!
            if (qWithA.rPca > 0.0 && qWithA.optMiddle) countRiskFactorsPca += qWithA.weightMiddlePca!!
            if (qWithA.rPca > 0.0 && qWithA.optNo) countRiskFactorsPca += qWithA.weightNoPca!!
            if (qWithA.rPca <= 0.0 && qWithA.optYes) countPositiveFactorsPca += qWithA.weightYesPca!!
            if (qWithA.rPca <= 0.0 && qWithA.optMiddle) countPositiveFactorsPca += qWithA.weightMiddlePca!!
            if (qWithA.rPca <= 0.0 && qWithA.optNo) countPositiveFactorsPca += qWithA.weightNoPca!!
        }
        if (qWithA.rNeglect != null) {
            if (qWithA.rNeglect > 0.0 && qWithA.optYes) countRiskFactorsNeglect += qWithA.weightYesNeglect!!
            if (qWithA.rNeglect > 0.0 && qWithA.optMiddle) countRiskFactorsNeglect += qWithA.weightMiddleNeglect!!
            if (qWithA.rNeglect > 0.0 && qWithA.optNo) countRiskFactorsNeglect += qWithA.weightNoNeglect!!
            if (qWithA.rNeglect <= 0.0 && qWithA.optYes) countPositiveFactorsNeglect += qWithA.weightYesNeglect!!
            if (qWithA.rNeglect <= 0.0 && qWithA.optMiddle) countPositiveFactorsNeglect += qWithA.weightMiddleNeglect!!
            if (qWithA.rNeglect <= 0.0 && qWithA.optNo) countPositiveFactorsNeglect += qWithA.weightNoNeglect!!
        }
    }
    updateParent(dbConnection, UpdateParent(
        id = parentId,
        highRiskPca = (countRiskFactorsPca - countPositiveFactorsPca) >= 4.0,
        highRiskNeglect = (countRiskFactorsNeglect - countPositiveFactorsNeglect) >= 4.0
    ))
}