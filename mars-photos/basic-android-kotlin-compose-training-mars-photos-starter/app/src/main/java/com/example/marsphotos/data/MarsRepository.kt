package com.example.marsphotos.data

import com.example.marsphotos.model.Mars
import com.example.marsphotos.network.MarsService

interface MarsRepository {
    suspend fun getMarsPhotos(): List<Mars>
}

class MarsRepositoryImpl(private val marsService: MarsService) : MarsRepository {
    override suspend fun getMarsPhotos(): List<Mars> {
        return marsService.getPhotos()
    }
}