package com.app.foodorder.marketlit

import com.app.foodorder.marketlit.model.BurungItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("lomba")
    fun getLatestLomba(): Call<List<Lomba>>

    @GET("burung")
    fun getBurungList(): Call<List<BurungItem>>
}