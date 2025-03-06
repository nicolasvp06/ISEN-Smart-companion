package fr.isen.pissavinvernet.isensmartcompanion.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Alignment
import fr.isen.pissavinvernet.isensmartcompanion.events.EventMod
import fr.isen.pissavinvernet.isensmartcompanion.RetrofitInstance

@Composable
fun EventsScreen(navController: NavHostController) {
    val context = LocalContext.current
    var events by remember { mutableStateOf<List<EventMod>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        RetrofitInstance.retrofitService.getEvents().enqueue(object : Callback<List<EventMod>> {
            override fun onResponse(call: Call<List<EventMod>>, response: Response<List<EventMod>>) {
                if (response.isSuccessful) {
                    events = response.body()?.shuffled() ?: emptyList()
                } else {
                    errorMessage = "Failed to load events"
                }
                isLoading = false
            }

            override fun onFailure(call: Call<List<EventMod>>, t: Throwable) {
                errorMessage = "Error: ${t.localizedMessage}"
                isLoading = false
            }
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
    ) {
        Text(
            text = "events",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (errorMessage.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        } else {
            LazyColumn {
                items(events) { event ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .height(140.dp)
                            .clickable {
                                val gson = Gson()
                                val intent = Intent(context, EventDetailActivity::class.java)
                                val jsonEvent = gson.toJson(event)
                                intent.putExtra("event_json", jsonEvent)
                                context.startActivity(intent)
                            },
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                color = Color.Black,
                                text = event.title,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEventScreen() {
    EventsScreen(navController = rememberNavController())
}
