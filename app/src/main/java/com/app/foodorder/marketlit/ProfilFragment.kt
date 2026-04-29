package com.app.foodorder.marketlit.profil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.foodorder.marketlit.R

class ProfilFragment : Fragment() {

    // ==========================================
    // Daftar menu profil — tambah/kurangi di sini
    // ==========================================
    private val menuList = listOf(
        MenuProfilItem(id = "iklan",      icon = "📋", title = "Iklan Saya"),
        MenuProfilItem(id = "lomba",      icon = "🏆", title = "Riwayat Lomba"),
        MenuProfilItem(id = "pengaturan", icon = "⚙️", title = "Pengaturan Akun")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_profil, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenuRecyclerView(view)
    }

    private fun setupMenuRecyclerView(view: View) {
        val rvMenu = view.findViewById<RecyclerView>(R.id.rvMenuProfil)

        val adapter = MenuProfilAdapter(menuList) { item ->
            handleMenuClick(item)
        }

        rvMenu.layoutManager = LinearLayoutManager(requireContext())
        rvMenu.adapter = adapter

        // Divider antar item — alternatif dari viewDivider manual
        rvMenu.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )
    }

    // ==========================================
    // Handler klik — mudah dikembangkan
    // ==========================================
    private fun handleMenuClick(item: MenuProfilItem) {
        when (item.id) {
            "iklan" -> {
                Toast.makeText(requireContext(), "Membuka Iklan Saya...", Toast.LENGTH_SHORT).show()
                // TODO: navigasi ke IklanSayaFragment atau Activity
            }
            "lomba" -> {
                Toast.makeText(requireContext(), "Membuka Riwayat Lomba...", Toast.LENGTH_SHORT).show()
                // TODO: navigasi ke RiwayatLombaFragment
                // parentFragmentManager.commit {
                //     replace(R.id.fragmentContainer, RiwayatLombaFragment())
                //     addToBackStack(null)
                // }
            }
            "pengaturan" -> {
                Toast.makeText(requireContext(), "Membuka Pengaturan Akun...", Toast.LENGTH_SHORT).show()
                // TODO: navigasi ke PengaturanFragment
            }
        }
    }

    companion object {
        fun newInstance() = ProfilFragment()
    }
}