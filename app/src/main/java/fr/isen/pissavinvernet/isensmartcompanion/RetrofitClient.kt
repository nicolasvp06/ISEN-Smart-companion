package fr.isen.pissavinvernet.isensmartcompanion

import fr.isen.pissavinvernet.isensmartcompanion.events.EventMod
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.http.GET

object RetrofitInstance {
    // URL
    private const val BASE_URL = "https://isen-smart-companion-default-rtdb.europe-west1.firebasedatabase.app/"

    // Initialisation
    val retrofit: Retrofit by lazy {
        Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }

    val retrofitService: RetrofitService by lazy {
        retrofit.create(RetrofitService::class.java)
    }
}

interface RetrofitService {
    @GET("events.json")
    fun getEvents(): Call<List<EventMod>>
}
