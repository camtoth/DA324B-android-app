package com.example.riskassesmentapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.riskassesmentapp.ui.theme.RiskAssesmentAppTheme

class InformationScreen (navController: NavController) {
    @Composable
    fun Content() {
        LazyColumn (modifier = Modifier.fillMaxSize()){
            item {
                Card ( modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .padding(10.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row ( modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp, 0.dp, 0.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically){
                        Icon(imageVector = Icons.Default.Info, contentDescription = "info",
                            modifier = Modifier
                                .size(34.dp))
                        Text("Information",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(10.dp))
                    }
                }

            }
            item {
                Text(text = "Välkommen till Forskning och Riskbedömning. " +
                        "Syftet med appen är att stödja riskbedömningar i vårdnadstvister. " +
                        "Appen sammanfattar och sammanväger vetenskapligt framtagna skydds- och " +
                        "riskfaktorer för fysisk barnmisshandel respektive. Andra former av vanvård " +
                        "stöds inte. Resultatet av appens analys blir en rekommendation, och ansvariga" +
                        " utredare ska även beakta sin yrkeskunskap inför slutlig riskbedömning.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(15.dp))

            }

        }
        
    }
}
@Preview
@Composable
fun ShowContent(){
    RiskAssesmentAppTheme {
        LazyColumn (modifier = Modifier.fillMaxSize()){
            item {
                Card ( modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 30.dp, 10.dp, 10.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
                    elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                    Row ( modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp, 0.dp, 0.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically){
                        Icon(imageVector = Icons.Default.Info, contentDescription = "info",
                            modifier = Modifier
                                .size(34.dp))
                        Text("Information",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(10.dp))
                    }
                }

            }
            item {
                Text(text = "Välkommen till Forskning och Riskbedömning. " +
                        "Syftet med appen är att stödja riskbedömningar i vårdnadstvister. " +
                        "Appen sammanfattar och sammanväger vetenskapligt framtagna skydds- och " +
                        "riskfaktorer för fysisk barnmisshandel respektive. Andra former av vanvård " +
                        "stöds inte. Resultatet av appens analys blir en rekommendation, och ansvariga" +
                        " utredare ska även beakta sin yrkeskunskap inför slutlig riskbedömning.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(15.dp))

            }

        }
    }
}