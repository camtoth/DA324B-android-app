package com.example.riskassesmentapp.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.riskassesmentapp.models.Case

@Preview
@Composable
fun ShowCaseCard(){
    val testCase =
        Case(
            number = 1,
            interviewDate = "March 2, 2023",
            firstName = "John",
            lastName = "Doe",
            personnummer = "19000302017892",
            overallRisk = "Low Risk",
            parentChildInteraction = 60,
            parentIndependent = 40,
            characteristicsChild = 70,
            characteristicsFamily = 50,
            isDetailedView = false
        )
    CaseCard(testCase)
}

@Composable
fun CasesList() {
    val dummyCases = listOf(
        Case(
            number = 1,
            interviewDate = "March 2, 2023",
            firstName = "John",
            lastName = "Doe",
            personnummer = "19000302017892",
            overallRisk = "Low Risk",
            parentChildInteraction = 60,
            parentIndependent = 40,
            characteristicsChild = 70,
            characteristicsFamily = 50,
            isDetailedView = false
        ),
        Case(
            number = 2,
            interviewDate = "April 15, 2023",
            firstName = "Jane",
            lastName = "Smith",
            personnummer = "19000302017892",
            overallRisk = "Medium Risk",
            parentChildInteraction = 70,
            parentIndependent = 50,
            characteristicsChild = 60,
            characteristicsFamily = 40,
            isDetailedView = false
        ),
        Case(
            number = 3,
            interviewDate = "May 20, 2023",
            firstName = "Alice",
            lastName = "Johnson",
            personnummer = "19000302017892",
            overallRisk = "High Risk",
            parentChildInteraction = 80,
            parentIndependent = 60,
            characteristicsChild = 75,
            characteristicsFamily = 70,
            isDetailedView = false
        ),
        Case(
            number = 4,
            interviewDate = "June 10, 2023",
            firstName = "Emily",
            lastName = "Williams",
            personnummer = "19000302017892",
            overallRisk = "Medium Risk",
            parentChildInteraction = 65,
            parentIndependent = 45,
            characteristicsChild = 55,
            characteristicsFamily = 60,
            isDetailedView = false
        ),
        Case(
            number = 5,
            interviewDate = "July 5, 2023",
            firstName = "Michael",
            lastName = "Anderson",
            personnummer = "19000302017892",
            overallRisk = "High Risk",
            parentChildInteraction = 75,
            parentIndependent = 65,
            characteristicsChild = 70,
            characteristicsFamily = 75,
            isDetailedView = false
        ),
        Case(
            number = 6,
            interviewDate = "August 12, 2023",
            firstName = "Olivia",
            lastName = "Martinez",
            personnummer = "19000302017892",
            overallRisk = "Low Risk",
            parentChildInteraction = 55,
            parentIndependent = 35,
            characteristicsChild = 45,
            characteristicsFamily = 50,
            isDetailedView = false
        ),
        Case(
            number = 7,
            interviewDate = "September 18, 2023",
            firstName = "James",
            lastName = "Brown",
            personnummer = "19000302017892",
            overallRisk = "Medium Risk",
            parentChildInteraction = 70,
            parentIndependent = 50,
            characteristicsChild = 60,
            characteristicsFamily = 65,
            isDetailedView = false
        ),
        Case(
            number = 8,
            interviewDate = "October 24, 2023",
            firstName = "Sophia",
            lastName = "Garcia",
            personnummer = "19000302017892",
            overallRisk = "High Risk",
            parentChildInteraction = 80,
            parentIndependent = 70,
            characteristicsChild = 75,
            characteristicsFamily = 80,
            isDetailedView = false
        )
    )

    LazyColumn {
        items(dummyCases) { case ->
            CaseCard(case)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewCase(navController: NavController) {
    var caseNumber by remember { mutableStateOf(0) }
    var personnummer by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Title(title = "Add New Case", modifier = Modifier.padding(20.dp))
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

        Button(
            onClick = { navController.navigate("assessment") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Take Risk Assessment")
        }
    }
}

@Composable
fun CaseCard(case: Case) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Case Number: ${case.number}", fontSize = 18.sp)
                IconButton(
                    onClick = { isExpanded = !isExpanded },
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            }
            Text("Interview Date: ${case.interviewDate}")
            Text("Name: ${case.firstName} ${case.lastName}")
            Text("Personnummer: ${case.personnummer}")
            Text("Overall Risk: ${case.overallRisk}")

            if (isExpanded) {
                Column {
                    Text("Risk by Section:")
                    Text("Parent-child interaction: ${case.parentChildInteraction}%")
                    Text("The parent independent of the child: ${case.parentIndependent}%")
                    Text("Characteristics of child excluding parent: ${case.characteristicsChild}%")
                    Text("Characteristics of the family: ${case.characteristicsFamily}%")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { /* Handle "See Interview" button click */ },
                        ) {
                            Text("See Assessment")
                        }
                        Button(
                            onClick = { /* Handle "Resubmit Interview" button click */ },
                        ) {
                            Text("Resubmit Assessment")
                        }
                    }
                }
            }
        }
    }
}