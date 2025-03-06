package fr.isen.pissavinvernet.isensmartcompanion.main

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.ViewAgenda
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf("home", "events", "history", "agenda")
    val icons = listOf(
        Icons.Filled.Home,
        Icons.Filled.Event,
        Icons.Filled.History,
        Icons.Filled.MenuBook
    )
    val labels = listOf("Home", "Events", "History", "Agenda")

    var selectedItem by rememberSaveable { mutableStateOf(0) }

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = labels[index],
                        tint = if (selectedItem == index) Color.Black else Color.Gray
                    )
                },
                label = {
                    Text(
                        text = labels[index],
                        color = if (selectedItem == index) Color.Black else Color.Gray
                    )
                },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(screen)
                },
                colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}