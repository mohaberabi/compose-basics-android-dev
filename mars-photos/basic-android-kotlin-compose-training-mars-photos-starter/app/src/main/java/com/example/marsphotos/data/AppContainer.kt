package com.example.marsphotos.data

import com.example.marsphotos.network.MarsService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {

    val marsRepository: MarsRepository


}


class DefaultAppContainer : AppContainer {
    companion object {
        private const val BASE_URL =
            "https://android-kotlin-fun-mars-server.appspot.com"

    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: MarsService by lazy {
        retrofit.create(MarsService::class.java)
    }

    override val marsRepository: MarsRepository
        get() = MarsRepositoryImpl(retrofitService)
}