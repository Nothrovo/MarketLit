package com.app.foodorder.marketlit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.foodorder.marketlit.adapter.BurungMarketAdapter
import com.app.foodorder.marketlit.model.Breeder
import com.app.foodorder.marketlit.model.BurungItem

class DetailPeternakActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_peternak)

        // Read Breeder extra
        val breeder = intent.getSerializableExtra("EXTRA_BREEDER") as? Breeder ?: return

        // Bind Views
        val tvEmoji: TextView = findViewById(R.id.tvDetailBreederEmoji)
        val tvName: TextView = findViewById(R.id.tvDetailBreederName)
        val tvFarm: TextView = findViewById(R.id.tvDetailBreederFarm)
        val tvLocation: TextView = findViewById(R.id.tvDetailBreederLocation)
        val tvRating: TextView = findViewById(R.id.tvDetailBreederRating)
        val tvDesc: TextView = findViewById(R.id.tvDetailBreederDesc)
        val btnBack: TextView = findViewById(R.id.btnBackPeternak)
        val btnChat: Button = findViewById(R.id.btnChatDetailPeternak)
        val rvProducts: RecyclerView = findViewById(R.id.rvBreederProducts)

        tvEmoji.text = breeder.emoji
        tvName.text = breeder.name
        tvFarm.text = breeder.farmName
        tvLocation.text = "📍 ${breeder.location}"
        tvRating.text = breeder.rating
        tvDesc.text = breeder.description

        btnBack.setOnClickListener { finish() }

        btnChat.setOnClickListener {
            try {
                val intentChat = Intent(this, Class.forName("com.app.foodorder.marketlit.ChatDokterActivity")).apply {
                    putExtra("DOKTER_NAMA", breeder.name)
                    putExtra("DOKTER_SPESIALIS", breeder.farmName)
                    putExtra("DOKTER_STATUS", "ONLINE")
                    putExtra("DOKTER_EMOJI", breeder.emoji)
                    putExtra("CHAT_TYPE", "PETERNAK")
                }
                startActivity(intentChat)
            } catch (e: Exception) {
                Toast.makeText(this, "Gagal membuka chat peternak.", Toast.LENGTH_SHORT).show()
            }
        }

        // Filter products sold by this breeder
        val allProducts = listOf(
            BurungItem(
                id = "1", nama = "Murai Batu Medan Gacor", jenis = "Burung",
                harga = 2_500_000, lokasi = "Jakarta Selatan", kondisi = "Gacor",
                penjual = "Pak Joko", ratingPenjual = 4.9f, bgAmber = false,
                emojiGambar = "🐦", isFeatured = true, stokTersedia = true,
                deskripsi = "Murai Batu Medan, gacor isian banyak, bodi panjang, ekor rapi. Siap lomba."
            ),
            BurungItem(
                id = "2", nama = "Kenari Yorkshire F2", jenis = "Burung",
                harga = 850_000, lokasi = "Bandung", kondisi = "Siap Lomba",
                penjual = "Bu Siti", ratingPenjual = 4.7f, bgAmber = true,
                emojiGambar = "🐤", isFeatured = false, stokTersedia = true,
                deskripsi = "Kenari Yorkshire F2, warna kuning solid, suara panjang, jinak."
            ),
            BurungItem(
                id = "5", nama = "Cucak Hijau Full Isian", jenis = "Burung",
                harga = 1_200_000, lokasi = "Semarang", kondisi = "Full Isian",
                penjual = "Mas Rudi", ratingPenjual = 4.6f, bgAmber = false,
                emojiGambar = "🦜", isFeatured = true, stokTersedia = true,
                deskripsi = "Cucak hijau full isian, isian murai, kenari, dan ciblek. Mental besi."
            ),
            BurungItem(
                id = "8", nama = "Anis Kembang Siap Gacor", jenis = "Burung",
                harga = 750_000, lokasi = "Malang", kondisi = "Gacor",
                penjual = "Bang Deni", ratingPenjual = 4.7f, bgAmber = false,
                emojiGambar = "🐦", isFeatured = false, stokTersedia = true,
                deskripsi = "Anis kembang jantan dewasa, gacor isian lengkap, bodi padat."
            ),
            BurungItem(
                id = "6", nama = "Perkutut Lokal Manggung", jenis = "Burung",
                harga = 300_000, lokasi = "Solo", kondisi = "Manggung",
                penjual = "Pak Hadi", ratingPenjual = 4.4f, bgAmber = true,
                emojiGambar = "🕊️", isFeatured = false, stokTersedia = true,
                deskripsi = "Perkutut lokal, sudah manggung rutin. Suara merdu."
            )
        )

        val breederProducts = allProducts.filter { it.penjual == breeder.name }

        rvProducts.layoutManager = GridLayoutManager(this, 2)
        rvProducts.adapter = BurungMarketAdapter(breederProducts) { item ->
            Toast.makeText(this, "${item.nama} dimasukkan ke keranjang!", Toast.LENGTH_SHORT).show()
        }
    }
}
