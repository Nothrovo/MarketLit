package com.app.foodorder.marketlit.model

import java.io.Serializable

data class Breeder(
    val id: Int = 0,
    val userId: Int = 0,
    val name: String = "",
    val farmName: String = "",
    val location: String = "",
    val rating: Float = 0f,
    val emoji: String = "",
    val description: String = ""
) : Serializable
