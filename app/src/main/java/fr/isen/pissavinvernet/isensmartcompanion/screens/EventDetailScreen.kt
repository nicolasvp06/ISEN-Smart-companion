package fr.isen.pissavinvernet.isensmartcompanion.screens

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.material3.Card
import androidx.work.*
import java.util.concurrent.TimeUnit
import androidx.compose.ui.platform.LocalContext
import fr.isen.pissavinvernet.isensmartcompanion.Alert
import fr.isen.pissavinvernet.isensmartcompanion.NotificationWorker
import fr.isen.pissavinvernet.isensmartcompanion.events.EventMod

class EventDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jsonEvent = intent.getStringExtra("event_json")
        val event = Gson().fromJson(jsonEvent, EventMod::class.java)

        setContent {
            EventDetailScreen(event = event)
        }
    }
}

@Composable
fun EventDetailScreen(event: EventMod) {
    var isReminderSet by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val reminderPreferences = Alert(context)

    LaunchedEffect(event.id) {
        isReminderSet = reminderPreferences.getReminder(event.id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = event.title.lowercase(),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

        Card(
            modifier = Modifier.fillMaxWidth().height(120.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Text(
                text = "Catégorie: ${event.category}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Card(
            modifier = Modifier.fillMaxWidth().height(120.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Text(
                text = "${event.date} - ${event.location}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Card(
            modifier = Modifier.fillMaxWidth().height(160.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Text(
                text = event.description,
                fontSize = 22.sp,
                fontStyle = FontStyle.Italic,
                color = Color.Black,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Justify
            )
        }
    }
}

fun scheduleNotification(context: Context, eventId: String) {
    val inputData = workDataOf("eventId" to eventId)
    val notificationWorkRequest: WorkRequest =
        OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(10, TimeUnit.SECONDS)
            .setInputData(inputData)
            .build()
    WorkManager.getInstance(context).enqueue(notificationWorkRequest)
}
