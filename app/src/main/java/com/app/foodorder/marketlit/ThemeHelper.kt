package com.app.foodorder.marketlit

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object ThemeHelper {
    fun applyTheme(context: Context) {
        val prefs = context.getSharedPreferences("USER_PROFILE", Context.MODE_PRIVATE)
        val mode = prefs.getInt("THEME_MODE", 0) // 0 = system, 1 = light, 2 = dark
        val nightMode = when (mode) {
            1 -> AppCompatDelegate.MODE_NIGHT_NO
            2 -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }
}
