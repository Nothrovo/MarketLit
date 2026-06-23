package com.app.foodorder.marketlit

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.foodorder.marketlit.db.MarketLitRepository
import java.text.NumberFormat
import java.util.Locale

// ─────────────────────────────────────────────────────────
//  Data classes
// ─────────────────────────────────────────────────────────
data class RiwayatLomba(
    val namaLomba: String,
    val tanggal: String,
    val lokasi: String,
    val hasil: String,
    val id: Int = 0,
    val userId: Int = 0,
    val lombaId: Int = 0
)

data class RiwayatTransaksi(
    val namaItem: String,
    val harga: Long,
    val tanggal: String,
    val tipe: String,
    val id: Int = 0,
    val userId: Int = 0
)

// ─────────────────────────────────────────────────────────
//  Adapter Lomba
// ─────────────────────────────────────────────────────────
class RiwayatLombaAdapter(
    private val items: List<RiwayatLomba>
) : RecyclerView.Adapter<RiwayatLombaAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvNamaLomba: TextView  = view.findViewById(R.id.tvNamaLomba)
        val tvTanggalLomba: TextView = view.findViewById(R.id.tvTanggalLomba)
        val tvLokasiLomba: TextView  = view.findViewById(R.id.tvLokasiLomba)
        val tvHasilLomba: TextView   = view.findViewById(R.id.tvHasilLomba)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_riwayat_lomba, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.tvNamaLomba.text    = item.namaLomba
        holder.tvTanggalLomba.text = item.tanggal
        holder.tvLokasiLomba.text  = item.lokasi
        holder.tvHasilLomba.text   = item.hasil

        val (bgColor, textColor) = if (item.hasil.startsWith("Juara")) {
            Pair("#FFF8E1", "#8A5220")
        } else {
            Pair("#E8F5E9", "#1B5E20")
        }
        holder.tvHasilLomba.setTextColor(Color.parseColor(textColor))
        val badgeDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(Color.parseColor(bgColor))
            cornerRadius = 20f
        }
        holder.tvHasilLomba.background = badgeDrawable
    }
}

// ─────────────────────────────────────────────────────────
//  Adapter Transaksi
// ─────────────────────────────────────────────────────────
class RiwayatTransaksiAdapter(
    private val items: List<RiwayatTransaksi>
) : RecyclerView.Adapter<RiwayatTransaksiAdapter.VH>() {

    private val rupiahFormat = NumberFormat.getNumberInstance(Locale("id", "ID"))

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvNamaItem: TextView    = view.findViewById(R.id.tvNamaItem)
        val tvHargaItem: TextView   = view.findViewById(R.id.tvHargaItem)
        val tvTanggalItem: TextView = view.findViewById(R.id.tvTanggalItem)
        val tvTipeItem: TextView    = view.findViewById(R.id.tvTipeItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_riwayat_transaksi, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.tvNamaItem.text    = item.namaItem
        holder.tvHargaItem.text   = "Rp ${rupiahFormat.format(item.harga)}"
        holder.tvTanggalItem.text = item.tanggal
        holder.tvTipeItem.text    = item.tipe

        val (bgColor, textColor) = if (item.tipe == "Dibeli") {
            Pair("#E8F5E9", "#1B5E20")
        } else {
            Pair("#FFF8E1", "#B87333")
        }
        holder.tvTipeItem.setTextColor(Color.parseColor(textColor))
        val badgeDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(Color.parseColor(bgColor))
            cornerRadius = 20f
        }
        holder.tvTipeItem.background = badgeDrawable
    }
}

// ─────────────────────────────────────────────────────────
//  Activity
// ─────────────────────────────────────────────────────────
class RiwayatActivity : AppCompatActivity() {

    private lateinit var rvLomba: RecyclerView
    private lateinit var rvTransaksi: RecyclerView
    private lateinit var tvTabLomba: TextView
    private lateinit var tvTabTransaksi: TextView
    private lateinit var indicatorLomba: View
    private lateinit var indicatorTransaksi: View
    private lateinit var btnBackRiwayat: Button
    private lateinit var repository: MarketLitRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat)

        repository = MarketLitRepository(this)
        val userId = getSharedPreferences("USER_SESSION", MODE_PRIVATE).getInt("USER_ID", 1)

        rvLomba           = findViewById(R.id.rvRiwayatLomba)
        rvTransaksi       = findViewById(R.id.rvRiwayatTransaksi)
        tvTabLomba        = findViewById(R.id.tvTabLomba)
        tvTabTransaksi    = findViewById(R.id.tvTabTransaksi)
        indicatorLomba    = findViewById(R.id.indicatorLomba)
        indicatorTransaksi= findViewById(R.id.indicatorTransaksi)
        btnBackRiwayat    = findViewById(R.id.btnBackRiwayat)

        val lombaData = repository.getRiwayatLombaByUserId(userId)
        val transaksiData = repository.getRiwayatTransaksiByUserId(userId)

        rvLomba.layoutManager = LinearLayoutManager(this)
        rvLomba.adapter = RiwayatLombaAdapter(lombaData)

        rvTransaksi.layoutManager = LinearLayoutManager(this)
        rvTransaksi.adapter = RiwayatTransaksiAdapter(transaksiData)

        tvTabLomba.setOnClickListener { showTab("lomba") }
        tvTabTransaksi.setOnClickListener { showTab("transaksi") }

        btnBackRiwayat.setOnClickListener { finish() }

        val tab = intent.getStringExtra("TAB") ?: "lomba"
        showTab(tab)
    }

    private fun showTab(tab: String) {
        if (tab == "lomba") {
            tvTabLomba.setTypeface(null, android.graphics.Typeface.BOLD)
            tvTabLomba.setTextColor(Color.parseColor("#1B5E20"))
            indicatorLomba.visibility = View.VISIBLE

            tvTabTransaksi.setTypeface(null, android.graphics.Typeface.NORMAL)
            tvTabTransaksi.setTextColor(Color.parseColor("#9E9E9E"))
            indicatorTransaksi.visibility = View.INVISIBLE

            rvLomba.visibility = View.VISIBLE
            rvTransaksi.visibility = View.GONE
        } else {
            tvTabTransaksi.setTypeface(null, android.graphics.Typeface.BOLD)
            tvTabTransaksi.setTextColor(Color.parseColor("#1B5E20"))
            indicatorTransaksi.visibility = View.VISIBLE

            tvTabLomba.setTypeface(null, android.graphics.Typeface.NORMAL)
            tvTabLomba.setTextColor(Color.parseColor("#9E9E9E"))
            indicatorLomba.visibility = View.INVISIBLE

            rvTransaksi.visibility = View.VISIBLE
            rvLomba.visibility = View.GONE
        }
    }
}
