package com.app.foodorder.marketlit

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        // Ambil status login dari SharedPreferences
        sharedPreferences = getSharedPreferences("USER_SESSION", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = if (isLoggedIn) {
                Intent(this@SplashActivity, MainActivity::class.java)
            } else {
                Intent(this@SplashActivity, LandingActivity::class.java)
            }

            startActivity(intent)
            finish()

        }, 3000)
    }
}