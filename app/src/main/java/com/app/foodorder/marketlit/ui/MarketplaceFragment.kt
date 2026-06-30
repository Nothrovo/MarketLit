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
import android.widget.ProgressBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.foodorder.marketlit.RetrofitClient
import com.app.foodorder.marketlit.R
import com.app.foodorder.marketlit.adapter.BurungMarketAdapter
import com.app.foodorder.marketlit.model.BurungItem
import com.app.foodorder.marketlit.model.Breeder
import com.app.foodorder.marketlit.adapter.PeternakAdapter
import com.app.foodorder.marketlit.DetailPeternakActivity
import com.app.foodorder.marketlit.db.MarketLitRepository

class MarketplaceFragment : Fragment() {

    companion object {
        val keranjangItems = mutableListOf<BurungItem>()
        val terjualApiIds = mutableSetOf<String>()

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
    private lateinit var progressBar: ProgressBar
    private lateinit var tvJumlah: TextView
    private lateinit var etSearch: EditText
    private lateinit var repository: MarketLitRepository

    private var activeFilter = "Semua"
    private var sqliteItems: List<BurungItem> = emptyList()
    private var apiItems: List<BurungItem> = emptyList()
    private val allItems: List<BurungItem>
        get() = sqliteItems + apiItems
    private var breedersList: List<Breeder> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activeFilter = arguments?.getString("INITIAL_FILTER") ?: "Semua"
        repository = MarketLitRepository(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_marketplace, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvMarket = view.findViewById(R.id.rvMarketplace)
        tvJumlah = view.findViewById(R.id.tvJumlahHasil)
        etSearch = view.findViewById(R.id.etSearch)

        progressBar = view.findViewById(R.id.progressBar)

        sqliteItems = repository.getAllBurungItems()
        breedersList = repository.getAllBreeders()

        adapter = BurungMarketAdapter(allItems) { item ->
            keranjangItems.add(item)
            Toast.makeText(requireContext(), "${item.nama} ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
        }
        rvMarket.layoutManager = GridLayoutManager(requireContext(), 2)
        rvMarket.adapter = adapter

        setupFilterChips(view)
        setupSearch()
        setupSort(view)
        setupTopbarActions(view)
        setupFab(view)
        fetchBurungFromApi()
        applyFilters(etSearch.text.toString())
    }

    override fun onResume() {
        super.onResume()
        if (::repository.isInitialized && ::etSearch.isInitialized) {
            sqliteItems = repository.getAllBurungItems()
            applyFilters(etSearch.text.toString())
        }
    }

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

    private fun setupFab(view: View) {
        val fabJual = view.findViewById<LinearLayout>(R.id.fabJual)
        fabJual.setOnClickListener {
            (activity as? com.app.foodorder.marketlit.MainActivity)?.loadFragment(
                com.app.foodorder.marketlit.JualFragment()
            )
        }
    }

    private fun fetchBurungFromApi() {
        progressBar.visibility = View.VISIBLE

        RetrofitClient.instance.getBurungList().enqueue(object : Callback<List<BurungItem>> {
            override fun onResponse(call: Call<List<BurungItem>>, response: Response<List<BurungItem>>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful && response.body() != null) {
                    apiItems = response.body()!!.map { it.copy(id = "api-${it.id}") }
                    applyFilters(etSearch.text.toString())
                } else {
                    Toast.makeText(requireContext(), "Gagal memuat data burung", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<BurungItem>>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show()
            }
        })
    }

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

    private fun setupSearch() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, st: Int, c: Int, a: Int) {}
            override fun onTextChanged(s: CharSequence?, st: Int, b: Int, c: Int) {
                applyFilters(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private var sortAscending = true

    private fun setupSort(view: View) {
        val btnSort = view.findViewById<LinearLayout>(R.id.btnSort)
        val tvSort = view.findViewById<TextView>(R.id.tvSortLabel)
        btnSort.setOnClickListener {
            sortAscending = !sortAscending
            tvSort.text = if (sortAscending) "⇅ Harga ↑" else "⇅ Harga ↓"
            applyFilters(etSearch.text.toString())
        }
    }

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
                            putExtra("RECEIVER_USER_ID", breeder.userId)
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
                allItems.filter { it.stokTersedia && it.id !in terjualApiIds }
            } else {
                allItems.filter { it.jenis == activeFilter && it.stokTersedia && it.id !in terjualApiIds }
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
