package com.app.foodorder.marketlit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class DashboardFragment : Fragment() {

    // 1. Fungsi ini untuk memanggil layout fragment_dashboard.xml
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout untuk fragment ini
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    // 2. Fungsi ini tempat lo naruh logika (klik tombol, ambil data, dll)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Contoh kalau nanti lo mau bikin klik di foto profil:
        // val profileCard = view.findViewById<View>(R.id.profile_card)
        // profileCard.setOnClickListener {
        //     // Aksi saat foto profil diklik
        // }
    }
}