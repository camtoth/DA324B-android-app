package com.example.riskassesmentapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults.shape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.riskassesmentapp.ui.theme.Typography

class SettingsScreen(private val navController: NavController) {

    @Composable
    fun Content() {
        val showDialog = remember { mutableStateOf(false) }
        val showDeleteDialog = remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Settings", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(128.dp))

            SettingRow(icon = Icons.Default.AccountBox, text = "Logged in as: username")
            Spacer(modifier = Modifier.height(8.dp))
            SettingRow(icon = Icons.Default.Lock, text = "Modify Password", onClick = { showDialog.value = true })
            Spacer(modifier = Modifier.height(64.dp))
            Button(
                onClick = { showDeleteDialog.value = true },
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = Modifier.padding(12.dp)
            ) {
                Text("Delete User", color = Color.White, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(8.dp))
            }

            if (showDialog.value) {
                PasswordDialog(onDismiss = { showDialog.value = false })
            }

            if (showDeleteDialog.value) {
                DeleteUserDialog(onDismiss = { showDeleteDialog.value = false })
            }
        }
    }

    @Composable
    fun SettingRow(icon: ImageVector, text: String, onClick: (() -> Unit)? = null) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .clickable(onClick != null) { onClick?.invoke() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text, style = MaterialTheme.typography.bodyLarge)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PasswordDialog(onDismiss: () -> Unit) {
        val newPassword = remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Enter New Password", style = MaterialTheme.typography.bodyLarge) },
            text = {
                OutlinedTextField(
                    value = newPassword.value,
                    onValueChange = { newPassword.value = it },
                    label = { Text("New Password") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                        focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                        cursorColor = MaterialTheme.colorScheme.primaryContainer)
                )
            },
            confirmButton = {
                Button(onClick = {
                    // handle password change logic here
                    onDismiss()
                }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer, contentColor = Color.White)) {
                    Text("Confirm", style = MaterialTheme.typography.bodyLarge)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel", color = Color.Black, style = MaterialTheme.typography.bodyLarge)
                }
            }
        )
    }

    @Composable
    fun DeleteUserDialog(onDismiss: () -> Unit) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Are you sure you want to delete this user?", style = MaterialTheme.typography.bodyLarge) },
            confirmButton = {
                Button(
                    onClick = {
                        // handle user deletion logic here
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text("Delete", color = Color.White, style = MaterialTheme.typography.bodyLarge)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel", color = Color.Black, style = MaterialTheme.typography.bodyLarge)
                }
            },
        )
    }
}
