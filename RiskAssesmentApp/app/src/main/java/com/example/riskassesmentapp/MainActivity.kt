package com.example.riskassesmentapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.navigation.compose.*
import com.example.riskassesmentapp.screens.*
import com.example.riskassesmentapp.ui.composables.BottomNav
import com.example.riskassesmentapp.ui.theme.LightBlue
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
        //containerColor = MaterialTheme.colorScheme.primary,
        bottomBar = {
            BottomNav(navController)
        }
    ) { innerPadding ->
        val gradientBrush = Brush.verticalGradient(
            colors = listOf(MaterialTheme.colorScheme.background, LightBlue)
        )

        Box(
            modifier = Modifier
                .background(brush = gradientBrush)
                .padding(innerPadding)
        ) {
            NavHost(navController, startDestination = "home") {
                composable("home") { HomeScreen(navController).Content() }
                composable("add_case") { AddNewCaseScreen(navController).Content() }
                composable("my_cases") { CasesListScreen(navController).Content() }
                composable("assessment") { AssessmentScreen(navController).Content() }
                composable("settings") { SettingsScreen(navController).Content() }
            }
        }
    }
}
