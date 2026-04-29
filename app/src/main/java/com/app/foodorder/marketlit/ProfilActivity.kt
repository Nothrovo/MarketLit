package com.app.foodorder.marketlit

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.foodorder.marketlit.profil.ProfilFragment

class ProfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        // Load fragment hanya saat pertama kali (hindari duplikasi saat rotate)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ProfilFragment.newInstance())
                .commit()
        }

        setupBottomNav()
    }

    private fun setupBottomNav() {
        val navHome     = findViewById<LinearLayout>(R.id.navHome)
        val navLomba    = findViewById<LinearLayout>(R.id.navLomba)
        val navMarket   = findViewById<LinearLayout>(R.id.navMarket)
        val navVet      = findViewById<LinearLayout>(R.id.navVet)
        val navProfil   = findViewById<LinearLayout>(R.id.navProfil)

        navHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            })
            finish()
        }

        navLomba.setOnClickListener {
            Toast.makeText(this, "Pindah ke Tab Lomba...", Toast.LENGTH_SHORT).show()
        }

        navMarket.setOnClickListener {
            Toast.makeText(this, "Pindah ke Tab Market...", Toast.LENGTH_SHORT).show()
        }

        navVet.setOnClickListener {
            Toast.makeText(this, "Pindah ke Tab Dokter...", Toast.LENGTH_SHORT).show()
        }

        navProfil.setOnClickListener { /* sudah di halaman ini */ }
    }
}