package com.app.foodorder.marketlit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cari ID menu yang tadi kita tambahkan di XML
        val menuPasar = view.findViewById<LinearLayout>(R.id.menuPasar)
        val menuLomba = view.findViewById<LinearLayout>(R.id.menuLomba)
        val menuDokter = view.findViewById<LinearLayout>(R.id.menuDokter)

        // Kasih aksi pas ditekan
        menuPasar.setOnClickListener {
            Toast.makeText(requireContext(), "Menu Pasar dipencet!", Toast.LENGTH_SHORT).show()
            // Logika pindah tab/activity nanti bisa ditaruh sini
        }

        menuLomba.setOnClickListener {
            Toast.makeText(requireContext(), "Menu Lomba dipencet!", Toast.LENGTH_SHORT).show()
        }

        menuDokter.setOnClickListener {
            Toast.makeText(requireContext(), "Menu Dokter dipencet!", Toast.LENGTH_SHORT).show()
        }

        // Buat rvRekomendasi (RecyclerView) biarin dulu,
        // nanti lu butuh Adapter khusus kalau mau diisi gambar-gambar burung/lomba.
    }
}