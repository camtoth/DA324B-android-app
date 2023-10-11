package com.example.riskassesmentapp.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale



@Composable
fun AppTitle(){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "RiskAssessmentPrototype",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "Risk Assessment Aid",
            fontStyle = FontStyle.Italic,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
    fun GetTodaysDate(){
        val currentDate = remember  { Calendar.getInstance()}

        val formattedDate = remember {
            val dateFormat = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault())
            dateFormat.format(currentDate.time)
        }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = formattedDate,
            style = MaterialTheme.typography.titleMedium
        )
    }
    }

    @Composable
    fun HomeScreenButton(
        navController: NavController,
        destination: String,
        icon: ImageVector,
        text: String,
        buttonColor: Color,
        textColor: Color
    ) {
        Card(
            modifier = Modifier
                .clickable {
                    navController.navigate(destination)
                }
                .aspectRatio(1f)
                .padding(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = buttonColor
        ),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )

        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = textColor
                )
                Spacer(modifier = Modifier.height(4.dp)) // Adjust the spacing as needed
                Text(
                    text = text,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.labelLarge,
                    color = textColor
                )
            }
        }
    }

@Preview
@Composable 
fun PreviewHomeScreen(){
    Box{
        Text(
            text = "Research Driven",
            style = MaterialTheme.typography.titleLarge
        )
    }

//    ButtonWithIconText()
}

