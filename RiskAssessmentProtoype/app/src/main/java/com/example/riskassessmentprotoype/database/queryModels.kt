package com.example.riskassessmentprotoype.database

import java.util.Date

data class User(
    val id: Long,
    val username: String,
    val pw: String,
    val givenNames: String,
    val lastName: String,
    val isAdmin: Boolean
)

data class Question(
    val id: Long,
    val textEn: String,
    val textSe: String,
    val rNeglect: Float?,
    val rPca: Float?,
    val weightYesNeglect: Float?,
    val weightMiddleNeglect: Float? = 0.5f,
    val weightNoNeglect: Float?,
    val weightYesPca: Float?,
    val weightMiddlePca: Float? = 0.5f,
    val weightNoPca: Float?
)

data class Case(
    val id: Long,
    val personnr: String,
    val email: String,
    val gender: String,
    val givenNames: String,
    val lastName: String,
    val neglectRisk: Boolean?,
    val neglectScore: Float?,
    val neglectEstimation: Float?,
    val pcaRisk: Boolean?,
    val pcaScore: Float?,
    val pcaEstimation: Float?,
    val userId: Int,
    val parents: List<InsertParent>
)

data class Parent(
    val id: Long,
    val givenNames: String,
    val lastName: String,
    val gender: String
)

data class Answer(
    val id: Long,
    val optYes: Boolean,
    val optMiddle: Boolean,
    val optNo: Boolean,
    val lastChanged: Date,
    val caseId: Int,
    val questionId: Int
)