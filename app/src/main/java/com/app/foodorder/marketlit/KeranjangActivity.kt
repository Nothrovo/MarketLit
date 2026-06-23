package com.app.foodorder.marketlit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.foodorder.marketlit.model.BurungItem
import com.app.foodorder.marketlit.ui.MarketplaceFragment
import java.text.NumberFormat
import java.util.Locale

class KeranjangActivity : AppCompatActivity() {

    private lateinit var rvKeranjang: RecyclerView
    private lateinit var tvTotalHarga: TextView
    private lateinit var btnCheckout: TextView
    private lateinit var btnBack: TextView
    private lateinit var layoutKosong: View
    private lateinit var keranjangAdapter: KeranjangAdapter

    private val rupiahFormat = NumberFormat.getNumberInstance(Locale("id", "ID"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang)

        rvKeranjang  = findViewById(R.id.rvKeranjang)
        tvTotalHarga = findViewById(R.id.tvTotalHarga)
        btnCheckout  = findViewById(R.id.btnCheckout)
        btnBack      = findViewById(R.id.btnBackKeranjang)
        layoutKosong = findViewById(R.id.layoutKosong)

        // Tampilkan warna status bar sesuai topbar
        window.statusBarColor = android.graphics.Color.parseColor("#3E5C44")

        // Back button
        btnBack.setOnClickListener { finish() }

        // Setup adapter
        keranjangAdapter = KeranjangAdapter(
            MarketplaceFragment.keranjangItems,
            onHapus = { item ->
                MarketplaceFragment.keranjangItems.remove(item)
                refreshUI()
            }
        )
        rvKeranjang.layoutManager = LinearLayoutManager(this)
        rvKeranjang.adapter = keranjangAdapter

        // Checkout
        btnCheckout.setOnClickListener {
            if (MarketplaceFragment.keranjangItems.isEmpty()) {
                Toast.makeText(this, "Keranjang masih kosong!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "✅ Pesanan berhasil dibuat! Terima kasih.", Toast.LENGTH_LONG).show()
                MarketplaceFragment.keranjangItems.clear()
                refreshUI()
            }
        }

        refreshUI()
    }

    private fun refreshUI() {
        val items = MarketplaceFragment.keranjangItems
        if (items.isEmpty()) {
            layoutKosong.visibility = View.VISIBLE
            rvKeranjang.visibility  = View.GONE
        } else {
            layoutKosong.visibility = View.GONE
            rvKeranjang.visibility  = View.VISIBLE
            keranjangAdapter.notifyDataSetChanged()
        }
        val total = items.sumOf { it.harga }
        tvTotalHarga.text = "Rp ${rupiahFormat.format(total)}"
    }
}

// ── Adapter keranjang inline ──────────────────────────────────────────────────
class KeranjangAdapter(
    private val items: MutableList<BurungItem>,
    private val onHapus: (BurungItem) -> Unit
) : RecyclerView.Adapter<KeranjangAdapter.KeranjangViewHolder>() {

    private val rupiahFormat = NumberFormat.getNumberInstance(Locale("id", "ID"))

    inner class KeranjangViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvEmoji   : TextView = itemView.findViewById(R.id.tvEmojiKeranjang)
        val tvNama    : TextView = itemView.findViewById(R.id.tvNamaKeranjang)
        val tvHarga   : TextView = itemView.findViewById(R.id.tvHargaKeranjang)
        val tvPenjual : TextView = itemView.findViewById(R.id.tvPenjualKeranjang)
        val btnHapus  : TextView = itemView.findViewById(R.id.btnHapusItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeranjangViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_keranjang, parent, false)
        return KeranjangViewHolder(v)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: KeranjangViewHolder, position: Int) {
        val item = items[position]
        holder.tvEmoji.text   = item.emojiGambar
        holder.tvNama.text    = item.nama
        holder.tvHarga.text   = "Rp ${rupiahFormat.format(item.harga)}"
        holder.tvPenjual.text = "📦 ${item.penjual} · ${item.lokasi}"
        holder.btnHapus.setOnClickListener { onHapus(item) }
    }
}
