package com.example.riskassesmentapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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

        Column {
            Title("Active Cases", modifier = Modifier.fillMaxWidth())
            TopAppBar(
                title = { Text(text = "Cases List") },
                actions = {
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        singleLine = true,
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null)
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = { /* Handle filter icon click */ }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }
           )
           CasesList()
        }
    }
}
