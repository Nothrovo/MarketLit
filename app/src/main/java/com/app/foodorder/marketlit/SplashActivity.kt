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

        supportActionBar?.hide()

        // Logika delay 3 detik (3000 milidetik) sebelum pindah halaman
        Handler(Looper.getMainLooper()).postDelayed({
            // Pindah ke Landing Activity
            // Di dalam Handler SplashActivity lo, ubah tujuannya ke LandingActivity
            val intent = Intent(this@SplashActivity, LandingActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}