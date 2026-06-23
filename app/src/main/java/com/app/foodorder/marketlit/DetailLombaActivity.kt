package com.app.foodorder.marketlit

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailLombaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lomba)

        // Menerima data dari Intent
        val lomba = intent.getSerializableExtra("EXTRA_LOMBA") as? Lomba

        val btnBack: TextView     = findViewById(R.id.btnBackLomba)
        val tvTitle: TextView     = findViewById(R.id.tvDetailTitle)
        val tvDate: TextView      = findViewById(R.id.tvDetailDate)
        val tvCategory: TextView  = findViewById(R.id.tvDetailCategory)
        val tvStatus: TextView    = findViewById(R.id.tvDetailStatus)
        val tvPrize: TextView     = findViewById(R.id.tvDetailPrize)
        val tvDesc: TextView      = findViewById(R.id.tvDetailDesc)
        val btnDaftar: Button     = findViewById(R.id.btnDaftarLomba)

        // Tombol back
        btnBack.setOnClickListener { finish() }

        // Bind data lomba
        lomba?.let {
            tvTitle.text    = it.title
            tvDate.text     = it.dateLocation
            tvCategory.text = it.categories
            tvPrize.text    = it.prize
            tvDesc.text     = it.description

            // Badge status
            val (bgColor, textColor) = when (it.status) {
                "Dibuka" -> Pair("#E8F5E9", "#1B5E20")
                "Segera" -> Pair("#FFF8E1", "#8A5220")
                else      -> Pair("#F0F0EE", "#8A9590") // Selesai
            }
            tvStatus.text = it.status
            tvStatus.setTextColor(Color.parseColor(textColor))
            val badgeDrawable = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                setColor(Color.parseColor(bgColor))
                cornerRadius = 100f  // Force full pill shape
            }
            tvStatus.background = badgeDrawable
        }

        // Tombol DAFTAR LOMBA
        btnDaftar.setOnClickListener {
            Toast.makeText(
                this,
                "Pendaftaran berhasil! Kami akan menghubungi kamu segera 🏆",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}