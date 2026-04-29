package com.app.foodorder.marketlit

import java.io.Serializable

data class Lomba(
    val id: Int,
    val title: String,
    val dateLocation: String,
    val categories: String,
    val status: String,
    val prize: String,
    val description: String
) : Serializable // Penting buat passing data antar Activity!