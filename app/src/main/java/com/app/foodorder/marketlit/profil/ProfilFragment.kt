package com.app.foodorder.marketlit.profil

import android.content.Intent
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
import com.app.foodorder.marketlit.db.MarketLitRepository

class ProfilFragment : Fragment() {

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
        val sessionPrefs = requireContext()
            .getSharedPreferences("USER_SESSION", android.content.Context.MODE_PRIVATE)
        val userId = sessionPrefs.getInt("USER_ID", 0)

        var nama = "Pengguna MarketLit"
        if (userId > 0) {
            val repository = MarketLitRepository(requireContext())
            val user = repository.getUserById(userId)
            if (user != null) {
                nama = user.nama
            }
        } else {
            val prefs = requireContext()
                .getSharedPreferences("USER_PROFILE", android.content.Context.MODE_PRIVATE)
            nama = prefs.getString("USER_NAME", nama) ?: nama
        }
        view.findViewById<TextView>(R.id.tvNamaPengguna)?.text = nama
    }

    private fun setupMenuRecyclerView(view: View) {
        val rvMenu = view.findViewById<RecyclerView>(R.id.rvMenuProfil)

        val adapter = MenuProfilAdapter(menuList) { item ->
            handleMenuClick(item)
        }

        rvMenu.layoutManager = LinearLayoutManager(requireContext())
        rvMenu.adapter = adapter

        rvMenu.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )
    }

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
                        requireContext().getSharedPreferences("USER_SESSION", android.content.Context.MODE_PRIVATE)
                            .edit()
                            .putBoolean("isLoggedIn", false)
                            .remove("USER_ID")
                            .apply()

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
