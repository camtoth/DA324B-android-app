package com.example.riskassesmentapp.screens

import android.database.sqlite.SQLiteDatabase
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.riskassesmentapp.R
import com.example.riskassesmentapp.db.DatabaseOpenHelper
import com.example.riskassesmentapp.models.UserViewModel
import kotlinx.coroutines.launch

class RegisterScreen(
    private val navController: NavController,
    val onRegisterSuccessful: (String) -> Unit,
    private val userViewModel: UserViewModel,
    private val database: SQLiteDatabase) {

        private val db: SQLiteDatabase = database

        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun Content() {
            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var passwordRepeat by remember { mutableStateOf("") }
            var confirmPassword by remember { mutableStateOf("") } // for confirming the password
            var givenNames by remember { mutableStateOf("") } // new field for given names
            var lastName by remember { mutableStateOf("") } // new field for last name
            var showError by remember { mutableStateOf(false) }
            var errorMessage = "Fel användarnamn eller lösenord"

            val scope = rememberCoroutineScope()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
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

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Registrera",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Användarnamn") },
                    leadingIcon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                        focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                        cursorColor = MaterialTheme.colorScheme.primaryContainer),
                    visualTransformation = VisualTransformation.None
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = givenNames,
                    onValueChange = { givenNames = it },
                    label = { Text("Förnamn") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                        focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                        cursorColor = MaterialTheme.colorScheme.primaryContainer),
                    visualTransformation = VisualTransformation.None
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Efternamn") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                        focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                        cursorColor = MaterialTheme.colorScheme.primaryContainer),
                    visualTransformation = VisualTransformation.None
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Lösenord") },
                    leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                        focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                        cursorColor = MaterialTheme.colorScheme.primaryContainer),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = passwordRepeat,
                    onValueChange = { passwordRepeat = it },
                    label = { Text("Repetera lösenord") },
                    leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                        focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                        cursorColor = MaterialTheme.colorScheme.primaryContainer),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                if (showError) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        scope.launch {
                            try {
                                if (username.isEmpty() || password.isEmpty() || givenNames.isEmpty() || lastName.isEmpty()) {
                                    // Update the error message and set showError to true
                                    errorMessage = "Alla fält är obligatoriska"
                                    showError = true
                                    return@launch
                                }

                                if (password != passwordRepeat) {
                                    // Update the error message and set showError to true
                                    errorMessage = "Lösenorden matchar inte"
                                    showError = true
                                    return@launch
                                }

                                userViewModel.registerUser(db, username, password, givenNames, lastName) // register the user
                                val user = authenticateUser(db, username, password) // Authenticate the user
                                if (user != null) {
                                    userViewModel.loginUser(user)
                                    onRegisterSuccessful(username)
                                } else {
                                    showError = true
                                }
                            } catch (e: Exception) {
                                showError = true
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Text("Registrera")
                }


                Spacer(modifier = Modifier.height(24.dp))

                TextButton(
                    onClick = {
                        // Handle sign up action
                    }
                ) {
                    SignUpText(navController = navController, "login")
                }
            }
        }


    @Composable
    fun SignUpText(navController: NavController, destination: String) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Har du redan ett konto? ",
                color = Color.Black
            )
            Text(
                text = "Logga in",
                color = MaterialTheme.colorScheme.primaryContainer,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    navController.navigate(destination)
                }
            )
        }
    }
}