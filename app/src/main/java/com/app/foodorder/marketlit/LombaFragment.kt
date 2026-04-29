package com.app.foodorder.marketlit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LombaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lomba, container, false)

        val rvLomba: RecyclerView = view.findViewById(R.id.rvLomba)
        rvLomba.layoutManager = LinearLayoutManager(context) // Susun ke bawah (vertikal)

        // Dummy Data Lomba berdasarkan desain HTML lo
        val listLomba = listOf(
            Lomba(1, "🏆 Kicau Mania Cup 2025", "📅 20 Mei 2025 · Surabaya", "🐦 Murai Batu, Kacer", "Dibuka", "Rp 50.000.000", "Lomba bergengsi tahunan untuk para Kicau Mania. Siapkan burung jagoanmu!"),
            Lomba(2, "🎵 Festival Kicau Nusantara", "📅 15 Juni 2025 · Jakarta", "🐦 Semua kategori", "Dibuka", "Rp 80.000.000", "Festival kicau terbesar di Nusantara dengan juri profesional dan hadiah fantastis."),
            Lomba(3, "🏅 Latber Spesial Minggu", "📅 10 Mei 2025 · Bandung", "🐦 Kenari, Lovebird", "Segera", "Rp 5.000.000", "Latihan bersama spesial hari minggu untuk melatih mental tanding burung.")
        )

        rvLomba.adapter = LombaAdapter(listLomba)
        return view
    }
}