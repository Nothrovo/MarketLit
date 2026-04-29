package com.app.foodorder.marketlit

import android.content.Intent
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
        // Menghubungkan ke fragment_dashboard.xml
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ==========================================
        // BINDING ID & LOGIKA BOTTOM NAVIGATION
        // (Sesuai dengan desain navbar temen lo)
        // ==========================================

        val navHome = view.findViewById<LinearLayout>(R.id.navHome)
        val navLomba = view.findViewById<LinearLayout>(R.id.navLomba)
        val navMarket = view.findViewById<LinearLayout>(R.id.navMarket)
        val navVet = view.findViewById<LinearLayout>(R.id.navVet)
        val navProfil = view.findViewById<LinearLayout>(R.id.navProfil)

        // Klik Beranda (tetap di sini)
        navHome.setOnClickListener {
            Toast.makeText(requireContext(), "Anda di Beranda", Toast.LENGTH_SHORT).show()
        }

        // Klik Profil (Pindah ke ProfilActivity punya temen lo)
        navProfil.setOnClickListener {
            val intent = Intent(requireContext(), ProfilActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        // Klik Lomba (Jika nanti sudah ada LombaActivity)
        navLomba.setOnClickListener {
            Toast.makeText(requireContext(), "Membuka Lomba...", Toast.LENGTH_SHORT).show()
            // val intent = Intent(requireContext(), LombaActivity::class.java)
            // startActivity(intent)
        }

        // Klik Market (Jika nanti sudah ada MarketActivity)
        navMarket.setOnClickListener {
            Toast.makeText(requireContext(), "Membuka Market...", Toast.LENGTH_SHORT).show()
        }

        // Klik Dokter
        navVet.setOnClickListener {
            Toast.makeText(requireContext(), "Membuka Dokter...", Toast.LENGTH_SHORT).show()
        }
    }
}