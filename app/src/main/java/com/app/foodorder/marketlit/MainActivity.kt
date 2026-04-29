package com.app.foodorder.marketlit

import android.os.Bundle
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Kita matikan fitur EdgeToEdge bawaan template biar aplikasi
        // gak dipaksa "nyelip" ke bawah tombol navigasi HP Xiaomi lo.
        setContentView(R.layout.activity_main)

        // Bikin barisan jam/baterai tetep hijau tua biar ganteng
        window.statusBarColor = Color.parseColor("#1B5E20")
    }
}