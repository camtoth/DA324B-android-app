package com.example.riskassesmentapp.screens

import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.riskassesmentapp.db.InsertCase
import com.example.riskassesmentapp.db.InsertParent
import com.example.riskassesmentapp.db.getCasesByUser
import com.example.riskassesmentapp.db.getUserByUsername
import com.example.riskassesmentapp.db.insertNewCase
import com.example.riskassesmentapp.db.insertNewParent
import com.example.riskassesmentapp.models.Case
import com.example.riskassesmentapp.models.Username
import com.example.riskassesmentapp.ui.composables.CasesList
//import com.example.riskassesmentapp.ui.composables.ShowDetailedCaseCard
//import com.example.riskassesmentapp.ui.composables.CasesList
import com.example.riskassesmentapp.ui.composables.Title
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class CasesListScreen(private val navController: NavController, private val database: SQLiteDatabase, private val username: String) {

    @Composable
    fun Content() {
        var userId = 1L // Replace this with the actual user ID
        val coroutineScope = rememberCoroutineScope()
        var cases by remember { mutableStateOf<List<com.example.riskassesmentapp.db.Case>>(emptyList()) }

        LaunchedEffect(Unit) {
            coroutineScope.launch {
                userId = getUserByUsername(database, username)!!.id

                userId?.let { id ->
                    val newCase = InsertCase(
                        personnr = "196509075678",
                        caseNr = "Case2023-001",
                        email = "john.doe@example.com",
                        gender = "Male",
                        givenNames = "John",
                        lastName = "Doe"
                    )
                    val caseId = insertNewCase(database, newCase, id)

                    val newParent1 = InsertParent(
                        personnr = "194503012345",
                        givenNames = "Mary",
                        lastName = "Johnson",
                        gender = "Female"
                    )
                    insertNewParent(database, newParent1, caseId)

                    val newParent2 = InsertParent(
                        personnr = "194807092345",
                        givenNames = "Robert",
                        lastName = "Johnson",
                        gender = "Male"
                    )
                    insertNewParent(database, newParent2, caseId)

                }

                cases = getCasesByUser(database, userId)

            }
        }

        var searchText by remember { mutableStateOf("") }
        val filteredCases by derivedStateOf {
            if (searchText.isBlank()) {
                cases
            } else {
                cases.filter { case ->
                    case.lastName.contains(searchText, ignoreCase = true)
                }
            }
        }
        Column(modifier = Modifier.padding(16.dp)) {
            TopAppBar(
                title = { Text(text = "Cases List") },
                actions = {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Shadow layer
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp) // Offset for the shadow
                                .background(
                                    Color.White,
                                    shape = RoundedCornerShape(50)
                                )
                                .graphicsLayer(
                                    shadowElevation = 16.dp.value, // Use .value here
                                    shape = RoundedCornerShape(50),
                                    clip = true
                                )
                        )

                        OutlinedTextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Color.White,
                                    shape = RoundedCornerShape(50)
                                ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Search, contentDescription = null)
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = { /* Handle filter icon click */ }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.List,
                                        contentDescription = null
                                    )
                                }
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                cursorColor = MaterialTheme.colorScheme.onBackground
                            )
                        )
                    }
                }
            )




            Text("My Cases", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(vertical = 16.dp))

            CasesList(filteredCases, onClick = { selectedCase ->
                navController.navigate("detailed_case/${selectedCase.id}")
            })
        }
    }
}
