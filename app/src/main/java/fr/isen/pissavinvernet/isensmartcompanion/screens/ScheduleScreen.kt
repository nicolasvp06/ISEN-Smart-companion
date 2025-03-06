package fr.isen.pissavinvernet.isensmartcompanion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun AgendaScreen(courses: List<Course>, events: List<AgendaEvent>) {
    // La liste des cours reste inchangée dans sa référence
    val courses = remember { mutableStateListOf(
        Course("Français", "08:00 - 10:00", "Salle A1", professorName = "Mme Dupont"),
        Course("Italien", "10:15 - 12:15", "Salle B2", professorName = "M. Rossi"),
        Course("Informatique", "14:00 - 16:00", "Salle C3", professorName = "M. Martin")
    ) }
    val eventsList = remember { mutableStateListOf(
        AgendaEvent("Gala", "15 Mars 2025"),
        AgendaEvent("Soirée Plage", "22 Juillet 2025")
    ) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .padding(16.dp)
    ) {
        // Titre de l'agenda
        Text(
            text = "Agenda",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD32F2F),
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Affichage des cours
        SectionTitle("Courses")
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn {
            items(courses) { course ->
                CourseItem(course = course, onDelete = { courses.remove(course) })
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Affichage des événements
        SectionTitle("Events")
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn {
            items(eventsList) { event ->
                EventItem(event = event)
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFD32F2F),
        fontFamily = FontFamily.Monospace,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        textAlign = TextAlign.Start
    )
}

@Composable
fun CourseItem(course: Course, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .border(2.dp, Color(0xFFD32F2F), shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White) // Fond blanc pour la cellule
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Ligne avec le nom du cours et la salle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = course.courseName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = course.courseRoom,
                    fontSize = 18.sp,
                    color = Color(0xFFD32F2F),
                    fontFamily = FontFamily.Monospace
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            // Affichage du nom du professeur, si renseigné
            if (course.professorName.isNotEmpty()) {
                Text(
                    text = course.professorName,
                    fontSize = 18.sp,
                    color = Color(0xFFD32F2F),
                    fontFamily = FontFamily.Monospace
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            // Horaires en dessous
            Text(
                text = course.courseTime,
                fontSize = 18.sp,
                color = Color.LightGray,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Composable
fun EventItem(event: AgendaEvent) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .border(2.dp, Color(0xFFD32F2F), shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = event.eventName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = event.eventDate,
                fontSize = 18.sp,
                color = Color.LightGray,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

// Data classes
data class Course(
    val courseName: String,
    val courseTime: String,
    val courseRoom: String,
    val professorName: String = ""
)

data class AgendaEvent(
    val eventName: String,
    val eventDate: String
)
