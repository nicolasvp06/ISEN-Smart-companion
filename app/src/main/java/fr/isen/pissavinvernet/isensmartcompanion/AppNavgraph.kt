package fr.isen.pissavinvernet.isensmartcompanion

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.isen.pissavinvernet.isensmartcompanion.screens.AgendaEvent
import fr.isen.pissavinvernet.isensmartcompanion.screens.AgendaScreen
import fr.isen.pissavinvernet.isensmartcompanion.screens.Course
import fr.isen.pissavinvernet.isensmartcompanion.screens.EventsScreen
import fr.isen.pissavinvernet.isensmartcompanion.screens.MainScreen

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier) {

    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val interactionDao = database.interactionDao()

    val coursesList = listOf(
        Course("Français", "8h - 10h", "Salle A1"),
        Course("Italien", "10h15 - 12h15", "Salle B2"),
        Course("Informatique", "14h - 16h", "Salle C3")
    )

    val eventsList = listOf(
        AgendaEvent("Gale", "15 mars 2025"),
        AgendaEvent("Soirée plage", "22 juillet 2025")
    )


    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            MainScreen()
        }
        composable("events") {
            EventsScreen(navController)
        }
        composable("agenda") {
            AgendaScreen(courses = coursesList, events = eventsList)
        }
        composable("history") {
            HistoryScreen(interactionDao)
        }
    }
}
