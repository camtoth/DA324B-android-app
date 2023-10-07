package com.example.riskassesmentapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.riskassesmentapp.screens.*
import com.example.riskassesmentapp.ui.composables.BottomNav
import com.example.riskassesmentapp.ui.theme.RiskAssesmentAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RiskAssesmentAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNav(navController)
        }
    ) {
        NavHost(navController, startDestination = "home") {
            composable("home") { HomeScreen(navController).Content() }
            composable("add_case") { AddNewCaseScreen(navController).Content() }
            composable("my_cases") { CasesListScreen(navController).Content() }
            composable("assessment") { AssessmentScreen(navController).Content() }
        }
    }
}