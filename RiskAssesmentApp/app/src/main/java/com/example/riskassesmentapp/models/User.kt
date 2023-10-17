package com.example.riskassesmentapp.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class Username(val username: String)

class UserViewModel : ViewModel() {
    // MutableState to hold User data and observe changes across composables.
    val currentUser: MutableState<Username?> = mutableStateOf(null)

    fun loginUser(username: String) {
        currentUser.value = Username(username)
    }

    fun logoutUser() {
        currentUser.value = null
    }

    val isLoggedIn: Boolean
        get() = currentUser.value != null

    val currentUsername: String?
        get() = currentUser.value?.username ?: "Guest"
}