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
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.riskassesmentapp.db.DatabaseProvider
import com.example.riskassesmentapp.db.User
import com.example.riskassesmentapp.models.UserViewModel
import com.example.riskassesmentapp.screens.*
import com.example.riskassesmentapp.ui.composables.BottomNav
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
    val context = LocalContext.current
    val dbHelper = DatabaseProvider.getInstance(context)
    val userViewModel: UserViewModel = viewModel()
    val username = userViewModel.currentUsername

    Scaffold(
        bottomBar = {
            BottomNav(navController)
        }
    ) {
        NavHost(navController, startDestination = "login") {
            composable("login") {
                LoginPage(navController, onLoginSuccessful = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }, userViewModel, dbHelper).Content()
            }
            composable("home") {
                if (username != null) {
                    HomeScreen(navController, username).Content()
                }
            }
            composable("add_case") { AddNewCaseScreen(navController).Content() }
            composable("my_cases") { CasesListScreen(navController).Content() }
            composable("assessment") { AssessmentScreen(navController).Content() }
        }
    }
}