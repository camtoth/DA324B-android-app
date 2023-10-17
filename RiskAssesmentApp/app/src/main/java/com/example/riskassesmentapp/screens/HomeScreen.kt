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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.riskassesmentapp.R
import com.example.riskassesmentapp.ui.composables.AppTitle
import com.example.riskassesmentapp.ui.composables.GetTodaysDate
import com.example.riskassesmentapp.ui.composables.HomeScreenButton


class HomeScreen(private val navController: NavController) {
    @Composable
    fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppTitle()            // App Title

            Spacer(modifier = Modifier.height(16.dp))

            Box(                        // Logo Photo
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
                                    text = "Add New Case",
                                    buttonColor = MaterialTheme.colorScheme.primaryContainer,
                                    textColor = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                            1 -> {
                                HomeScreenButton (
                                    navController = navController,
                                    destination = "add_case",
                                    icon = Icons.Default.Info,
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
@Preview
@Composable
fun previewHomeScreen(){
    val navController = rememberNavController()
    HomeScreen(navController).Content()
}

