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
import com.app.foodorder.marketlit.db.MarketLitRepository

class LombaFragment : Fragment() {

    private lateinit var adapter: LombaAdapter
    private lateinit var rvLomba: RecyclerView
    private lateinit var repository: MarketLitRepository

    private lateinit var chipSemua: TextView
    private lateinit var chipDibuka: TextView
    private lateinit var chipSegera: TextView
    private lateinit var chipSelesai: TextView

    private var allLomba: List<Lomba> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lomba, container, false)

        repository = MarketLitRepository(requireContext())
        allLomba = repository.getAllLomba()

        rvLomba = view.findViewById(R.id.rvLomba)
        rvLomba.layoutManager = LinearLayoutManager(context)

        chipSemua   = view.findViewById(R.id.chipSemua)
        chipDibuka  = view.findViewById(R.id.chipDibuka)
        chipSegera  = view.findViewById(R.id.chipSegera)
        chipSelesai = view.findViewById(R.id.chipSelesai)

        adapter = LombaAdapter(allLomba) { lomba -> }
        rvLomba.adapter = adapter

        setupChipListeners()
        setActiveChip(chipSemua)

        return view
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
