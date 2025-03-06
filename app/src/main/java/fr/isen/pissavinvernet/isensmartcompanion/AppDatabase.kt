package fr.isen.pissavinvernet.isensmartcompanion

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Interaction::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun interactionDao(): InteractionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            INSTANCE?.let { return it }

            // Synchro
            return synchronized(this) {

                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "interactions_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
