package com.app.foodorder.marketlit

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
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

        // Biar status bar warna sage dark sesuai HTML
        window.statusBarColor = Color.parseColor("#3E5C44")

        sharedPreferences = getSharedPreferences("USER_SESSION", MODE_PRIVATE)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvGoToRegister = findViewById<TextView>(R.id.tvGoToRegister)

        // Tombol Kembali
        btnBack.setOnClickListener {
            finish()
        }

        // Berhasil Login
        btnLogin.setOnClickListener {
            Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()

            // Simpan status login
            val editor = sharedPreferences.edit()
            editor.putBoolean("isLoggedIn", true)
            editor.apply()

            // PINDAH KE MAIN ACTIVITY (DASHBOARD)
            val intent = Intent(this, MainActivity::class.java)
            // Flag ini penting biar setelah login, user gak bisa "back" ke halaman login lagi
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // Navigasi ke Daftar Gratis
        tvGoToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}