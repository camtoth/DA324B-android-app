package com.example.riskassesmentapp.ui.composables


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.riskassesmentapp.ui.theme.GrayTonedBlue


@Composable
fun BottomNavButton(
    isSelected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    text: String,
) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxHeight()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally ,
            modifier = Modifier.size(width = 120.dp, height = 56.dp) // Add this line
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                text = text,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                style = if (isSelected) MaterialTheme.typography. labelLarge else MaterialTheme.typography.labelMedium,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }
    }
}
@Composable
fun BottomNav(navController: NavController) {
    BottomAppBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(33f)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            BottomNavButton(
                isSelected = currentRoute == "home",
                onClick = { if (currentRoute != "home") navController.navigate("home") },
                icon = Icons.Default.Home,
                text = "Hem"
            )

            BottomNavButton(
                isSelected = currentRoute == "my_cases",
                onClick = { if (currentRoute != "my_cases") navController.navigate("my_cases") },
                icon = Icons.Default.Star,
                text = "Mina Ärenden"
            )

            BottomNavButton(
                isSelected = currentRoute == "settings",
                onClick = { if (currentRoute != "settings") navController.navigate("settings") },
                icon = Icons.Default.Settings,
                text = "Inställningar",
            )
        }
    }
}