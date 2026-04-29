package com.app.foodorder.marketlit

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Styling status bar biar sinkron sama Header Sage Dark temen lo (#3E5C44)
        window.statusBarColor = Color.parseColor("#3E5C44")

        // 2. Load DashboardFragment lo (PASTIKAN NAMA CLASS-NYA BENER)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DashboardFragment())
                .commit()
        }
    }
}