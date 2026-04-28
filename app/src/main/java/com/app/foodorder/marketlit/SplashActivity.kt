package com.app.foodorder.marketlit

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Sub CPMK-3: Intent untuk navigasi antar Activity
        Handler(Looper.getMainLooper()).postDelayed({
            // Asumsi setelah Splash langsung masuk ke MainActivity (tempat Fragment berada)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}