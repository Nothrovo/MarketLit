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

        // Tombol Kembali
        btnBack.setOnClickListener {
            finish()
        }

        // Berhasil Login
        btnLogin.setOnClickListener {
            // Logika verifikasi bisa ditaruh di sini nanti
            Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()

            // ✅ Simpan status login
            val editor = sharedPreferences.edit()
            editor.putBoolean("isLoggedIn", true)
            editor.apply()

            // Navigasi ke Dashboard
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            finish()
        }

        // Navigasi ke Daftar Gratis
        tvGoToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        /*
        findViewById<Button>(R.id.btnGoogle)?.setOnClickListener {
            Toast.makeText(this, "Login Google dipilih", Toast.LENGTH_SHORT).show()
        }
        */
    }
}