package com.example.riskassesmentapp.ui.composables


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNav(navController: NavController) {
    BottomAppBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        IconButton(
            onClick = { if (currentRoute != "home") navController.navigate("home") },
            modifier = Modifier.weight(1f)
        ) {
            Icon(imageVector = Icons.Default.Home, contentDescription = null)
        }

        IconButton(
            onClick = { if (currentRoute != "my_cases") navController.navigate("my_cases") },
            modifier = Modifier.weight(1f)
        ) {
            Icon(imageVector = Icons.Default.Star, contentDescription = null)
        }

        IconButton(
            onClick = { if (currentRoute != "assessment") navController.navigate("assessment") },
            modifier = Modifier.weight(1f)
        ) {
            Icon(imageVector = Icons.Default.List, contentDescription = null)
        }

        IconButton(
            onClick = { if (currentRoute != "add_case") navController.navigate("add_case") },
            modifier = Modifier.weight(1f)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }
}