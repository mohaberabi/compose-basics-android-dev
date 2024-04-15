package com.example.marsphotos.network

import com.example.marsphotos.model.Mars
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET


interface MarsService {


    @GET("photos")
    suspend fun getPhotos(): List<Mars>
}


