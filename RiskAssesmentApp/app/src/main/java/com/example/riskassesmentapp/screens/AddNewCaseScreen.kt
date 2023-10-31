package com.example.riskassesmentapp.screens

import android.database.sqlite.SQLiteDatabase
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.riskassesmentapp.db.InsertCase
import com.example.riskassesmentapp.db.InsertParent
import com.example.riskassesmentapp.models.UserViewModel
import com.example.riskassesmentapp.ui.composables.AddNewCase

class AddNewCaseScreen(
    private val navController: NavController,
    private val dbConnection: SQLiteDatabase,
    private val user: UserViewModel) {
    public lateinit var caseToInsert: InsertCase
    public lateinit var parent1: InsertCase
    public lateinit var parent1ToInsert: InsertParent
    public lateinit var parent2: InsertCase
    public lateinit var parent2ToInsert: InsertParent


    @Composable
    fun Content() {
        AddNewCase(navController, dbConnection, user, this)
    }
}