package com.example.riskassesmentapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.riskassesmentapp.R
import com.example.riskassesmentapp.db.Parent
import com.example.riskassesmentapp.models.Case
import com.example.riskassesmentapp.ui.theme.BrightGreen
import com.example.riskassesmentapp.ui.theme.LightBlue
import com.example.riskassesmentapp.ui.theme.LightGray
import com.example.riskassesmentapp.ui.theme.PaleGreen
import com.example.riskassesmentapp.ui.theme.RiskAssesmentAppTheme
import java.util.LinkedList


@Composable
fun CasesList() {
    val dummyCases = listOf(
    Case(
        caseNr = "44",
        email = "email1@email.com",
        gender = "F",
        givenNames = "Suzy",
        id = 12345679333,
        lastName = "Svensson",
        personnr = "123423451234"
    ),
    Case(
        caseNr = "45",
        email = "email2@email.com",
        gender = "M",
        givenNames = "John",
        id = 987654321,
        lastName = "Doe",
        personnr = "9876543210"
    ),
    Case(
        caseNr = "46",
        email = "email3@email.com",
        gender = "F",
        givenNames = "Alice",
        id = 555555555,
        lastName = "Johnson",
        personnr = "5555555555"
    ),
    Case(
        caseNr = "47",
        email = "email4@email.com",
        gender = "M",
        givenNames = "Bob",
        id = 444444444,
        lastName = "Smith",
        personnr = "4444444444"
    ),
    Case(
        caseNr = "48",
        email = "email5@email.com",
        gender = "F",
        givenNames = "Eva",
        id = 123456789,
        lastName = "Williams",
        personnr = "1234567890"
    ),
    Case(
        caseNr = "49",
        email = "email6@email.com",
        gender = "M",
        givenNames = "David",
        id = 9876543210,
        lastName = "Brown",
        personnr = "9876543211"
    ),
    Case(
        caseNr = "50",
        email = "email7@email.com",
        gender = "F",
        givenNames = "Sophia",
        id = 111111111,
        lastName = "Miller",
        personnr = "1111111111"
    ),
    Case(
        caseNr = "51",
        email = "email8@email.com",
        gender = "M",
        givenNames = "Oliver",
        id = 222222222,
        lastName = "Taylor",
        personnr = "2222222222"
    ),
    Case(
        caseNr = "52",
        email = "email9@email.com",
        gender = "F",
        givenNames = "Charlotte",
        id = 333333333,
        lastName = "Davis",
        personnr = "3333333333"
    ),
    Case(
        caseNr = "53",
        email = "email10@email.com",
        gender = "M",
        givenNames = "James",
        id = 4444444444,
        lastName = "Moore",
        personnr = "4444444445"
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
                Text("Case Number: ${case.caseNr}", fontSize = 18.sp)
            }

        }
    }
}

@Composable
fun ParentInfo(parent: Parent) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Personnummer",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "${parent.personnr}",
                style = MaterialTheme.typography.bodySmall
            )
        }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween){
            Text(
                text = "Kön",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "${parent.gender}",
                style = MaterialTheme.typography.bodySmall
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Senast ändrad",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = parent.lastChanged ?: "Not Available",
                    style = MaterialTheme.typography.bodySmall
                )
        }
    }

@Composable
fun ParentSection(parent: Parent, isExpanded: Boolean, onToggle: () -> Unit) {
    Column(
        modifier = Modifier
            .background(color = LightBlue,
                shape = RoundedCornerShape(8.dp)),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .background(LightBlue)
                .padding(10.dp, 0.dp)
                .fillMaxWidth()
                .clickable { onToggle() }
        ) {
            // Display an arrow icon based on the expansion state
            Text("${parent.givenNames} ${parent.lastName}")
            Icon(
                imageVector = if (isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Display additional content if the parent is expanded
        if (isExpanded) {

            Column (modifier = Modifier.padding(10.dp, 0.dp)) {
                ParentInfo(parent)
                Divider(
                    color = LightGray, thickness = 1.dp, modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally)
                )
                RiskDetails(parent = parent)
                ReviewSection(parent = parent)
            }
        }
    }
}
@Composable
fun RiskDetails(parent: Parent) {
    Row {
        Text(
            "Riskrekommendationer",
            style = MaterialTheme.typography.labelMedium.copy(textDecoration = TextDecoration.Underline)
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row ( modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                "Försummelse",
                style = MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.Underline)
            )
            Row (horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){
                Text(
                    text = "Visa",
                    color = MaterialTheme.colorScheme.primaryContainer,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        textDecoration =  TextDecoration.Underline),
                    modifier = Modifier
                        .padding(8.dp, 4.dp)
                        .clickable {
                            // Handle button click action here
                        }
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Rekommendation:",
                style = MaterialTheme.typography.labelSmall
            )
            parent.highRiskNeglect?.let { RiskLevel(highRisk = it) }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Uppfattad Risk",
                style = MaterialTheme.typography.labelSmall
            )
            parent.estHighRiskNeglect?.let { RiskLevel(highRisk = it) }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)
        {
            Text(
                "Fysisk Skada",
                style = MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.Underline)
            )
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Visa",
                    color = MaterialTheme.colorScheme.primaryContainer,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        textDecoration =  TextDecoration.Underline),
                    modifier = Modifier
                        .padding(8.dp, 4.dp)
                        .clickable {
                        // Handle button click action here
                    }
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Rekommendation:",
                style = MaterialTheme.typography.labelSmall
            )
            parent.highRiskPca?.let { RiskLevel(highRisk = it) }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Upfattad Risk",
                style = MaterialTheme.typography.labelSmall
            )
            parent.estHighRiskPca?.let { RiskLevel(highRisk = it) }
        }
    }
}
    //SectionAnswers(section = "Physical Harm")


@Composable
fun SectionAnswers(section:String){
    //get the answers to the Neglect and Risk Sections 
    Text("$section Question and Answers go here ",
        style = MaterialTheme.typography.bodySmall)
}

@Composable
fun ReviewSection(parent: Parent){
    Column {
        Text(text = "Redigera Assessment",
            style = MaterialTheme.typography.labelMedium)
        //EstimatedRisk(parent = parent)
        ReviewButton(parent = parent)
    }
}

@Composable
fun EstimatedRisk(parent: Parent){
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Estimated Risk Neglect:",
                style = MaterialTheme.typography.labelSmall
            )
            parent.estHighRiskNeglect?.let { RiskLevel(highRisk = it) }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Estimated Risk Physical Harm:",
                style = MaterialTheme.typography.labelSmall
            )
            parent.estHighRiskPca?.let { RiskLevel(highRisk = it) }
        }
    }


}

@Composable
fun ReviewButton(parent: Parent){
    //to do
    Button(
        onClick = { /*to do*/},
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = "Redigera")
    }
}


@Composable
fun DetailedCaseCard(case: Case, parents: LinkedList<Parent>) {
    val parentShowDetails = remember { mutableStateMapOf<Parent, Boolean>() }

    parents.forEach { parent ->
        parentShowDetails[parent] = true
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            item {
                Text(" Ärendenummer | ${case.caseNr}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(10.dp))
            }
            item {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = LightBlue, shape = RoundedCornerShape(8.dp)),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Row ( modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom) {
                        Text("Namn")
                        Text(" ${case.givenNames} ${case.lastName}")
                    }
                    Row ( modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp),
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Personnummer")
                        Text("${case.personnr}")
                    }
                    Row ( modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp),
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Kön")
                        Text(" ${case.gender}")
                    }
                }
            }
            item {
                Row {
                    Text("Föräldrar",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(10.dp, 10.dp)
                    )
                }
            }
            items(parents) { parent ->
                ParentSection(
                    parent = parent,
                    isExpanded = parentShowDetails[parent] ?: false,
                    onToggle = {
                        parentShowDetails[parent] = !parentShowDetails[parent]!!
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable 
fun RiskLevel(highRisk: Boolean){
    val fillColor = if (highRisk) MaterialTheme.colorScheme.error else BrightGreen
    val riskText = if (highRisk) "High" else "Low"

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(fillColor, shape = CircleShape)
        ) {
            // Empty box for the dot
        }

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = riskText,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally)
        )
    }
}

    @Preview
    @Composable
    fun ShowDetailedCaseCard() {
        RiskAssesmentAppTheme {
            val parent1 = Parent(
                caseId = 12345679333,
                gender = "M",
                givenNames = "Sven",
                id = 23437957439,
                lastName = "Svensson",
                personnr = "123424232312",
                estHighRiskNeglect = false,
                highRiskNeglect = false,
                estHighRiskPca = true,
                highRiskPca = true

            )

            val parent2 = Parent(
                caseId = 987654321,
                gender = "F",
                givenNames = "Anna",
                id = 567890123,
                lastName = "Andersson",
                personnr = "9876543210",
                estHighRiskNeglect = false,
                highRiskNeglect = false,
                estHighRiskPca = true,
                highRiskPca = true
            )
            val testParents = LinkedList<Parent>()
            testParents.add(parent1)
            testParents.add(parent2)

            val testCase = Case(
                caseNr = "44",
                email = "email@email.com",
                gender = "F",
                givenNames = "Suzy",
                id = 12345679333,
                lastName = "Svensson",
                personnr = "123423451234",
            )

            DetailedCaseCard(case = testCase, testParents)
        }
    }
