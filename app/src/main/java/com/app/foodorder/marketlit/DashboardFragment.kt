package com.app.foodorder.marketlit

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.foodorder.marketlit.adapter.BurungMarketAdapter
import com.app.foodorder.marketlit.db.MarketLitRepository
import com.app.foodorder.marketlit.ui.MarketplaceFragment

class DashboardFragment : Fragment() {

    private lateinit var repository: MarketLitRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = MarketLitRepository(requireContext())

        val catJualBurung    = view.findViewById<LinearLayout>(R.id.catJualBurung)
        val catLomba         = view.findViewById<LinearLayout>(R.id.catLomba)
        val catDokter        = view.findViewById<LinearLayout>(R.id.catDokter)
        val catPerlengkapan  = view.findViewById<LinearLayout>(R.id.catPerlengkapan)
        val catPeternak      = view.findViewById<LinearLayout>(R.id.catPeternak)

        catJualBurung.setOnClickListener   { navigateToFragment(MarketplaceFragment.newInstance("Semua")) }
        catLomba.setOnClickListener        { navigateToFragment(LombaFragment()) }
        catDokter.setOnClickListener       { navigateToFragment(DokterFragment()) }
        catPerlengkapan.setOnClickListener { navigateToFragment(MarketplaceFragment.newInstance("Perlengkapan")) }
        catPeternak.setOnClickListener     { navigateToFragment(MarketplaceFragment.newInstance("Peternak")) }

        val btnDaftarBanner = view.findViewById<TextView>(R.id.btnDaftarBanner)
        btnDaftarBanner.setOnClickListener { navigateToFragment(LombaFragment()) }

        val tvLihatSemua = view.findViewById<TextView>(R.id.tvLihatSemua)
        tvLihatSemua.setOnClickListener { navigateToFragment(MarketplaceFragment()) }

        val ivKeranjang = view.findViewById<TextView>(R.id.ivKeranjang)
        ivKeranjang.setOnClickListener {
            try {
                val intent = Intent(requireContext(), KeranjangActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) { }
        }

        val rvBurungPilihan = view.findViewById<RecyclerView>(R.id.rvBurungPilihan)
        rvBurungPilihan.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val featuredItems = repository.getFeaturedItems()
        rvBurungPilihan.adapter = BurungMarketAdapter(featuredItems)

        val layoutAnnounce1 = view.findViewById<View>(R.id.layoutAnnounce1)
        val layoutAnnounce2 = view.findViewById<View>(R.id.layoutAnnounce2)
        val layoutAnnounce3 = view.findViewById<View>(R.id.layoutAnnounce3)

        layoutAnnounce1.setOnClickListener {
            showAnnouncementDialog(
                "Lomba Regional Jawa Timur",
                "Pendaftaran untuk Lomba Regional Jawa Timur resmi dibuka mulai tanggal 1 hingga 30 Juli 2025.\n\nKategori Lomba:\n- Murai Batu (Eksekutif)\n- Kacer (Bintang)\n- Kenari (Favorit)\n\nDapatkan kesempatan memperebutkan piala bergengsi dan hadiah pembinaan total puluhan juta rupiah. Hubungi panitia lokal melalui menu Dokter/Vet untuk konsultasi kesehatan burung sebelum lomba."
            )
        }

        layoutAnnounce2.setOnClickListener {
            showAnnouncementDialog(
                "Konsultasi Drh. Gratis Sabtu Ini",
                "Layanan Kesehatan MarketLit:\n\nSabtu ini, kami mengadakan program Konsultasi Dokter Hewan Gratis khusus bagi 20 pengguna pertama yang mendaftar melalui menu Dokter.\n\nSyarat & Ketentuan:\n- Burung peliharaan sendiri\n- Menyertakan keluhan kesehatan secara mendetail saat berkonsultasi\n\nSegera jadwalkan visit atau hubungi dokter hewan online di aplikasi!"
            )
        }

        layoutAnnounce3.setOnClickListener {
            showAnnouncementDialog(
                "Fitur Chat Penjual Tersedia!",
                "Kabar gembira untuk Kicau Mania!\n\nSekarang Anda dapat melakukan negosiasi harga dan menanyakan detail kondisi burung atau perlengkapan secara langsung melalui fitur Chat Penjual yang baru.\n\nNikmati kemudahan transaksi yang aman, cepat, dan transparan langsung dari genggaman Anda. Update aplikasi secara berkala untuk performa terbaik."
            )
        }
    }

    private fun showAnnouncementDialog(title: String, message: String) {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Tutup", null)
            .show()
    }

    private fun navigateToFragment(fragment: Fragment) {
        (activity as? MainActivity)?.loadFragment(fragment)
    }
}
