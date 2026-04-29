package com.app.foodorder.marketlit

import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.foodorder.marketlit.profil.ProfilFragment
import com.app.foodorder.marketlit.ui.MarketplaceFragment

// Pastikan fragment-fragment lu ter-import di sini ya

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Styling status bar
        window.statusBarColor = Color.parseColor("#1B5E20")

        // Load fragment pertama kali aplikasi dibuka
        if (savedInstanceState == null) {
            loadFragment(LombaFragment()) // Gua set langsung ke LombaFragment tugas lu
        }

        // ==========================================
        // LOGIC UNTUK CUSTOM NAVBAR (LINEAR LAYOUT)
        // ==========================================

        // 1. Tangkap semua ID tombol dari layout navbar lu
        val navHome = findViewById<LinearLayout>(R.id.navHome)
        val navLomba = findViewById<LinearLayout>(R.id.navLomba)
        val navMarket = findViewById<LinearLayout>(R.id.navMarket)
        val navVet = findViewById<LinearLayout>(R.id.navVet)
        val navProfil = findViewById<LinearLayout>(R.id.navProfil)

        // 2. Kasih perintah klik (OnClickListener) untuk ganti Fragment
        navHome.setOnClickListener {
            loadFragment(DashboardFragment()) // Buka komen (hapus //) kalau file-nya udah lu/temen lu bikin
        }

        navLomba.setOnClickListener {
            // Ini page tugas lu
            loadFragment(LombaFragment())
        }

        navMarket.setOnClickListener {
            // Contoh kalau temen lu udah bikin MarketplaceFragment:
            loadFragment(MarketplaceFragment())
        }

        navVet.setOnClickListener {
            loadFragment(DokterFragment())
        }

        navProfil.setOnClickListener {
            loadFragment(ProfilFragment())
        }
    }

    // Fungsi sakti buat masukin Fragment ke dalam FrameLayout (container)
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment) // Pastikan ID ini sama dengan FrameLayout di activity_main.xml lu
            .commit()
    }
}