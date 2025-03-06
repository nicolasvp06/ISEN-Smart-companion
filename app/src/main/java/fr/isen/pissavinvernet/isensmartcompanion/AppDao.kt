package fr.isen.pissavinvernet.isensmartcompanion

import androidx.room.*
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface InteractionDao {
    @Query("SELECT * FROM interaction")
    fun getAllInteractions(): Flow<List<Interaction>>
    @Insert
    suspend fun insertInteraction(interaction: Interaction)
    @Delete
    suspend fun deleteInteraction(interaction: Interaction)

    @Query("DELETE FROM interaction")
    suspend fun deleteAllInteractions()
}