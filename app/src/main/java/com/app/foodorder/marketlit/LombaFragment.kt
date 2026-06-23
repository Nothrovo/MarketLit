package com.app.foodorder.marketlit

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.ProgressBar
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class LombaFragment : Fragment() {

    private lateinit var adapter: LombaAdapter
    private lateinit var rvLomba: RecyclerView

    private lateinit var chipSemua: TextView
    private lateinit var chipDibuka: TextView
    private lateinit var chipSegera: TextView
    private lateinit var chipSelesai: TextView
    private lateinit var progressBar: ProgressBar

    private var allLomba: List<Lomba> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lomba, container, false)

        rvLomba = view.findViewById(R.id.rvLomba)
        progressBar = view.findViewById(R.id.progressBar)
        rvLomba.layoutManager = LinearLayoutManager(context)

        chipSemua   = view.findViewById(R.id.chipSemua)
        chipDibuka  = view.findViewById(R.id.chipDibuka)
        chipSegera  = view.findViewById(R.id.chipSegera)
        chipSelesai = view.findViewById(R.id.chipSelesai)

        adapter = LombaAdapter(allLomba) { lomba ->
            // onDaftarClick callback — Toast ditampilkan di dalam adapter
        }
        rvLomba.adapter = adapter

        setupChipListeners()
        setActiveChip(chipSemua)
        fetchLombaFromApi()

        return view
    }

    private fun fetchLombaFromApi() {
        progressBar.visibility = View.VISIBLE

        RetrofitClient.instance.getLatestLomba().enqueue(object : Callback<List<Lomba>> {
            override fun onResponse(call: Call<List<Lomba>>, response: Response<List<Lomba>>) {
                progressBar.visibility = View.GONE
                android.util.Log.d("LombaAPI", "Code: ${response.code()}, Body: ${response.body()}")
                if (response.isSuccessful && response.body() != null) {
                    allLomba = response.body()!!
                    android.util.Log.d("LombaAPI", "Jumlah data: ${allLomba.size}")
                    adapter.updateData(allLomba)
                }
            }

            override fun onFailure(call: Call<List<Lomba>>, t: Throwable) {
                progressBar.visibility = View.GONE
                android.util.Log.e("LombaAPI", "Error: ${t.message}")
                Toast.makeText(requireContext(), "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupChipListeners() {
        chipSemua.setOnClickListener {
            setActiveChip(chipSemua)
            adapter.updateData(allLomba)
        }
        chipDibuka.setOnClickListener {
            setActiveChip(chipDibuka)
            adapter.updateData(allLomba.filter { it.status == "Dibuka" })
        }
        chipSegera.setOnClickListener {
            setActiveChip(chipSegera)
            adapter.updateData(allLomba.filter { it.status == "Segera" })
        }
        chipSelesai.setOnClickListener {
            setActiveChip(chipSelesai)
            adapter.updateData(allLomba.filter { it.status == "Selesai" })
        }
    }

    private fun setActiveChip(activeChip: TextView) {
        val chips = listOf(chipSemua, chipDibuka, chipSegera, chipSelesai)
        chips.forEach { chip ->
            if (chip == activeChip) {
                chip.setBackgroundResource(R.drawable.bg_chip_active)
                chip.setTextColor(Color.WHITE)
            } else {
                chip.setBackgroundResource(R.drawable.bg_chip_inactive)
                chip.setTextColor(androidx.core.content.ContextCompat.getColor(requireContext(), R.color.text_mid))
            }
        }
    }
}