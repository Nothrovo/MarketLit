package com.app.foodorder.marketlit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt

class RegisterActivity : AppCompatActivity() {

    private var selectedRole = "Pembeli"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnBackReg = findViewById<ImageView>(R.id.btnBackReg)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvGoToLogin = findViewById<TextView>(R.id.tvGoToLogin)

        // Komponen Role Picker
        val rolePembeli = findViewById<TextView>(R.id.rolePembeli)
        val rolePenjual = findViewById<TextView>(R.id.rolePenjual)
        val rolePeternak = findViewById<TextView>(R.id.rolePeternak)

        // Logika Klik Role Picker
        val roles = listOf(rolePembeli, rolePenjual, rolePeternak)
        for (role in roles) {
            role.setOnClickListener { view ->
                val clicked = view as TextView
                selectedRole = clicked.text.toString()

                // Reset warna semua chip ke warna netral (Cream #F8F8F6)
                roles.forEach {
                    it.setBackgroundColor("#F8F8F6".toColorInt())
                    it.setTextColor("#4A5550".toColorInt()) // text_mid
                }

                // Set warna chip yang diklik ke Hijau Muda (#EDF4EF)
                clicked.setBackgroundColor("#EDF4EF".toColorInt())
                clicked.setTextColor("#3E5C44".toColorInt()) // sage_dark

                Toast.makeText(this, "Mendaftar sebagai $selectedRole", Toast.LENGTH_SHORT).show()
            }
        }

        // Tombol Kembali
        btnBackReg.setOnClickListener {
            finish()
        }

        // Berhasil Register
        btnRegister.setOnClickListener {
            // Menampilkan pesan sukses (opsional)
            Toast.makeText(this, "Akun $selectedRole berhasil dibuat!", Toast.LENGTH_SHORT).show()

            // Navigasi ke LoginEmailActivity
            val intent = Intent(this, LoginEmailActivity::class.java)
            startActivity(intent)

            // finish() digunakan agar saat user menekan tombol 'Back' di HP,
            // mereka tidak kembali lagi ke halaman registrasi yang sudah sukses.
            finish()
        }

        // Navigasi balik ke Login
        tvGoToLogin.setOnClickListener {
            val intent = Intent(this, LoginEmailActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}