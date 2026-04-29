package com.app.foodorder.marketlit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        val btnMulai = findViewById<Button>(R.id.btn_mulai)
        btnMulai.setOnClickListener {
            // Pindah dari Landing ke Dashboard (MainActivity)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Biar kalau di-back gak balik ke Landing lagi
        }
    }
}