package com.example.riskassesmentapp.models

data class Case(
    val number: Int,
    val interviewDate: String,
    val firstName: String,
    val lastName: String,
    val personnummer: String,
    val overallRisk: String,
    val parentChildInteraction: Int,
    val parentIndependent: Int,
    val characteristicsChild: Int,
    val characteristicsFamily: Int,
    val isDetailedView: Boolean = false
)