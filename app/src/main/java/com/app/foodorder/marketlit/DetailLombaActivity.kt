package com.app.foodorder.marketlit

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailLombaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lomba)

        // Sub CPMK-3: Menerima data dari Intent
        val lomba = intent.getSerializableExtra("EXTRA_LOMBA") as? Lomba

        val tvTitle: TextView = findViewById(R.id.tvDetailTitle)
        val tvDate: TextView = findViewById(R.id.tvDetailDate)
        val tvPrize: TextView = findViewById(R.id.tvDetailPrize)
        val tvDesc: TextView = findViewById(R.id.tvDetailDesc)
        val btnDaftar: Button = findViewById(R.id.btnDaftar)

        // Set text berdasarkan data yang dikirim dari LombaFragment
        lomba?.let {
            tvTitle.text = it.title
            tvDate.text = "${it.dateLocation}\nKategori: ${it.categories}"
            tvPrize.text = "Total Hadiah:\n${it.prize}"
            tvDesc.text = it.description
        }

        btnDaftar.setOnClickListener {
            Toast.makeText(this, "Berhasil mendaftar ke ${lomba?.title}!", Toast.LENGTH_SHORT).show()
        }
    }
}