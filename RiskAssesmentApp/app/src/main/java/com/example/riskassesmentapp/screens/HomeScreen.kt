package com.example.riskassesmentapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.riskassesmentapp.R
import com.example.riskassesmentapp.ui.composables.ButtonWithIconText



class HomeScreen(private val navController: NavController, private val username: String) {

    @Composable
    fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Welcome Message
            Text(
                text = "Welcome $username",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Profile Photo
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(shape = CircleShape)
                    .background(Color.Gray)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            val buttons = listOf(
                Icons.Default.Add to "Add New Case",
                Icons.Default.Star to "My Cases",
                Icons.Default.Settings to "Settings",
                Icons.Default.Info to "Info"
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth(),
                content = {
                    items(buttons) { (icon, text) ->
                        ButtonWithIconText(icon, text) {
                            when (text) {
                                "Add New Case" -> {
                                    println("Add New Case button clicked")
                                    navController.navigate("add_case")
                                }

                                "My Cases" -> {
                                    println("My Cases button clicked")
                                    navController.navigate("my_cases")
                                }

                                "Settings" -> {
                                    println("Settings button clicked")
                                    // Navigate to Settings if available
                                }

                                "Info" -> {
                                    println("Info button clicked")
                                    // Navigate to Info if available
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}
