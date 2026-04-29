package com.app.foodorder.marketlit

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.foodorder.marketlit.ui.MarketplaceFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Styling status bar
        window.statusBarColor = Color.parseColor("#1B5E20")

        // Load fragment pertama (biar gak double saat rotasi)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MarketplaceFragment())
                .commit()
        }
    }
}