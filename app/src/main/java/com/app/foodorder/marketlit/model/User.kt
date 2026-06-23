package com.app.foodorder.marketlit.model

import java.io.Serializable

data class User(
    val id: Int = 0,
    val nama: String = "",
    val email: String = "",
    val phone: String = "",
    val lokasi: String = "",
    val jenisBurungAndalan: String = "",
    val role: String = "Pembeli",
    val avatar: String = ""
) : Serializable
