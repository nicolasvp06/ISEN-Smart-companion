package fr.isen.pissavinvernet.isensmartcompanion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.foundation.shape.RoundedCornerShape
import fr.isen.pissavinvernet.isensmartcompanion.AppDatabase
import fr.isen.pissavinvernet.isensmartcompanion.Interaction
import fr.isen.pissavinvernet.isensmartcompanion.getGeminiResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current
    var userInput by remember { mutableStateOf(TextFieldValue("")) }
    var messages by remember { mutableStateOf<List<Pair<String, String>>>(emptyList()) }

    val database = AppDatabase.getDatabase(context)
    val interactionDao = database.interactionDao()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Home",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = "ISEN",
            fontSize = 144.sp,
            color = Color(0xFFD00000),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Smart Companion",
            fontSize = 19.sp,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Nicolas",
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(0.5f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { (user, aiResponse) ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = "MOI: $user",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 13.sp
                    )
                    Text(
                        text = "SMART COMPANION: $aiResponse",
                        color = Color.Black,
                        fontSize = 13.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        // Augmentation de la hauteur à 96.dp (80.dp * 1.2)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(Color.White, shape = MaterialTheme.shapes.medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                placeholder = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(text = "Ask a question", color = Color.Black)
                    }
                },
                textStyle = TextStyle(fontSize = 20.sp, color = Color.Black),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            IconButton(
                onClick = {
                    val inputText = userInput.text
                    if (inputText.isNotEmpty()) {
                        userInput = TextFieldValue("")
                        messages = messages + (inputText to "Chargement...")
                        CoroutineScope(Dispatchers.Main).launch {
                            val response = getGeminiResponse(inputText)
                            val responseText = response ?: "No response available"
                            messages = messages.dropLast(1) + (inputText to responseText)
                            val interaction = Interaction(
                                question = inputText,
                                answer = responseText,
                                date = System.currentTimeMillis()
                            )
                            CoroutineScope(Dispatchers.IO).launch {
                                interactionDao.insertInteraction(interaction)
                            }
                        }
                    }
                },
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp)
                    .background(Color.Black, shape = RoundedCornerShape(8.dp))
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowUpward,
                    contentDescription = "Envoyer",
                    tint = Color.White,
                    modifier = Modifier.rotate(90f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AssistantUIPreview() {
    MainScreen()
}