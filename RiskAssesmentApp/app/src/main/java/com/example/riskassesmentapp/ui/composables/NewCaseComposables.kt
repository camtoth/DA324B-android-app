package com.example.riskassesmentapp.ui.composables

import android.database.sqlite.SQLiteDatabase
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.riskassesmentapp.db.InsertCase
import com.example.riskassesmentapp.db.InsertParent
import com.example.riskassesmentapp.db.insertNewAnswer
import com.example.riskassesmentapp.db.insertNewCase
import com.example.riskassesmentapp.db.insertNewParent
import com.example.riskassesmentapp.models.UserViewModel
import com.example.riskassesmentapp.ui.theme.RiskAssesmentAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewCase(navController: NavController, dbConnection: SQLiteDatabase, user: UserViewModel) {
    RiskAssesmentAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) { }
        Column {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    Text(
                        text = "Add New Case",
                        modifier = Modifier
                            .padding(20.dp),
                        style = MaterialTheme.typography.headlineLarge,
                    )
                    var caseToInsert =  NewCaseCard(true)

                    Text(
                        text = "Add Parent 1",
                        modifier = Modifier
                            .padding(20.dp),
                        style = MaterialTheme.typography.headlineLarge,
                    )
                    var parent1 =  NewCaseCard(false)
                    var parent1ToInsert = InsertParent(personnr = parent1.personnr, givenNames = parent1.givenNames, lastName = parent1.lastName, gender = parent1.gender)
                    Text(
                        text = "Add Parent 2",
                        modifier = Modifier
                            .padding(20.dp),
                        style = MaterialTheme.typography.headlineLarge,
                    )
                    var parent2 =  NewCaseCard(false)
                    var parent2ToInsert = InsertParent(personnr = parent2.personnr, givenNames = parent2.givenNames, lastName = parent2.lastName, gender = parent2.gender)

                    SaveCaseButton(
                        dbConnection = dbConnection,
                        navController = navController,
                        currentUserId = user.currentUserId.value,
                        caseToInsert = caseToInsert,
                        parent1ToInsert = parent1ToInsert,
                        parent2ToInsert = parent2ToInsert
                    )
                }
                item {}
            }
        }
    }
}

@Composable
fun myCard(){
    Card(modifier = Modifier
        .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ){

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewCaseCard(showCaseNumberInput: Boolean) : InsertCase{
    var caseNumber: Int by remember { mutableStateOf(0) }
    var personnummer by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    Card(modifier = Modifier
        .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if(showCaseNumberInput) {
                TextField(
                    value = caseNumber.toString(),
                    onValueChange = {
                        caseNumber = it.toIntOrNull() ?: 0
                    },
                    label = { Text("Case Number") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
            TextField(
                value = personnummer,
                onValueChange = {
                    personnummer = it
                },
                label = { Text("Personnummer") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            TextField(
                value = firstName,
                onValueChange = {
                    firstName = it
                },
                label = { Text("First Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            TextField(
                value = lastName,
                onValueChange = {
                    lastName = it
                },
                label = { Text("Last Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            gender = GenderPicker()
        }
    }
    return InsertCase(caseNr = caseNumber.toString(), personnr = personnummer, givenNames = firstName, lastName = lastName, email = "test@test", gender = gender)
}

@Composable
fun GenderPicker() : String{
    val radioOptions = listOf("Female", "Male", "Other")
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
                androidx.compose.material3.RadioButton(
                    selected = (text == selectedOption),
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
    return selectedOption
}

@Composable
fun SaveCaseButton(dbConnection: SQLiteDatabase, navController: NavController, currentUserId: Long, caseToInsert: InsertCase, parent1ToInsert: InsertParent, parent2ToInsert: InsertParent) {
    var scope = rememberCoroutineScope()
    Button(
        onClick = {
            var caseId = addCaseToDb(dbConnection = dbConnection, caseToInsert = caseToInsert, currentUserId = currentUserId)
            addParentToDb(scope = scope, dbConnection = dbConnection, currentCaseId = caseId, parentToInsert = parent1ToInsert)
            addParentToDb(scope = scope, dbConnection = dbConnection, currentCaseId = caseId, parentToInsert = parent2ToInsert)
            navController.navigate("assessment") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer, contentColor = MaterialTheme.colorScheme.onPrimaryContainer)
    ) {
        Text("Save")
    }
}

fun addCaseToDb(dbConnection: SQLiteDatabase, caseToInsert: InsertCase, currentUserId: Long) : Long{
    var newCaseId : Long
    runBlocking {
        newCaseId = insertNewCase(db = dbConnection, case = caseToInsert, currentUserId = currentUserId)
    }
    return newCaseId
}

fun addParentToDb(scope: CoroutineScope, dbConnection: SQLiteDatabase, parentToInsert: InsertParent, currentCaseId: Long) {
    scope.launch {
        insertNewParent(db = dbConnection, parent = parentToInsert, curCaseId = currentCaseId)
    }
}