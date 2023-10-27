package com.example.riskassesmentapp.ui.composables

import android.database.sqlite.SQLiteDatabase
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.riskassesmentapp.db.Parent
import com.example.riskassesmentapp.db.Case
import com.example.riskassesmentapp.db.QuestionWithAnswer
import com.example.riskassesmentapp.db.getQuestionsWithAnswerByParent
import androidx.navigation.NavController
import com.example.riskassesmentapp.models.Case
import com.example.riskassesmentapp.ui.theme.BrightGreen
import com.example.riskassesmentapp.ui.theme.LightBlue
import com.example.riskassesmentapp.ui.theme.LightGray
import java.util.LinkedList


@Composable
fun CasesList(cases: List<Case>, onClick: (Case) -> Unit) {
    LazyColumn {
        items(cases) { case ->
            CaseCard(case, onClick = { onClick(case) })
        }
    }
}

@Composable
fun CaseCard(case: Case, onClick: () -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 0.dp)
            .clickable(onClick = onClick) ,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Icon(
                Icons.Default.Email, // Replace with your icon's resource ID
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.Black
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Text
            Text(
                text = "Case No. | ${case.caseNr}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
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
fun ParentSection(parent: Parent, db: SQLiteDatabase, isExpanded: Boolean, onToggle: () -> Unit) {
    Column(
        modifier = Modifier
            .background(
                color = LightBlue,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp)),
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

            Column(modifier = Modifier.padding(10.dp, 0.dp)) {
                ParentInfo(parent)
                Divider(
                    color = LightGray, thickness = 1.dp, modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally)
                )
                RiskDetails(parent = parent, db = db)
                ReviewSection(parent = parent)
            }
        }
    }
}

@Composable
fun RiskDetails(parent: Parent, db: SQLiteDatabase) {
    var areQuestionsVisible by remember { mutableStateOf(false) }

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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Försummelse",
                style = MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.Underline)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Visa",
                    color = MaterialTheme.colorScheme.primaryContainer,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier
                        .padding(8.dp, 4.dp)
                        .clickable {
                            areQuestionsVisible = true
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                "Fysisk Skada",
                style = MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.Underline)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Visa",
                    color = MaterialTheme.colorScheme.primaryContainer,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier
                        .padding(8.dp, 4.dp)
                        .clickable {
                            areQuestionsVisible = true
                        }
                )
                if (areQuestionsVisible) {
                    ShowSectionAnswers(
                        section = "pca",
                        parent = parent,
                        db = db,
                        isVisible = areQuestionsVisible,
                        onDismiss = {
                            areQuestionsVisible = false
                        }
                    )
                }
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

@Composable
fun ShowSectionAnswers(  section: String,
                         parent: Parent,
                         db: SQLiteDatabase,
                         isVisible: Boolean,
                         onDismiss: () -> Unit) {
    //get the answers to the Neglect and Risk Sections
    if (isVisible) {
        Dialog(
            onDismissRequest = {
                onDismiss() // Call the callback to dismiss the dialog
            }
        ) {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Column {
                    var allQuestions by remember { mutableStateOf<List<QuestionWithAnswer>?>(null) }
                    LaunchedEffect(db, parent.id) {
                        val questions = getQuestionsWithAnswerByParent(db, parent.id)
                        allQuestions = questions
                    }
                    val neglectQuestions = LinkedList<QuestionWithAnswer>()
                    for (question in allQuestions!!) {
                        if (question.rNeglect != null) {
                            neglectQuestions.add(question)
                        }
                        // Close button (X)
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon",
                            modifier = Modifier
                                .align(Alignment.End)
                                .clickable {
                                    onDismiss() // Call the callback to dismiss the dialog
                                }
                        )
                        // Convert the linked list to a readable text
                        Text(
                            text = if (section == "N") neglectQuestions.joinToString(separator = "\n")
                            else allQuestions!!.joinToString(separator = "\n"),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ReviewSection(parent: Parent){
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {
//        Text(text = "Redigera Assessment",
//            style = MaterialTheme.typography.labelMedium)
        //EstimatedRisk(parent = parent)
        ReviewButton(parent = parent)
    }
}

@Composable
fun ReviewButton(parent: Parent){
    Button(
        onClick = { /* Handle button click action */ },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Pencil Icon",
                tint = MaterialTheme.colorScheme.onPrimaryContainer // Icon color
            )
            Text(
                text = "Redigera",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer // Text color
            )
        }
    }
}

@Composable
fun EditAssessment(){
    //should allow for the edits of an assessment
}

@Composable
fun RiskLevel(highRisk: Boolean) {
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
