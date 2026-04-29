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

                // Reset warna semua chip ke warna netral (Background Cream, Teks Abu-abu)
                roles.forEach {
                    it.setBackgroundColor("#F8F8F6".toColorInt()) // bg_cream
                    it.setTextColor("#4A5550".toColorInt())       // text_mid
                }

                // Set warna chip yang diklik menjadi GELAP (Background Hijau Tua, Teks Putih)
                clicked.setBackgroundColor("#3E5C44".toColorInt()) // sage_dark
                clicked.setTextColor("#FFFFFF".toColorInt())       // bg_white

                Toast.makeText(this, "Mendaftar sebagai $selectedRole", Toast.LENGTH_SHORT).show()
            }
        }

        // Tombol Kembali (Arrow Kiri Atas) diarahkan eksplisit ke LoginEmailActivity
        btnBackReg.setOnClickListener {
            val intent = Intent(this, LoginEmailActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Tombol Buat Akun diarahkan ke LoginEmailActivity
        btnRegister.setOnClickListener {
            Toast.makeText(this, "Akun $selectedRole berhasil dibuat!", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, LoginEmailActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Teks "Masuk di sini" diarahkan ke LoginEmailActivity
        tvGoToLogin.setOnClickListener {
            val intent = Intent(this, LoginEmailActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}