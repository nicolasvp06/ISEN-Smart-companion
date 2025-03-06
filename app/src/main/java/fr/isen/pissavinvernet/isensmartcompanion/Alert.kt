package fr.isen.pissavinvernet.isensmartcompanion

import android.content.Context
import android.content.SharedPreferences

class Alert(context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
    }


    fun saveReminder(eventId: String, isReminderSet: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(eventId, isReminderSet)
            apply()
        }
    }


    fun getReminder(eventId: String): Boolean =
        sharedPreferences.getBoolean(eventId, false)
}
