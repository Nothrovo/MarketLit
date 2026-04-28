package com.app.foodorder.marketlit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginEmailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_email)

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

            // Navigasi ke Dashboard (Ganti MainActivity dengan nama class dashboard-mu jika beda)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // finish() agar user tidak kembali ke halaman login setelah masuk ke dashboard
            finish()
        }

        // Navigasi ke Daftar Gratis
        tvGoToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Opsional: Jika ingin memberi aksi pada tombol Google/Facebook
        // Pastikan di XML sudah ditambahkan android:id="@+id/btnGoogle" dll
        /*
        findViewById<Button>(R.id.btnGoogle)?.setOnClickListener {
            Toast.makeText(this, "Login Google dipilih", Toast.LENGTH_SHORT).show()
        }
        */
    }
}