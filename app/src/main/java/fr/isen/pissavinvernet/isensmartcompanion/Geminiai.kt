package fr.isen.pissavinvernet.isensmartcompanion

import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val API_KEY = "AIzaSyARDaR6sr5wD8OvlD9UuqItATjNPyZeO60"

suspend fun getGeminiResponse(userInput: String, dispatcher: CoroutineDispatcher = Dispatchers.IO): String {
    return withContext(dispatcher) {
        try {
            val aiModel = GenerativeModel(
                modelName = "gemini-1.5-flash",
                apiKey = API_KEY
            )
            aiModel.generateContent(userInput).text ?: "No response received"
        } catch (exception: Exception) {
            "Error encountered: ${exception.localizedMessage}"
        }
    }
}
