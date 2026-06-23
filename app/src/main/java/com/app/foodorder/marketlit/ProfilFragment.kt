package com.app.foodorder.marketlit.profil

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.foodorder.marketlit.LandingActivity
import com.app.foodorder.marketlit.PengaturanActivity
import com.app.foodorder.marketlit.R
import com.app.foodorder.marketlit.RiwayatActivity

class ProfilFragment : Fragment() {

    // ==========================================
    // Daftar menu profil — tambah/kurangi di sini
    // ==========================================
    private val menuList = listOf(
        MenuProfilItem(id = "iklan",      icon = "📋", title = "Iklan Saya"),
        MenuProfilItem(id = "lomba",      icon = "🏆", title = "Riwayat Lomba"),
        MenuProfilItem(id = "pengaturan", icon = "⚙️", title = "Pengaturan Akun"),
        MenuProfilItem(id = "logout",     icon = "🚪", title = "Keluar / Log Out")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_profil, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUserName(view)
        setupMenuRecyclerView(view)
    }

    override fun onResume() {
        super.onResume()
        view?.let { loadUserName(it) }
    }

    private fun loadUserName(view: View) {
        val prefs: SharedPreferences = requireContext()
            .getSharedPreferences("USER_PROFILE", android.content.Context.MODE_PRIVATE)
        val nama = prefs.getString("USER_NAME", "Pengguna MarketLit") ?: "Pengguna MarketLit"
        view.findViewById<TextView>(R.id.tvNamaPengguna)?.text = nama
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
                val intent = Intent(requireContext(), RiwayatActivity::class.java)
                intent.putExtra("TAB", "transaksi")
                startActivity(intent)
            }
            "lomba" -> {
                val intent = Intent(requireContext(), RiwayatActivity::class.java)
                intent.putExtra("TAB", "lomba")
                startActivity(intent)
            }
            "pengaturan" -> {
                val intent = Intent(requireContext(), PengaturanActivity::class.java)
                startActivity(intent)
            }
            "logout" -> {
                androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Log Out")
                    .setMessage("Apakah Anda yakin ingin keluar?")
                    .setPositiveButton("Ya") { _, _ ->
                        val intent = Intent(requireContext(), LandingActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        activity?.finish()
                    }
                    .setNegativeButton("Batal", null)
                    .show()
            }
        }
    }

    companion object {
        fun newInstance() = ProfilFragment()
    }
}