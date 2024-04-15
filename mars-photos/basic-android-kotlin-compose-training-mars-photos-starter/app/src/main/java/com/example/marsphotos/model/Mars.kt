package com.example.marsphotos.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Mars(
    val id: String,
    @SerialName(value = "img_src")

    val image: String
)