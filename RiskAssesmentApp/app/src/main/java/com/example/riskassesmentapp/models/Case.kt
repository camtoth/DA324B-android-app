package com.example.riskassesmentapp.models

import com.example.riskassesmentapp.db.Parent
import java.util.LinkedList

data class Case(
    val id: Long,
    val personnr: String,
    val caseNr: String,
    val email: String,
    val gender: String,
    val givenNames: String,
    val lastName: String,
    val highRisk: Boolean? = null,
)