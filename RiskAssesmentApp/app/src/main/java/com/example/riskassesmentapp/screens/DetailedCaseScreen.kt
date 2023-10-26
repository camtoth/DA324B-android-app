package com.example.riskassesmentapp.screens

import android.database.sqlite.SQLiteDatabase
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.riskassesmentapp.db.Case
import com.example.riskassesmentapp.db.DatabaseOpenHelper
import com.example.riskassesmentapp.db.Parent
import com.example.riskassesmentapp.ui.composables.ParentSection
import com.example.riskassesmentapp.ui.theme.LightBlue
import java.util.LinkedList

class DetailedCaseScreen(private val navController: NavController,
                         private val databaseOpenHelper: DatabaseOpenHelper,
                         private val parents: LinkedList<Parent>,
                         private val case: Case   ) {
    @Composable
    fun Content() {
        val db = databaseOpenHelper.readableDatabase
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
                        db = db,
                        isExpanded = parentShowDetails[parent] ?: false,
                        onToggle = {
                            parentShowDetails[parent] = !parentShowDetails[parent]!!
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}



