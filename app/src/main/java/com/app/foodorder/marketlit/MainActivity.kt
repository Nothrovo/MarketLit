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
        ThemeHelper.applyTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Styling status bar
        window.statusBarColor = Color.parseColor("#1B5E20")

        // Load fragment pertama kali aplikasi dibuka
        if (savedInstanceState == null) {
            loadFragment(DashboardFragment())
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
            loadFragment(DashboardFragment())
        }

        navLomba.setOnClickListener {
            loadFragment(LombaFragment())
        }

        navMarket.setOnClickListener {
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
    fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment) // Pastikan ID ini sama dengan FrameLayout di activity_main.xml lu
            .commit()

        // Automatically update bottom navigation active state based on fragment type
        when (fragment) {
            is DashboardFragment -> updateBottomNav(R.id.navHome)
            is LombaFragment -> updateBottomNav(R.id.navLomba)
            is MarketplaceFragment -> updateBottomNav(R.id.navMarket)
            is DokterFragment -> updateBottomNav(R.id.navVet)
            is ProfilFragment -> updateBottomNav(R.id.navProfil)
        }
    }

    fun updateBottomNav(activeId: Int) {
        val navHomeText = findViewById<android.widget.TextView>(R.id.navHomeText)
        val navHomeDot = findViewById<android.view.View>(R.id.navHomeDot)
        val navLombaText = findViewById<android.widget.TextView>(R.id.navLombaText)
        val navLombaDot = findViewById<android.view.View>(R.id.navLombaDot)
        val navMarketText = findViewById<android.widget.TextView>(R.id.navMarketText)
        val navMarketDot = findViewById<android.view.View>(R.id.navMarketDot)
        val navVetText = findViewById<android.widget.TextView>(R.id.navVetText)
        val navVetDot = findViewById<android.view.View>(R.id.navVetDot)
        val navProfilText = findViewById<android.widget.TextView>(R.id.navProfilText)
        val navProfilDot = findViewById<android.view.View>(R.id.navProfilDot)

        val poppinsRegular = androidx.core.content.res.ResourcesCompat.getFont(this, R.font.poppins)
        val poppinsBold = androidx.core.content.res.ResourcesCompat.getFont(this, R.font.poppins_bold)
        val sageDark = Color.parseColor("#3E5C44")
        val textLight = Color.parseColor("#8A9590")

        val textViews = mapOf(
            R.id.navHome to navHomeText,
            R.id.navLomba to navLombaText,
            R.id.navMarket to navMarketText,
            R.id.navVet to navVetText,
            R.id.navProfil to navProfilText
        )
        val dots = mapOf(
            R.id.navHome to navHomeDot,
            R.id.navLomba to navLombaDot,
            R.id.navMarket to navMarketDot,
            R.id.navVet to navVetDot,
            R.id.navProfil to navProfilDot
        )

        for (id in textViews.keys) {
            val tv = textViews[id]
            val dot = dots[id]
            if (id == activeId) {
                tv?.setTextColor(sageDark)
                tv?.typeface = poppinsBold
                dot?.visibility = android.view.View.VISIBLE
            } else {
                tv?.setTextColor(textLight)
                tv?.typeface = poppinsRegular
                dot?.visibility = android.view.View.GONE
            }
        }
    }
}