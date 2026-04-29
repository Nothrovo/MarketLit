package com.app.foodorder.marketlit

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        // ==========================================
        // 1. BINDING ID & LOGIKA MENU LIST PROFIL
        // ==========================================
        val menuIklanSaya = findViewById<LinearLayout>(R.id.menuIklanSaya)
        val menuRiwayatLomba = findViewById<LinearLayout>(R.id.menuRiwayatLomba)
        val menuPengaturan = findViewById<LinearLayout>(R.id.menuPengaturan)

        // Aksi Klik Menu Iklan Saya
        menuIklanSaya.setOnClickListener {
            Toast.makeText(this, "Membuka Iklan Saya...", Toast.LENGTH_SHORT).show()
        }

        // Aksi Klik Menu Riwayat Lomba (Revisi)
        menuRiwayatLomba.setOnClickListener {
            // Arahkan ke halaman Riwayat Lomba jika sudah ada class-nya:
            // val intent = Intent(this, RiwayatLombaActivity::class.java)
            // startActivity(intent)
            Toast.makeText(this, "Membuka Riwayat Lomba...", Toast.LENGTH_SHORT).show()
        }

        // Aksi Klik Menu Pengaturan
        menuPengaturan.setOnClickListener {
            Toast.makeText(this, "Membuka Pengaturan Akun...", Toast.LENGTH_SHORT).show()
        }

        // ==========================================
        // 2. BINDING ID & LOGIKA BOTTOM NAVIGATION
        // ==========================================
        val navHome = findViewById<LinearLayout>(R.id.navHome)
        val navLomba = findViewById<LinearLayout>(R.id.navLomba)
        val navMarket = findViewById<LinearLayout>(R.id.navMarket)
        val navVet = findViewById<LinearLayout>(R.id.navVet)
        val navProfil = findViewById<LinearLayout>(R.id.navProfil)

        // Navigasi ke Home (Beranda/Dashboard)
        navHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // Hapus animasi biar perpindahan antar tab terasa instan & mulus
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish() // Tutup activity profil agar tidak menumpuk di memori
        }

        // Navigasi ke Lomba
        navLomba.setOnClickListener {
            // Uncomment blok di bawah ini kalau file LombaActivity sudah dibuat:
            /*
            val intent = Intent(this, LombaActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
            */
            Toast.makeText(this, "Pindah ke Tab Lomba...", Toast.LENGTH_SHORT).show()
        }

        // Navigasi ke Market
        navMarket.setOnClickListener {
            // Uncomment blok di bawah ini kalau file MarketActivity sudah dibuat:
            /*
            val intent = Intent(this, MarketActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
            */
            Toast.makeText(this, "Pindah ke Tab Market...", Toast.LENGTH_SHORT).show()
        }

        // Navigasi ke Dokter Vet
        navVet.setOnClickListener {
            // Uncomment blok di bawah ini kalau file VetActivity sudah dibuat:
            /*
            val intent = Intent(this, VetActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
            */
            Toast.makeText(this, "Pindah ke Tab Dokter...", Toast.LENGTH_SHORT).show()
        }

        // Navigasi ke Profil
        navProfil.setOnClickListener {
            // Dibiarkan kosong karena pengguna sudah berada di halaman Profil
        }
    }
}