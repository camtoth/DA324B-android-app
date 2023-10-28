package com.example.riskassesmentapp.screens

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.riskassesmentapp.db.DatabaseOpenHelper
import com.example.riskassesmentapp.db.User
import com.example.riskassesmentapp.db.getPwByUsername
import com.example.riskassesmentapp.db.getUserByUsername
import com.example.riskassesmentapp.models.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.riskassesmentapp.R
import com.example.riskassesmentapp.db.InsertUser
import java.sql.SQLInput


@OptIn(ExperimentalMaterial3Api::class)
class LoginPage(
    private val navController: NavController,
    val onLoginSuccessful: (String) -> Unit,
    private val userViewModel: UserViewModel,
    private val databaseOpen: SQLiteDatabase
) {

    private val db: SQLiteDatabase = databaseOpen

    @Composable
    fun Content() {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var showError by remember { mutableStateOf(false) }
        val errorMessage = "Wrong username or password"

        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
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
                text = "Sign in",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
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
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
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
                            val user = authenticateUser(db, username, password)
                            if (user != null) {
                                userViewModel.loginUser(username, user.id) // Utilize the provided ViewModel instance
                                onLoginSuccessful(username)
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
                Text("Sign in")
            }


            Spacer(modifier = Modifier.height(24.dp))

            TextButton(
                onClick = {
                    // Handle sign up action
                }
            ) {
                SignUpText(navController = navController, "register")
            }
        }
    }

    // Mocked function to represent user authentication. Replace this with your actual authentication logic.

}


@Composable
fun SignUpText(navController: NavController, destination: String) {
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "No account yet? ",
            color = Color.Black
        )
        Text(
            text = "Sign up",
            color = MaterialTheme.colorScheme.primaryContainer,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {
                navController.navigate(destination)
            }
        )
    }
}


suspend fun authenticateUser(db: SQLiteDatabase, username: String, password: String): User? {
    return withContext(Dispatchers.IO) { // Use IO Dispatcher for DB operations
        try {
            Log.d("AuthenticateUser", "Fetching password for username: $username")
            val dbPasswordHash = getPwByUsername(db, username) // Assuming this fetches the hashed password
            val user = getUserByUsername(db, username)
            if (checkPassword(password, dbPasswordHash!!)) {
                Log.d("AuthenticateUser", "Password matches! User authenticated.")
                user
            } else {
                Log.w("AuthenticateUser", "Password mismatch. Authentication failed.")
                Log.w("AuthenticateUser","password : $password  original password: $dbPasswordHash")
                null
            }
        } catch (e: Exception) {
            Log.e("AuthenticateUser", "Error fetching user from database", e)
            null
        }
    }
}

// Hash a password
fun hashPassword(password: String): String {
    return BCrypt.withDefaults().hashToString(12, password.toCharArray())
}

// Check a password against a hash
fun checkPassword(candidate: String, hashedPassword: String): Boolean {
    return BCrypt.verifyer().verify(candidate.toCharArray(), hashedPassword).verified
}

fun insertNewUser(db: SQLiteDatabase, user: InsertUser): Long {
    val hashedPassword = hashPassword(user.pw)

    val userValues = ContentValues().apply {
        put("username", user.username)
        put("pw", hashedPassword)
        put("given_names", user.givenNames)
        put("last_name", user.lastName)
        put("is_admin", user.isAdmin)
    }

    val newUserId = db.insert("Users", null, userValues)
    return newUserId
}





