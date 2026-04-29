package com.app.foodorder.marketlit

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginEmailActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_email)

        sharedPreferences = getSharedPreferences("USER_SESSION", MODE_PRIVATE)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvGoToRegister = findViewById<TextView>(R.id.tvGoToRegister)
        val btnGoogle = findViewById<Button>(R.id.btnGoogle)
        val btnFacebook = findViewById<Button>(R.id.btnFacebook)

        // 1. Tombol Kembali (Arrow Kiri) -> Ke LandingActivity
        btnBack.setOnClickListener {
            val intent = Intent(this, LandingActivity::class.java)
            startActivity(intent)
            finish() // Tutup halaman login ini
        }

        // 2. Berhasil Login Pakai Email -> Ke MainActivity
        btnLogin.setOnClickListener {
            Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()

            // Simpan status login
            sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()

            // Navigasi ke Dashboard
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 3. Login Pakai Google -> Ke MainActivity
        btnGoogle.setOnClickListener {
            Toast.makeText(this, "Login Google Berhasil!", Toast.LENGTH_SHORT).show()
            sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 4. Login Pakai Facebook -> Ke MainActivity
        btnFacebook.setOnClickListener {
            Toast.makeText(this, "Login Facebook Berhasil!", Toast.LENGTH_SHORT).show()
            sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 5. Navigasi ke Daftar Gratis -> Ke RegisterActivity
        tvGoToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish() // Opsional: tutup login biar kalau user pencet back di Register, gak balik kesini (sesuai selera alurmu)
        }
    }
}