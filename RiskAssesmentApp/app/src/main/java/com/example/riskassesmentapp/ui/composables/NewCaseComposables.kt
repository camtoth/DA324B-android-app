package com.example.riskassesmentapp.ui.composables

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.riskassesmentapp.ui.theme.RiskAssesmentAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewCase(navController: NavController) {
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
                items(1) { i ->
                    NewCaseCard(navController)
                }
                item {}
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewCaseCard(navController: NavController){
    var caseNumber by remember { mutableStateOf(0) }
    var personnummer by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
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
            Text(
                text = "Add New Case",
                modifier = Modifier
                    .padding(20.dp),
                style = MaterialTheme.typography.headlineLarge,
            )
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
            GenderPicker()
            Button(
                onClick = { navController.navigate("assessment") },
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

@Composable
fun GenderPicker(){
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
}