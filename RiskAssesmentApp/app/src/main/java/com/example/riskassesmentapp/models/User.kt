package com.example.riskassesmentapp.models

import android.database.sqlite.SQLiteDatabase
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.riskassesmentapp.db.InsertUser
import com.example.riskassesmentapp.db.insertNewUser
import com.example.riskassesmentapp.screens.hashPassword

data class Username(val username: String)

class UserViewModel : ViewModel() {
    // MutableState to hold User data and observe changes across composables.
    val currentUser: MutableState<Username?> = mutableStateOf(null)

    fun loginUser(username: String) {
        currentUser.value = Username(username)
    }

    suspend fun registerUser(db: SQLiteDatabase, username: String, password: String, givenNames: String, lastName: String){
        insertNewUser(db, InsertUser(username, hashPassword(password), givenNames, lastName, false))
    }

    fun logoutUser() {
        currentUser.value = null
    }

    val isLoggedIn: Boolean
        get() = currentUser.value != null

    val currentUsername: String?
        get() = currentUser.value?.username ?: "Guest"
}