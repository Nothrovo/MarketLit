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

class LombaFragment : Fragment() {

    private lateinit var adapter: LombaAdapter
    private lateinit var rvLomba: RecyclerView

    private lateinit var chipSemua: TextView
    private lateinit var chipDibuka: TextView
    private lateinit var chipSegera: TextView
    private lateinit var chipSelesai: TextView

    private val allLomba = listOf(
        Lomba(
            1, "🏆 Kicau Mania Cup 2025",
            "📅 20 Mei 2025 · Surabaya",
            "🐦 Murai Batu, Kacer",
            "Dibuka",
            "Rp 50.000.000",
            "Lomba bergengsi tahunan untuk para Kicau Mania. Siapkan burung jagoanmu! Hadiah total mencapai 50 juta rupiah dengan kategori Murai Batu dan Kacer kelas dunia."
        ),
        Lomba(
            2, "🎵 Festival Kicau Nusantara",
            "📅 15 Juni 2025 · Jakarta",
            "🐦 Semua kategori",
            "Dibuka",
            "Rp 80.000.000",
            "Festival kicau terbesar di Nusantara dengan juri profesional dan hadiah fantastis. Terbuka untuk semua jenis burung kicau dengan total hadiah 80 juta rupiah."
        ),
        Lomba(
            3, "🏅 Latber Spesial Minggu",
            "📅 10 Mei 2025 · Bandung",
            "🐦 Kenari, Lovebird",
            "Segera",
            "Rp 5.000.000",
            "Latihan bersama spesial hari minggu untuk melatih mental tanding burung. Cocok untuk pemula maupun senior. Pendaftaran ditutup 3 hari sebelum acara."
        ),
        Lomba(
            4, "🎖️ Piala Gubernur 2024",
            "📅 1 Des 2024 · Jogja",
            "🐦 Cucak Rowo",
            "Selesai",
            "Rp 100.000.000",
            "Lomba bergengsi berhadiah 100 juta rupiah yang telah berhasil diselenggarakan. Terima kasih kepada semua peserta yang telah ikut berpartisipasi dalam Piala Gubernur 2024."
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lomba, container, false)

        rvLomba = view.findViewById(R.id.rvLomba)
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