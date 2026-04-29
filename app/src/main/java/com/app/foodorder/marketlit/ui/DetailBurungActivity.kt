package com.app.foodorder.marketlit.ui

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.foodorder.marketlit.R
import com.app.foodorder.marketlit.model.BurungItem
import java.text.NumberFormat
import java.util.*

class DetailBurungActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_burung)

        // 1. Ambil data pakai kunci yang ada di companion object (EXTRA_BURUNG)
        val item = intent.getSerializableExtra(EXTRA_BURUNG) as? BurungItem

        if (item != null) {
            // 2. Langsung panggil setupUI saja agar tidak double kodenya
            setupUI(item)
        } else {
            Toast.makeText(this, "Data burung tidak ditemukan", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnBeli).setOnClickListener {
            Toast.makeText(this, "Pesanan untuk ${item?.nama} diproses!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupUI(item: BurungItem) {
        findViewById<TextView>(R.id.tvDetailEmoji).text = item.emojiGambar
        findViewById<TextView>(R.id.tvDetailNama).text = item.nama
        findViewById<TextView>(R.id.tvDetailDeskripsi).text = item.deskripsi
        findViewById<TextView>(R.id.tvDetailPenjual).text = item.penjual
        findViewById<TextView>(R.id.tvDetailLokasi).text = item.lokasi
        findViewById<TextView>(R.id.tvPenjualInitial).text = item.penjual.take(1)

        val localeID = Locale("id", "ID") // "id" untuk Indonesia
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        findViewById<TextView>(R.id.tvDetailHarga).text = formatRupiah.format(item.harga)
    }

    companion object {
        // Ini adalah kunci standar yang akan dipakai Adapter dan Activity ini
        const val EXTRA_BURUNG = "extra_burung"
    }
}