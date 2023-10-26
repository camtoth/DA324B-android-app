package com.example.riskassesmentapp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.riskassesmentapp.db.DatabaseOpenHelper
import com.example.riskassesmentapp.db.DatabaseProvider
import com.example.riskassesmentapp.db.User
import com.example.riskassesmentapp.models.UserViewModel
import com.example.riskassesmentapp.screens.*
import com.example.riskassesmentapp.ui.composables.BottomNav
import com.example.riskassesmentapp.ui.theme.LightBlue
import com.example.riskassesmentapp.ui.theme.RiskAssesmentAppTheme

class MainActivity : ComponentActivity() {

    private val userViewModel: UserViewModel by viewModels()
    private var wasInBackground = true
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
    override fun onResume() {
        super.onResume()
        if (shouldAuthenticate()) {
            authenticateUser()
        }
        wasInBackground = false // Reset the flag since the app is now in the foreground
    }

    override fun onStop() {
        super.onStop()
        wasInBackground = true // Set the flag since the app might be going to the background
    }

    private fun shouldAuthenticate(): Boolean {
        // Check if the user is logged in and the app was in the background
        return userViewModel.isLoggedIn && wasInBackground
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTH_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Authentication succeeded
            } else {
                // Authentication failed
                authenticateUser() // Optionally, retry authentication
            }
        }
    }

    private fun authenticateUser() {
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        val intent = keyguardManager.createConfirmDeviceCredentialIntent(
            "Authentication Required",
            "Please enter your device PIN/Password, or use Fingerprint/Face unlock to access the app."
        )
        if(intent != null) {
            startActivityForResult(intent, AUTH_REQUEST_CODE)
        } else {
            // Handle the case where there is no secure lock screen set
            Toast.makeText(this, "Secure lock screen is not set.", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val AUTH_REQUEST_CODE = 1001
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val navController = rememberNavController()
    val dbConnection = DatabaseOpenHelper(LocalContext.current).writableDatabase
    val userViewModel: UserViewModel = viewModel()
    val username = userViewModel.currentUsername

    val isLoggedIn = userViewModel.isLoggedIn

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
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(navController, startDestination = "login") {
                composable("login") {
                    if (isLoggedIn) {
                        // Redirect to home if already logged in
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        LoginPage(navController, onLoginSuccessful = {
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        }, userViewModel, dbConnection).Content()
                    }
                }
                composable("home") {
                    LaunchedEffect(key1 = isLoggedIn) {
                        if (!isLoggedIn) {
                            navController.navigate("login") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    }
                    if (isLoggedIn && username != null) {
                        HomeScreen(navController, username).Content()
                    }
                }
                composable("add_case") {
                    LaunchedEffect(key1 = isLoggedIn) {
                        if (!isLoggedIn) {
                            navController.navigate("login")
                        }
                    }
                    if (isLoggedIn) {
                        AddNewCaseScreen(navController).Content()
                    }
                }
                composable("my_cases") {
                    LaunchedEffect(key1 = isLoggedIn) {
                        if (!isLoggedIn) {
                            navController.navigate("login")
                        }
                    }
                    if (isLoggedIn) {
                        if (username != null) {
                            CasesListScreen(navController, dbConnection, username).Content()
                        }
                    }
                }
                composable("assessment") {
                    LaunchedEffect(key1 = isLoggedIn) {
                        if (!isLoggedIn) {
                            navController.navigate("login")
                        }
                    }
                    if (isLoggedIn) {
                        AssessmentScreen(navController).Content()
                    }
                }
                composable("settings") {
                    LaunchedEffect(key1 = isLoggedIn) {
                        if (!isLoggedIn) {
                            navController.navigate("login")
                        }
                    }
                    if (isLoggedIn) {
                        SettingsScreen(navController, userViewModel).Content()
                    }
                }
                composable("information") {
                    LaunchedEffect(key1 = isLoggedIn) {
                        if (!isLoggedIn) {
                            navController.navigate("login")
                        }
                    }
                    if (isLoggedIn) {
                        InformationScreen(navController).Content()
                    }
                }
//                composable("detailed_case") {
//                    LaunchedEffect(key1 = isLoggedIn) {
//                        if (!isLoggedIn) {
//                            navController.navigate("login")
//                        }
//                    }
//                    if (isLoggedIn) {
//                        DetailedCaseScreen(navController).Content()
//                    }
//                }
                composable("register") {
                    // Register page accessible regardless of login status
                    RegisterScreen(navController, onRegisterSuccessful = {
                        navController.navigate("home") {
                            popUpTo("register") { inclusive = true }
                        }
                    }, userViewModel, dbConnection).Content()
                }

            }
        }
    }

}
