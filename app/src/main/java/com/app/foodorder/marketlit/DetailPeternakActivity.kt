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
import com.app.foodorder.marketlit.db.MarketLitRepository
import com.app.foodorder.marketlit.model.Breeder
import com.app.foodorder.marketlit.ui.MarketplaceFragment

class DetailPeternakActivity : AppCompatActivity() {

    private lateinit var repository: MarketLitRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_peternak)

        repository = MarketLitRepository(this)

        val breeder = intent.getSerializableExtra("EXTRA_BREEDER") as? Breeder ?: return

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
        tvRating.text = "⭐ ${breeder.rating} / 5.0"
        tvDesc.text = breeder.description

        btnBack.setOnClickListener { finish() }

        btnChat.setOnClickListener {
            try {
                val intentChat = Intent(this, ChatDokterActivity::class.java).apply {
                    putExtra("DOKTER_NAMA", breeder.name)
                    putExtra("DOKTER_SPESIALIS", breeder.farmName)
                    putExtra("DOKTER_STATUS", "ONLINE")
                    putExtra("DOKTER_EMOJI", breeder.emoji)
                    putExtra("CHAT_TYPE", "PETERNAK")
                    putExtra("RECEIVER_USER_ID", breeder.userId)
                }
                startActivity(intentChat)
            } catch (e: Exception) {
                Toast.makeText(this, "Gagal membuka chat peternak.", Toast.LENGTH_SHORT).show()
            }
        }

        val breederProducts = repository.getBurungItemsByPenjualId(breeder.userId)

        rvProducts.layoutManager = GridLayoutManager(this, 2)
        rvProducts.adapter = BurungMarketAdapter(breederProducts) { item ->
            MarketplaceFragment.keranjangItems.add(item)
            Toast.makeText(this, "${item.nama} dimasukkan ke keranjang!", Toast.LENGTH_SHORT).show()
        }
    }
}
