package com.example.riskassessmentprotoype

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.*
import com.example.riskassessmentprotoype.ui.theme.RiskAssessmentProtoypeTheme
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RiskAssessmentProtoypeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    var currentScreen by remember { mutableStateOf(Screen.Home) }

    Scaffold(
        bottomBar = { BottomNav(currentScreen) { newScreen -> currentScreen = newScreen } }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Title(
                "Risk Assessment Prototype",
                modifier = Modifier.fillMaxWidth()
            )

            when (currentScreen) {
                Screen.Home -> HomeScreen(currentScreen) { newScreen -> currentScreen = newScreen }
                Screen.MyCases -> CasesListScreen()
                Screen.Assessment -> AssessmentScreen()
                Screen.AddCase -> AddNewCaseScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNav(currentScreen: Screen, onScreenChange: (Screen) -> Unit) {
    BottomAppBar {
        IconButton(
            onClick = { onScreenChange(Screen.Home) },
            modifier = Modifier.weight(1f)
        ) {
            Icon(imageVector = Icons.Default.Home, contentDescription = null)
        }

        IconButton(
            onClick = { onScreenChange(Screen.MyCases) },
            modifier = Modifier.weight(1f)
        ) {
            Icon(imageVector = Icons.Default.Star, contentDescription = null)
        }

        IconButton(
            onClick = { onScreenChange(Screen.Assessment) },
            modifier = Modifier.weight(1f)
        ) {
            Icon(imageVector = Icons.Default.List, contentDescription = null)
        }

        IconButton(
            onClick = { onScreenChange(Screen.AddCase) },
            modifier = Modifier.weight(1f)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }

    }
}

enum class Screen(val caseNumber: Int = -1) {
    Home,
    Assessment,
    AddCase,
    MyCases
}

@Composable
fun Title(title: String, modifier: Modifier) {
    Text(
        text = title,
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        fontFamily = FontFamily.Serif
    )
}

@Composable
fun HomeScreen(currentScreen: Screen, onScreenChange: (Screen) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Photo
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(shape = CircleShape)
                .background(Color.Gray)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        val buttons = listOf(
            Icons.Default.Add to "Add New Case",
            Icons.Default.Star to "My Cases",
            Icons.Default.Settings to "Settings",
            Icons.Default.Info to "Info"
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            content = {
                items(buttons) { (icon, text) ->
                    ButtonWithIconText(icon, text) {
                        when (text) {
                            "Add New Case" -> {
                                println("Add New Case button clicked")
                                onScreenChange(Screen.Home)
                            }

                            "My Cases" -> {
                                println("My Cases button clicked")
                                onScreenChange(Screen.MyCases)
                            }

                            "Settings" -> {
                                println("Settings button clicked")
                                onScreenChange(Screen.Home)
                            }

                            "Info" -> {
                                println("Info button clicked")
                                onScreenChange(Screen.Home)
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun ButtonWithIconText(icon: ImageVector, text: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconToggleButton(
            checked = false,
            onCheckedChange = { /* Handle button click here */ },
            modifier = Modifier.size(56.dp),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Black,
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = text,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onClick() }
        )
    }
}

@Composable
fun AddNewCaseScreen() {
    AddNewCase()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CasesListScreen() {
    var searchText by remember { mutableStateOf("") }

    Column {
        Title("Active Cases", modifier = Modifier.fillMaxWidth())
        TopAppBar(
            title = {
                Text(text = "Cases List")
            },
            actions = {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    singleLine = true,
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { /* Handle filter icon click */ }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        )
        CasesList()
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssessmentScreen() {
    Title(title = "Risk Assessment", modifier = Modifier.padding(20.dp))

    LazyColumn {
        items((1..3).toList()) { questionNumber ->
            QuestionAndAnswers(question = "Question $questionNumber")
        }

        item {
            Button(
                onClick = { /* Handle Submit Assessment */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Submit Assessment")
            }
        }
    }
}


@Composable
fun QuestionAndAnswers(question: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = question,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val answerOptions = listOf("Answer 1", "Answer 2", "Answer 3")

        for (answer in answerOptions) {
            AnswerChoice(answer)
        }
    }
}

@Composable
fun AnswerChoice(answer: String) {
    var isChecked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { isChecked = it },
            modifier = Modifier.padding(end = 8.dp)
        )

        Text(text = answer)
    }
}

fun onResubmitClick() {
    TODO("Not yet implemented")
}

@Composable
fun CasesList() {
    val dummyCases = listOf(
        Case(
            number = 1,
            interviewDate = "March 2, 2023",
            firstName = "John",
            lastName = "Doe",
            personnummer = "19000302017892",
            overallRisk = "Low Risk",
            parentChildInteraction = 60,
            parentIndependent = 40,
            characteristicsChild = 70,
            characteristicsFamily = 50,
            isDetailedView = false
        ),
        Case(
            number = 2,
            interviewDate = "April 15, 2023",
            firstName = "Jane",
            lastName = "Smith",
            personnummer = "19000302017892",
            overallRisk = "Medium Risk",
            parentChildInteraction = 70,
            parentIndependent = 50,
            characteristicsChild = 60,
            characteristicsFamily = 40,
            isDetailedView = false
        ),
        Case(
            number = 3,
            interviewDate = "May 20, 2023",
            firstName = "Alice",
            lastName = "Johnson",
            personnummer = "19000302017892",
            overallRisk = "High Risk",
            parentChildInteraction = 80,
            parentIndependent = 60,
            characteristicsChild = 75,
            characteristicsFamily = 70,
            isDetailedView = false
        ),
        Case(
            number = 4,
            interviewDate = "June 10, 2023",
            firstName = "Emily",
            lastName = "Williams",
            personnummer = "19000302017892",
            overallRisk = "Medium Risk",
            parentChildInteraction = 65,
            parentIndependent = 45,
            characteristicsChild = 55,
            characteristicsFamily = 60,
            isDetailedView = false
        ),
        Case(
            number = 5,
            interviewDate = "July 5, 2023",
            firstName = "Michael",
            lastName = "Anderson",
            personnummer = "19000302017892",
            overallRisk = "High Risk",
            parentChildInteraction = 75,
            parentIndependent = 65,
            characteristicsChild = 70,
            characteristicsFamily = 75,
            isDetailedView = false
        ),
        Case(
            number = 6,
            interviewDate = "August 12, 2023",
            firstName = "Olivia",
            lastName = "Martinez",
            personnummer = "19000302017892",
            overallRisk = "Low Risk",
            parentChildInteraction = 55,
            parentIndependent = 35,
            characteristicsChild = 45,
            characteristicsFamily = 50,
            isDetailedView = false
        ),
        Case(
            number = 7,
            interviewDate = "September 18, 2023",
            firstName = "James",
            lastName = "Brown",
            personnummer = "19000302017892",
            overallRisk = "Medium Risk",
            parentChildInteraction = 70,
            parentIndependent = 50,
            characteristicsChild = 60,
            characteristicsFamily = 65,
            isDetailedView = false
        ),
        Case(
            number = 8,
            interviewDate = "October 24, 2023",
            firstName = "Sophia",
            lastName = "Garcia",
            personnummer = "19000302017892",
            overallRisk = "High Risk",
            parentChildInteraction = 80,
            parentIndependent = 70,
            characteristicsChild = 75,
            characteristicsFamily = 80,
            isDetailedView = false
        )
    )

    LazyColumn {
        items(dummyCases) { case ->
            CaseCard(case)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewCase() {
    var caseNumber by remember { mutableStateOf(0) }
    var personnummer by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Title(title = "Add New Case", modifier = Modifier.padding(20.dp))
        TextField(
            value = caseNumber.toString(),
            onValueChange = {
                caseNumber = it.toIntOrNull() ?: 0
            },
            label = { Text("Case Number") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

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

        Button(
            onClick = { /* Non-functional button */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Take Risk Assessment")
        }
    }
}

@Composable
fun CaseCard(case: Case) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Case Number: ${case.number}", fontSize = 18.sp)
                IconButton(
                    onClick = { isExpanded = !isExpanded },
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            }
            Text("Interview Date: ${case.interviewDate}")
            Text("Name: ${case.firstName} ${case.lastName}")
            Text("Personnummer: ${case.personnummer}")
            Text("Overall Risk: ${case.overallRisk}")

            if (isExpanded) {
                Column {
                    Text("Risk by Section:")
                    Text("Parent-child interaction: ${case.parentChildInteraction}%")
                    Text("The parent independent of the child: ${case.parentIndependent}%")
                    Text("Characteristics of child excluding parent: ${case.characteristicsChild}%")
                    Text("Characteristics of the family: ${case.characteristicsFamily}%")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { /* Handle "See Interview" button click */ },
                        ) {
                            Text("See Assessment")
                        }
                        Button(
                            onClick = { /* Handle "Resubmit Interview" button click */ },
                        ) {
                            Text("Resubmit Assessment")
                        }
                    }
                }
            }
        }
    }
}

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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RiskAssessmentProtoypeTheme {
        Greeting("Android")
    }
}