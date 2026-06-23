package com.app.foodorder.marketlit.model

import java.io.Serializable

data class BurungItem(
    val id: String,
    val nama: String,
    val jenis: String,
    val harga: Long,
    val lokasi: String,
    val kondisi: String,
    val penjual: String,
    val ratingPenjual: Float,
    val deskripsi: String,
    val emojiGambar: String,
    val bgAmber: Boolean = false,
    val isFeatured: Boolean = false,
    val stokTersedia: Boolean = true,
    val penjualId: Int = 0
) : Serializable