package fr.isen.pissavinvernet.isensmartcompanion

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // Extraction de l'ID de l'événement depuis les données d'entrée, sinon échec immédiat
        val eventId = inputData.getString("eventId") ?: return Result.failure()

        // Vérifier si le rappel est activé pour cet événement
        val reminderPrefs = Alert(applicationContext)
        if (!reminderPrefs.getReminder(eventId)) {
            return Result.success()
        }

        // Configuration du canal de notification pour Android Oreo et versions ultérieures
        initNotificationChannel()

        // Construction de la notification à afficher
        val notification = NotificationCompat.Builder(applicationContext, "event_channel")
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Utilisation de l'icône système par défaut
            .setContentTitle("Upcoming Event Alert Reminder ")
            .setContentText("Don't miss out, your event is just around the corner!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()


        val notifManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notifManager.notify(eventId.hashCode(), notification)

        return Result.success()
    }

    private fun initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "event_channel",
                "Event Notifications informations",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for some events who will appear"
            }
            val manager = applicationContext.getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }
}
