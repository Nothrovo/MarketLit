package com.app.foodorder.marketlit.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.foodorder.marketlit.R
import com.app.foodorder.marketlit.adapter.BurungMarketAdapter
import com.app.foodorder.marketlit.model.BurungItem
import com.app.foodorder.marketlit.model.Breeder
import com.app.foodorder.marketlit.adapter.PeternakAdapter
import com.app.foodorder.marketlit.DetailPeternakActivity

class MarketplaceFragment : Fragment() {

    companion object {
        val keranjangItems = mutableListOf<BurungItem>()

        fun newInstance(initialFilter: String = "Semua"): MarketplaceFragment {
            val fragment = MarketplaceFragment()
            val args = Bundle()
            args.putString("INITIAL_FILTER", initialFilter)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var adapter: BurungMarketAdapter
    private lateinit var rvMarket: RecyclerView
    private lateinit var tvJumlah: TextView
    private lateinit var etSearch: EditText

    // Filter aktif
    private var activeFilter = "Semua"

    private val breedersList = listOf(
        Breeder("1", "Pak Joko", "Joko Murai Farm", "Jakarta Selatan", "⭐ 4.9 (42 ulasan)", "👨‍🌾", "Spesialis penangkaran Murai Batu ekor panjang dengan trah juara dan mental petarung."),
        Breeder("2", "Bu Siti", "Siti Kenari Jaya", "Bandung", "⭐ 4.7 (35 ulasan)", "👩‍🌾", "Fokus pada breeding Kenari Yorkshire dan Kenari lokal kualitas suara nyaring panjang."),
        Breeder("3", "Mas Rudi", "Rudi Hijau Farm", "Semarang", "⭐ 4.6 (18 ulasan)", "🧑‍🌾", "Pakar breeding Cucak Hijau dan Anis Merah siap kontes dengan pakan herbal alami."),
        Breeder("4", "Bang Deni", "Deni Anis Kembang", "Malang", "⭐ 4.7 (29 ulasan)", "👨‍🌾", "Penangkaran Anis Kembang mandiri, sehat, lincah, dan garansi gacor ring terdaftar."),
        Breeder("5", "Pak Hadi", "Hadi Perkutut Luhur", "Solo", "⭐ 4.4 (15 ulasan)", "👨‍🌾", "Pelestari Perkutut Lokal pilihan dengan katuranggan bagus dan suara merdu klasik.")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activeFilter = arguments?.getString("INITIAL_FILTER") ?: "Semua"
    }

    // ────────────────────────────────────────────
    // DATA DUMMY — ganti dengan API / ViewModel
    // ────────────────────────────────────────────
    private val allItems = listOf(
        BurungItem(
            id = "1", nama = "Murai Batu Medan Gacor", jenis = "Burung",
            harga = 2_500_000, lokasi = "Jakarta Selatan", kondisi = "Gacor",
            penjual = "Pak Joko", ratingPenjual = 4.9f, bgAmber = false,
            emojiGambar = "🐦", isFeatured = true, stokTersedia = true,
            deskripsi = "Murai Batu Medan, gacor isian banyak, bodi panjang, " +
                    "ekor rapi. Siap lomba. Sudah makan voer & kroto."
        ),
        BurungItem(
            id = "2", nama = "Kenari Yorkshire F2", jenis = "Burung",
            harga = 850_000, lokasi = "Bandung", kondisi = "Siap Lomba",
            penjual = "Bu Siti", ratingPenjual = 4.7f, bgAmber = true,
            emojiGambar = "🐤", isFeatured = false, stokTersedia = true,
            deskripsi = "Kenari Yorkshire F2, warna kuning solid, suara panjang, " +
                    "jinak dan sehat. Sudah vaksin ND."
        ),
        BurungItem(
            id = "3", nama = "Lovebird Dakocan Ngekek", jenis = "Burung",
            harga = 650_000, lokasi = "Surabaya", kondisi = "Ngekek Panjang",
            penjual = "Mas Andi", ratingPenjual = 4.8f, bgAmber = false,
            emojiGambar = "💚", isFeatured = false, stokTersedia = true,
            deskripsi = "LB Dakocan ngekek panjang, mental bagus, " +
                    "sudah sering ikut latber dan selalu juara kelas B."
        ),
        BurungItem(
            id = "4", nama = "Kacer Poci Betina", jenis = "Burung",
            harga = 400_000, lokasi = "Yogyakarta", kondisi = "Sehat",
            penjual = "Pak Budi", ratingPenjual = 4.5f, bgAmber = false,
            emojiGambar = "🐦", isFeatured = false, stokTersedia = false,
            deskripsi = "Kacer poci betina, lincah, makan voer, " +
                    "cocok untuk master atau ternak."
        ),
        BurungItem(
            id = "5", nama = "Cucak Hijau Full Isian", jenis = "Burung",
            harga = 1_200_000, lokasi = "Semarang", kondisi = "Full Isian",
            penjual = "Mas Rudi", ratingPenjual = 4.6f, bgAmber = false,
            emojiGambar = "🦜", isFeatured = true, stokTersedia = true,
            deskripsi = "Cucak hijau full isian, isian murai, kenari, dan ciblek. " +
                    "Mental besi, sudah juara di beberapa event regional."
        ),
        BurungItem(
            id = "6", nama = "Perkutut Lokal Manggung", jenis = "Burung",
            harga = 300_000, lokasi = "Solo", kondisi = "Manggung",
            penjual = "Pak Hadi", ratingPenjual = 4.4f, bgAmber = true,
            emojiGambar = "🕊️", isFeatured = false, stokTersedia = true,
            deskripsi = "Perkutut lokal, sudah manggung rutin. " +
                    "Suara merdu dan nyaring. Harga nego."
        ),
        BurungItem(
            id = "7", nama = "Sangkar Bulat Minimalis", jenis = "Kandang",
            harga = 150_000, lokasi = "Bekasi", kondisi = "Baru",
            penjual = "Toko KicauJaya", ratingPenjual = 4.8f, bgAmber = true,
            emojiGambar = "🧰", isFeatured = false, stokTersedia = true,
            deskripsi = "Sangkar bambu bulat finishing halus, ukuran 40cm, " +
                    "cocok untuk lovebird dan kenari."
        ),
        BurungItem(
            id = "8", nama = "Anis Kembang Siap Gacor", jenis = "Burung",
            harga = 750_000, lokasi = "Malang", kondisi = "Gacor",
            penjual = "Bang Deni", ratingPenjual = 4.7f, bgAmber = false,
            emojiGambar = "🐦", isFeatured = false, stokTersedia = true,
            deskripsi = "Anis kembang jantan dewasa, gacor isian lengkap, " +
                    "bodi padat, ekor panjang. Bisa nego tipis."
        ),
        BurungItem(
            id = "9", nama = "Sangkar Jati Premium", jenis = "Kandang",
            harga = 450_000, lokasi = "Bandung", kondisi = "Baru",
            penjual = "Toko Kayu Mas", ratingPenjual = 4.8f, bgAmber = true,
            emojiGambar = "🏠", isFeatured = false, stokTersedia = true,
            deskripsi = "Sangkar kayu jati ukir, finishing halus"
        ),
        BurungItem(
            id = "10", nama = "Jangkrik Kering 1kg", jenis = "Pakan",
            harga = 45_000, lokasi = "Online", kondisi = "Baru",
            penjual = "SupplyBurung", ratingPenjual = 4.6f, bgAmber = false,
            emojiGambar = "🌾", isFeatured = false, stokTersedia = true,
            deskripsi = "Jangkrik kering kualitas premium"
        ),
        BurungItem(
            id = "11", nama = "Vitamin Burung Kicau", jenis = "Perlengkapan",
            harga = 35_000, lokasi = "Online", kondisi = "Baru",
            penjual = "PetShop Sehat", ratingPenjual = 4.5f, bgAmber = true,
            emojiGambar = "💊", isFeatured = false, stokTersedia = true,
            deskripsi = "Suplemen vitamin lengkap untuk burung kicau"
        ),
        BurungItem(
            id = "12", nama = "Voer Breder Premium 1kg", jenis = "Pakan",
            harga = 28_000, lokasi = "Online", kondisi = "Baru",
            penjual = "NutriKicau", ratingPenjual = 4.7f, bgAmber = false,
            emojiGambar = "🌿", isFeatured = false, stokTersedia = true,
            deskripsi = "Voer premium tinggi protein, untuk semua jenis burung kicau"
        ),
        BurungItem(
            id = "13", nama = "Tempat Minum Otomatis", jenis = "Perlengkapan",
            harga = 22_000, lokasi = "Jakarta", kondisi = "Baru",
            penjual = "AksesoriKicau", ratingPenjual = 4.3f, bgAmber = true,
            emojiGambar = "💧", isFeatured = false, stokTersedia = true,
            deskripsi = "Tempat minum anti tumpah dengan kapasitas 250ml"
        ),
    )
    // ────────────────────────────────────────────

    // Filter kategori yang dianggap "Burung"
    private val kategoriPeternak = setOf("Peternak")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_marketplace, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvMarket = view.findViewById(R.id.rvMarketplace)
        tvJumlah = view.findViewById(R.id.tvJumlahHasil)
        etSearch  = view.findViewById(R.id.etSearch)

        // Setup RecyclerView Grid 2 kolom
        adapter = BurungMarketAdapter(allItems) { item ->
            keranjangItems.add(item)
            Toast.makeText(requireContext(), "✅ ${item.nama} ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
        }
        rvMarket.layoutManager = GridLayoutManager(requireContext(), 2)
        rvMarket.adapter = adapter

        setupFilterChips(view)
        setupSearch()
        setupSort(view)
        setupTopbarActions(view)
        setupFab(view)

        // Apply initial filter from arguments/bundle
        applyFilters(etSearch.text.toString())
    }

    // ── Topbar Action (notif + keranjang) ────────
    private fun setupTopbarActions(view: View) {
        val ivKeranjang = view.findViewById<TextView>(R.id.ivKeranjangMarket)
        ivKeranjang.setOnClickListener {
            try {
                val intent = Intent(requireContext(), Class.forName("com.app.foodorder.marketlit.KeranjangActivity"))
                startActivity(intent)
            } catch (e: ClassNotFoundException) {
                Toast.makeText(requireContext(), "Fitur keranjang segera hadir!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // ── FAB Jual ─────────────────────────────────
    private fun setupFab(view: View) {
        val fabJual = view.findViewById<LinearLayout>(R.id.fabJual)
        fabJual.setOnClickListener {
            (activity as? com.app.foodorder.marketlit.MainActivity)?.loadFragment(
                com.app.foodorder.marketlit.JualFragment()
            )
        }
    }

    // ── Filter Chip ──────────────────────────────
    private fun setupFilterChips(view: View) {
        val chips = mapOf(
            "Semua"        to view.findViewById<TextView>(R.id.chipSemuaMkt),
            "Burung"       to view.findViewById<TextView>(R.id.chipBurung),
            "Perlengkapan" to view.findViewById<TextView>(R.id.chipPerlengkapan),
            "Pakan"        to view.findViewById<TextView>(R.id.chipPakan),
            "Kandang"      to view.findViewById<TextView>(R.id.chipKandang),
            "Peternak"     to view.findViewById<TextView>(R.id.chipPeternak),
        )

        chips.forEach { (label, chip) ->
            chip.setOnClickListener {
                activeFilter = label
                updateChipStyle(chips, label)
                applyFilters(etSearch.text.toString())
            }
        }
        // Set initial active state
        updateChipStyle(chips, activeFilter)
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
            tvSort.text = if (sortAscending) "⇅ Harga ↑" else "⇅ Harga ↓"
            applyFilters(etSearch.text.toString())
        }
    }

    // ── Apply filter + search + sort ─────────────
    private fun applyFilters(query: String) {
        if (activeFilter == "Peternak") {
            var filteredBreeders = breedersList
            if (query.isNotBlank()) {
                val q = query.lowercase()
                filteredBreeders = breedersList.filter {
                    it.name.lowercase().contains(q) ||
                            it.farmName.lowercase().contains(q) ||
                            it.location.lowercase().contains(q)
                }
            }
            
            (rvMarket.layoutManager as? GridLayoutManager)?.spanCount = 1
            
            val peternakAdapter = PeternakAdapter(filteredBreeders,
                onChatClick = { breeder ->
                    try {
                        val intentChat = Intent(requireContext(), Class.forName("com.app.foodorder.marketlit.ChatDokterActivity")).apply {
                            putExtra("DOKTER_NAMA", breeder.name)
                            putExtra("DOKTER_SPESIALIS", breeder.farmName)
                            putExtra("DOKTER_STATUS", "ONLINE")
                            putExtra("DOKTER_EMOJI", breeder.emoji)
                            putExtra("CHAT_TYPE", "PETERNAK")
                        }
                        startActivity(intentChat)
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Gagal chat peternak.", Toast.LENGTH_SHORT).show()
                    }
                },
                onItemClick = { breeder ->
                    val intent = Intent(requireContext(), DetailPeternakActivity::class.java).apply {
                        putExtra("EXTRA_BREEDER", breeder)
                    }
                    startActivity(intent)
                }
            )
            rvMarket.adapter = peternakAdapter
            tvJumlah.text = "${filteredBreeders.size} peternak tersedia"
        } else {
            (rvMarket.layoutManager as? GridLayoutManager)?.spanCount = 2
            
            var result = if (activeFilter == "Semua") {
                allItems
            } else {
                allItems.filter { it.jenis == activeFilter }
            }
            
            if (query.isNotBlank()) {
                val q = query.lowercase()
                result = result.filter {
                    it.nama.lowercase().contains(q) ||
                            it.jenis.lowercase().contains(q) ||
                            it.lokasi.lowercase().contains(q) ||
                            it.penjual.lowercase().contains(q)
                }
            }
            
            result = if (sortAscending) result.sortedBy { it.harga }
            else result.sortedByDescending { it.harga }
            
            rvMarket.adapter = adapter
            adapter.updateData(result)
            tvJumlah.text = "${result.size} produk tersedia"
        }
    }
}