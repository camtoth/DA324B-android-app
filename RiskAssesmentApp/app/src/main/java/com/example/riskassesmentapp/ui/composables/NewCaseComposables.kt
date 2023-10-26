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
import com.example.riskassesmentapp.db.insertNewAnswer
import com.example.riskassesmentapp.db.insertNewCase
import com.example.riskassesmentapp.ui.theme.RiskAssesmentAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewCase(navController: NavController, dbConnection: SQLiteDatabase) {
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
                    NewCaseCard(navController, dbConnection)
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
fun NewCaseCard(navController: NavController, dbConnection: SQLiteDatabase){
    var caseNumber: Int by remember { mutableStateOf(0) }
    var personnummer by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var gender: String
    var scope = rememberCoroutineScope()
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
            Button(
                onClick = {
                    addCaseToDb(scope = scope, dbConnection = dbConnection, caseNumber = caseNumber.toString(), personnummer = personnummer, firstName = firstName, lastName = lastName, email = "test@test.nu", gender = gender)
                    navController.navigate("assessment") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer, contentColor = MaterialTheme.colorScheme.onPrimaryContainer)
            ) {
                Text("Take Risk Assessment")
            }
        }
    }
}

fun addCaseToDb(scope: CoroutineScope, dbConnection: SQLiteDatabase, caseNumber: String, personnummer: String, firstName: String, lastName: String, gender: String, email: String) {
    val insertCase = InsertCase(caseNr = caseNumber, personnr = personnummer, givenNames = firstName, lastName = lastName, email = email, gender = gender)
    scope.launch {
        insertNewCase(db = dbConnection, case = insertCase, currentUserId = 0)//TODO: pass real currentUserId
    }
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