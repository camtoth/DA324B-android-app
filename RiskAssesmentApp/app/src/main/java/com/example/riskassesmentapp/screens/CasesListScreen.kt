package com.example.riskassesmentapp.screens

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
import com.example.riskassesmentapp.ui.composables.CasesList
import com.example.riskassesmentapp.ui.composables.ShowDetailedCaseCard
//import com.example.riskassesmentapp.ui.composables.CasesList
import com.example.riskassesmentapp.ui.composables.Title

@OptIn(ExperimentalMaterial3Api::class)
class CasesListScreen(private val navController: NavController) {
    @Composable
    fun Content() {
        var searchText by remember { mutableStateOf("") }

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

            CasesList()

        }
    }
}
