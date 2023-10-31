package com.example.riskassesmentapp.ui.composables

import android.database.sqlite.SQLiteDatabase
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.riskassesmentapp.db.InsertCase
import com.example.riskassesmentapp.db.InsertParent
import com.example.riskassesmentapp.db.insertNewCase
import com.example.riskassesmentapp.db.insertNewParent
import com.example.riskassesmentapp.models.UserViewModel
import com.example.riskassesmentapp.screens.AddNewCaseScreen
import com.example.riskassesmentapp.ui.theme.RiskAssesmentAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewCase(navController: NavController, dbConnection: SQLiteDatabase, user: UserViewModel, addNewCaseScreen: AddNewCaseScreen) {
    RiskAssesmentAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) { }
        Column {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    Text(
                        text = "Add New Case",
                        modifier = Modifier
                            .padding(20.dp),
                        style = MaterialTheme.typography.headlineLarge,
                    )
                    addNewCaseScreen.caseToInsert =  NewCaseCard(showCaseNumberInput = true, showAddAssessmentButton = false, dbConnection = dbConnection, navController = navController, currentUserId = user.currentUser.value!!.id, addNewCaseScreen = addNewCaseScreen)

                    Text(
                        text = "Add Parent 1",
                        modifier = Modifier
                            .padding(20.dp),
                        style = MaterialTheme.typography.headlineLarge,
                    )
                    addNewCaseScreen.parent1 =  NewCaseCard(showCaseNumberInput = false, showAddAssessmentButton = true, dbConnection = dbConnection, navController = navController, currentUserId = user.currentUser.value!!.id , addNewCaseScreen = addNewCaseScreen)
                    addNewCaseScreen.parent1ToInsert = InsertParent(personnr = addNewCaseScreen.parent1.personnr, givenNames = addNewCaseScreen.parent1.givenNames, lastName = addNewCaseScreen.parent1.lastName, gender = addNewCaseScreen.parent1.gender)

                    Text(
                        text = "Add Parent 2",
                        modifier = Modifier
                            .padding(20.dp),
                        style = MaterialTheme.typography.headlineLarge,
                    )
                    addNewCaseScreen.parent2 =  NewCaseCard(showCaseNumberInput = false, showAddAssessmentButton = true, dbConnection = dbConnection, navController = navController, currentUserId = user.currentUser.value!!.id , addNewCaseScreen = addNewCaseScreen)
                    addNewCaseScreen.parent2ToInsert = InsertParent(personnr = addNewCaseScreen.parent2.personnr, givenNames = addNewCaseScreen.parent2.givenNames, lastName = addNewCaseScreen.parent2.lastName, gender = addNewCaseScreen.parent2.gender)

                    SaveCaseButton(
                        dbConnection = dbConnection,
                        navController = navController,
                        currentUserId = user.currentUser.value!!.id,
                        caseToInsert = addNewCaseScreen.caseToInsert,
                        parent1ToInsert = addNewCaseScreen.parent1ToInsert,
                        parent2ToInsert = addNewCaseScreen.parent2ToInsert
                    )
                }
                item {}
            }
        }
    }
}

@Composable
fun myCard(){
    Card(modifier = Modifier
        .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ){

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewCaseCard(dbConnection: SQLiteDatabase, navController: NavController, currentUserId: Long, addNewCaseScreen: AddNewCaseScreen, showCaseNumberInput: Boolean, showAddAssessmentButton: Boolean) : InsertCase{
    var caseNumber by remember { mutableStateOf("") }
    var personnummer by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    Card(modifier = Modifier
        .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if(showCaseNumberInput) {
                TextField(
                    value = caseNumber,
                    onValueChange = {
                        caseNumber = it
                    },
                    label = { Text("Case Number") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
            TextField(
                value = personnummer,
                onValueChange = {
                    personnummer = it
                },
                label = { Text("Personnummer") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            TextField(
                value = firstName,
                onValueChange = {
                    firstName = it
                },
                label = { Text("First Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            TextField(
                value = lastName,
                onValueChange = {
                    lastName = it
                },
                label = { Text("Last Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            gender = GenderPicker()

            if(showAddAssessmentButton) {
                /*AddAssessmentButton(
                    dbConnection = dbConnection,
                    navController = navController,
                    currentUserId = currentUserId,
                    addNewCaseScreen = addNewCaseScreen
                )*/
            }
        }
    }
    return InsertCase(caseNr = caseNumber.toString(), personnr = personnummer, givenNames = firstName, lastName = lastName, email = "test@test", gender = gender)
}

@Composable
fun GenderPicker() : String{
    val radioOptions = listOf("Female", "Male", "Other")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0] ) }
    Column {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                        }
                    )
                    .padding(horizontal = 16.dp)
            ) {
                androidx.compose.material3.RadioButton(
                    selected = (text == selectedOption),
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedColor = MaterialTheme.colorScheme.onTertiary,
                    ),
                    onClick = {
                        onOptionSelected(text)
                    }
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
    return selectedOption
}

@Composable
fun SaveCaseButton(dbConnection: SQLiteDatabase, navController: NavController, currentUserId: Long, caseToInsert: InsertCase, parent1ToInsert: InsertParent, parent2ToInsert: InsertParent) {
    var scope = rememberCoroutineScope()
    var showInvalidInputCard = remember{ mutableStateOf(false)}
    if(showInvalidInputCard.value) {
        InvalidInputCard()
    }
    Button(
        onClick = {
            if(isNewCaseInputValid(newCase = caseToInsert, parent1 = parent1ToInsert, parent2 = parent2ToInsert)) {
                showInvalidInputCard.value = false
                var caseId = addCaseToDb(dbConnection = dbConnection, caseToInsert = caseToInsert, currentUserId = currentUserId)
                addParentToDb(scope = scope, dbConnection = dbConnection, currentCaseId = caseId, parentToInsert = parent1ToInsert)
                addParentToDb(scope = scope, dbConnection = dbConnection, currentCaseId = caseId, parentToInsert = parent2ToInsert)
                navController.navigate("my_cases")
            } else {
                showInvalidInputCard.value = true
            }},
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer, contentColor = MaterialTheme.colorScheme.onPrimaryContainer)
    ) {
        Text("Save")
    }
}

@Composable
fun InvalidInputCard() {
    Card(modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 2.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        )
    ) {
        Text(text = "Fill in all input fields.",
            modifier = Modifier
                .padding(10.dp),
            style = MaterialTheme.typography.labelLarge)
    }
}

//Checks that case and both parents have been fully filled and returns a bool
fun isNewCaseInputValid(newCase: InsertCase, parent1: InsertParent, parent2: InsertParent) : Boolean{
    val isNewCaseValid = !(newCase.caseNr.isNullOrBlank() || newCase.gender.isNullOrBlank() || newCase.email.isNullOrBlank() || newCase.lastName.isNullOrBlank() || newCase.givenNames.isNullOrBlank() || newCase.personnr.isNullOrBlank())
    val isParent1Valid = !(parent1.gender.isNullOrBlank() || parent1.givenNames.isNullOrBlank() || parent1.lastName.isNullOrBlank() || parent1.personnr.isNullOrBlank())
    val isParent2Valid = !(parent2.gender.isNullOrBlank() || parent2.givenNames.isNullOrBlank() || parent2.lastName.isNullOrBlank() || parent2.personnr.isNullOrBlank())
    return (isNewCaseValid && isParent1Valid && isParent2Valid)
}

@Composable
fun AddAssessmentButton(dbConnection: SQLiteDatabase, navController: NavController, currentUserId: Long, addNewCaseScreen: AddNewCaseScreen) {
    var scope = rememberCoroutineScope()
    var parentId : Long
    parentId = 0
    Button(
        onClick = {
            if(addNewCaseScreen.caseToInsert != null) {
                var caseId = addCaseToDb(dbConnection = dbConnection, caseToInsert = addNewCaseScreen.caseToInsert, currentUserId = currentUserId)
                parentId = addParentToDb(scope = scope, dbConnection = dbConnection, currentCaseId = caseId, parentToInsert = addNewCaseScreen.parent1ToInsert)
                if(addNewCaseScreen.parent2ToInsert != null) {
                    addParentToDb(scope = scope, dbConnection = dbConnection, currentCaseId = caseId, parentToInsert = addNewCaseScreen.parent2ToInsert)
                }
            }

            navController.navigate("assessment/$parentId") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer, contentColor = MaterialTheme.colorScheme.onPrimaryContainer)
    ) {
        Text("Add assessment")
    }
}

fun addCaseToDb(dbConnection: SQLiteDatabase, caseToInsert: InsertCase, currentUserId: Long) : Long{
    var newCaseId : Long
    runBlocking {
        newCaseId = insertNewCase(db = dbConnection, case = caseToInsert, currentUserId = currentUserId)
    }
    return newCaseId
}

fun addParentToDb(scope: CoroutineScope, dbConnection: SQLiteDatabase, parentToInsert: InsertParent, currentCaseId: Long) : Long{
    var newParentId : Long
    runBlocking {
        newParentId = insertNewParent(db = dbConnection, parent = parentToInsert, curCaseId = currentCaseId)
    }
    return newParentId
}