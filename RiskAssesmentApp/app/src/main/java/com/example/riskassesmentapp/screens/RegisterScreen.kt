package com.example.riskassesmentapp.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

class RegisterScreen(private val navController: NavController) {
    
    @Composable
    fun Content() {
        Text(text = "Register Screen")
    }
}