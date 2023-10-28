package com.example.riskassesmentapp.screens

import android.database.sqlite.SQLiteDatabase
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.riskassesmentapp.models.UserViewModel
import com.example.riskassesmentapp.ui.composables.AddNewCase

class AddNewCaseScreen(private val navController: NavController, private val dbConnection: SQLiteDatabase, private val user: UserViewModel) {
    @Composable
    fun Content() {
        AddNewCase(navController, dbConnection, user)
    }
}