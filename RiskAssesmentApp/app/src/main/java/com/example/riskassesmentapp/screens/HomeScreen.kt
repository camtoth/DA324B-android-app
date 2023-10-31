package com.example.riskassesmentapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.riskassesmentapp.R
import com.example.riskassesmentapp.ui.composables.GetTodaysDate
import com.example.riskassesmentapp.ui.composables.HomeScreenButton



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
                text = "Välkommen $username",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Profile Photo
            Box(
                modifier = Modifier
                    .scale(1.5f)
                    .padding(30.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.riskapplogo),
                    contentDescription = null
                )
            }

            GetTodaysDate()               // Get Today's Date



            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(top = 50.dp),
                content = {

                    //  onClick = { if (currentRoute != "home") navController.navigate("home") },
                    items( 2) { index ->
                        when(index) {

                            0 -> {
                                HomeScreenButton(
                                    navController = navController,
                                    destination = "add_case",
                                    icon = Icons.Default.Add,
                                    text = "Nytt Ärende",
                                    buttonColor = MaterialTheme.colorScheme.primaryContainer,
                                    textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                    iconSize = 35.dp
                                )
                            }
                            1 -> {
                                HomeScreenButton (
                                    navController = navController,
                                    destination = "information",
                                    icon = Icons.Default.Info,
                                    iconSize = 35.dp,
                                    text = "Info",
                                    buttonColor = MaterialTheme.colorScheme.tertiaryContainer,
                                    textColor = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                            }

                        }
                    }
                }
            )
        }
    }
}
