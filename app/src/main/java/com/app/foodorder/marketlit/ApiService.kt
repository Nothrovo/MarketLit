package com.app.foodorder.marketlit

import retrofit2.Call
import retrofit2.http.GET

// Sub CPMK-7: Web Service dan parsing JSON format
interface ApiService {
    @GET("api/lomba/terbaru") // Endpoint disesuaikan jadi lomba
    fun getLatestLomba(): Call<List<Lomba>> // Bird diganti jadi Lomba
}