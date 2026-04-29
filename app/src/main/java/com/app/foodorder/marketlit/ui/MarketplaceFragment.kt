package com.app.foodorder.marketlit.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.foodorder.marketlit.R
import com.app.foodorder.marketlit.adapter.BurungMarketAdapter
import com.app.foodorder.marketlit.model.BurungItem

class MarketplaceFragment : Fragment() {

    private lateinit var adapter: BurungMarketAdapter
    private lateinit var rvMarket: RecyclerView
    private lateinit var tvJumlah: TextView
    private lateinit var etSearch: EditText

    // Filter aktif
    private var activeFilter = "Semua"

    // ────────────────────────────────────────────
    // DATA DUMMY — ganti dengan API / ViewModel
    // ────────────────────────────────────────────
    private val allItems = listOf(
        BurungItem(
            id = "1", nama = "Murai Batu Medan Gacor", jenis = "Murai",
            harga = 2_500_000, lokasi = "Jakarta Selatan", kondisi = "Gacor",
            penjual = "Pak Joko", ratingPenjual = 4.9f, bgAmber = false,
            emojiGambar = "🐦", isFeatured = true, stokTersedia = true,
            deskripsi = "Murai Batu Medan, gacor isian banyak, bodi panjang, " +
                    "ekor rapi. Siap lomba. Sudah makan voer & kroto."
        ),
        BurungItem(
            id = "2", nama = "Kenari Yorkshire F2", jenis = "Kenari",
            harga = 850_000, lokasi = "Bandung", kondisi = "Siap Lomba",
            penjual = "Bu Siti", ratingPenjual = 4.7f, bgAmber = true,
            emojiGambar = "🐤", isFeatured = false, stokTersedia = true,
            deskripsi = "Kenari Yorkshire F2, warna kuning solid, suara panjang, " +
                    "jinak dan sehat. Sudah vaksin ND."
        ),
        BurungItem(
            id = "3", nama = "Lovebird Dakocan Ngekek", jenis = "Lovebird",
            harga = 650_000, lokasi = "Surabaya", kondisi = "Ngekek Panjang",
            penjual = "Mas Andi", ratingPenjual = 4.8f, bgAmber = false,
            emojiGambar = "💚", isFeatured = false, stokTersedia = true,
            deskripsi = "LB Dakocan ngekek panjang, mental bagus, " +
                    "sudah sering ikut latber dan selalu juara kelas B."
        ),
        BurungItem(
            id = "4", nama = "Kacer Poci Betina", jenis = "Murai",
            harga = 400_000, lokasi = "Yogyakarta", kondisi = "Sehat",
            penjual = "Pak Budi", ratingPenjual = 4.5f, bgAmber = false,
            emojiGambar = "🐦", isFeatured = false, stokTersedia = false,
            deskripsi = "Kacer poci betina, lincah, makan voer, " +
                    "cocok untuk master atau ternak."
        ),
        BurungItem(
            id = "5", nama = "Cucak Hijau Full Isian", jenis = "Murai",
            harga = 1_200_000, lokasi = "Semarang", kondisi = "Full Isian",
            penjual = "Mas Rudi", ratingPenjual = 4.6f, bgAmber = false,
            emojiGambar = "🦜", isFeatured = true, stokTersedia = true,
            deskripsi = "Cucak hijau full isian, isian murai, kenari, dan ciblek. " +
                    "Mental besi, sudah juara di beberapa event regional."
        ),
        BurungItem(
            id = "6", nama = "Perkutut Lokal Manggung", jenis = "Perkutut",
            harga = 300_000, lokasi = "Solo", kondisi = "Manggung",
            penjual = "Pak Hadi", ratingPenjual = 4.4f, bgAmber = true,
            emojiGambar = "🕊️", isFeatured = false, stokTersedia = true,
            deskripsi = "Perkutut lokal, sudah manggung rutin. " +
                    "Suara merdu dan nyaring. Harga nego."
        ),
        BurungItem(
            id = "7", nama = "Sangkar Bulat Minimalis", jenis = "Aksesori",
            harga = 150_000, lokasi = "Bekasi", kondisi = "Baru",
            penjual = "Toko KicauJaya", ratingPenjual = 4.8f, bgAmber = true,
            emojiGambar = "🧰", isFeatured = false, stokTersedia = true,
            deskripsi = "Sangkar bambu bulat finishing halus, ukuran 40cm, " +
                    "cocok untuk lovebird dan kenari."
        ),
        BurungItem(
            id = "8", nama = "Anis Kembang Siap Gacor", jenis = "Murai",
            harga = 750_000, lokasi = "Malang", kondisi = "Gacor",
            penjual = "Bang Deni", ratingPenjual = 4.7f, bgAmber = false,
            emojiGambar = "🐦", isFeatured = false, stokTersedia = true,
            deskripsi = "Anis kembang jantan dewasa, gacor isian lengkap, " +
                    "bodi padat, ekor panjang. Bisa nego tipis."
        ),
    )

    // ────────────────────────────────────────────

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_marketplace, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvMarket = view.findViewById(R.id.rvMarketplace)
        tvJumlah = view.findViewById(R.id.tvJumlahHasil)
        etSearch = view.findViewById(R.id.etSearch)

        // Setup RecyclerView Grid 2 kolom
        adapter = BurungMarketAdapter(allItems)
        rvMarket.layoutManager = GridLayoutManager(requireContext(), 2)
        rvMarket.adapter = adapter

        setupFilterChips(view)
        setupSearch()
        setupSort(view)
    }

    // ── Filter Chip ──────────────────────────────
    private fun setupFilterChips(view: View) {
        val chips = mapOf(
            "Semua"    to view.findViewById<TextView>(R.id.chipSemua),
            "Murai"    to view.findViewById<TextView>(R.id.chipMurai),
            "Kenari"   to view.findViewById<TextView>(R.id.chipKenari),
            "Lovebird" to view.findViewById<TextView>(R.id.chipLovebird),
            "Perkutut" to view.findViewById<TextView>(R.id.chipPerkutut),
            "Aksesori" to view.findViewById<TextView>(R.id.chipAksesori),
        )

        chips.forEach { (label, chip) ->
            chip.setOnClickListener {
                activeFilter = label
                updateChipStyle(chips, label)
                applyFilters(etSearch.text.toString())
            }
        }
    }

    private fun updateChipStyle(chips: Map<String, TextView>, active: String) {
        chips.forEach { (label, chip) ->
            if (label == active) {
                chip.setBackgroundResource(R.drawable.bg_chip_active)
                chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            } else {
                chip.setBackgroundResource(R.drawable.bg_chip_inactive)
                chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.g30b))
            }
        }
    }

    // ── Search ───────────────────────────────────
    private fun setupSearch() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, st: Int, c: Int, a: Int) {}
            override fun onTextChanged(s: CharSequence?, st: Int, b: Int, c: Int) {
                applyFilters(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // ── Sort ─────────────────────────────────────
    private var sortAscending = true

    private fun setupSort(view: View) {
        val btnSort = view.findViewById<LinearLayout>(R.id.btnSort)
        val tvSort  = view.findViewById<TextView>(R.id.tvSortLabel)
        btnSort.setOnClickListener {
            sortAscending = !sortAscending
            tvSort.text = if (sortAscending) "Harga ↑" else "Harga ↓"
            applyFilters(etSearch.text.toString())
        }
    }

    // ── Apply filter + search + sort ─────────────
    private fun applyFilters(query: String) {
        var result = if (activeFilter == "Semua") allItems
        else allItems.filter { it.jenis == activeFilter }

        if (query.isNotBlank()) {
            val q = query.lowercase()
            result = result.filter {
                it.nama.lowercase().contains(q) ||
                        it.jenis.lowercase().contains(q) ||
                        it.lokasi.lowercase().contains(q)
            }
        }

        result = if (sortAscending) result.sortedBy { it.harga }
        else result.sortedByDescending { it.harga }

        adapter.updateData(result)
        tvJumlah.text = "${result.size} burung tersedia"
    }
}