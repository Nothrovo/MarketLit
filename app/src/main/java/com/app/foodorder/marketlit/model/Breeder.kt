package com.app.foodorder.marketlit.model

import java.io.Serializable

data class Breeder(
    val id: String,
    val name: String,
    val farmName: String,
    val location: String,
    val rating: String,
    val emoji: String,
    val description: String
) : Serializable
