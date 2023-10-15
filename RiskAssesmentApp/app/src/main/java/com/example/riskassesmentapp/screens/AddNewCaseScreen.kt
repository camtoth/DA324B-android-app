package com.example.riskassesmentapp.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.riskassesmentapp.ui.composables.AddNewCase

class AddNewCaseScreen(private val navController: NavController) {
    @Composable
    fun Content() {
        AddNewCase(navController)
    }
}