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
import com.app.foodorder.marketlit.model.BurungItem
import com.app.foodorder.marketlit.ui.MarketplaceFragment

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ── Kategori chip ──────────────────────────────────────────────────────
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

        // ── Banner: Daftar Sekarang ────────────────────────────────────────────
        val btnDaftarBanner = view.findViewById<TextView>(R.id.btnDaftarBanner)
        btnDaftarBanner.setOnClickListener { navigateToFragment(LombaFragment()) }

        // ── Lihat Semua ────────────────────────────────────────────────────────
        val tvLihatSemua = view.findViewById<TextView>(R.id.tvLihatSemua)
        tvLihatSemua.setOnClickListener { navigateToFragment(MarketplaceFragment()) }

        // ── Ikon keranjang ─────────────────────────────────────────────────────
        val ivKeranjang = view.findViewById<TextView>(R.id.ivKeranjang)
        ivKeranjang.setOnClickListener {
            try {
                val intent = Intent(requireContext(), KeranjangActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                // KeranjangActivity belum tersedia; abaikan sementara
            }
        }

        // ── RecyclerView Burung Pilihan ─────────────────────────────────────────
        val rvBurungPilihan = view.findViewById<RecyclerView>(R.id.rvBurungPilihan)
        rvBurungPilihan.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val dummyBurung = listOf(
            BurungItem(
                id            = "1",
                nama          = "Murai Batu Medan",
                jenis         = "Murai Batu",
                harga         = 3_500_000L,
                lokasi        = "Medan, Sumut",
                kondisi       = "Gacor",
                penjual       = "Pak Budi",
                ratingPenjual = 4.9f,
                deskripsi     = "Murai batu kelas lomba asal Medan",
                emojiGambar   = "🐦",
                bgAmber       = false,
                stokTersedia  = true
            ),
            BurungItem(
                id            = "2",
                nama          = "Kenari F1",
                jenis         = "Kenari",
                harga         = 850_000L,
                lokasi        = "Bandung, Jabar",
                kondisi       = "Sehat",
                penjual       = "Bu Sari",
                ratingPenjual = 4.7f,
                deskripsi     = "Kenari F1 isian merdu",
                emojiGambar   = "🐤",
                bgAmber       = true,
                stokTersedia  = true
            ),
            BurungItem(
                id            = "3",
                nama          = "Kacer Lokal",
                jenis         = "Kacer",
                harga         = 650_000L,
                lokasi        = "Surabaya, Jatim",
                kondisi       = "Siap Lomba",
                penjual       = "Mas Joko",
                ratingPenjual = 4.5f,
                deskripsi     = "Kacer fighter siap turun ring",
                emojiGambar   = "🦅",
                bgAmber       = false,
                stokTersedia  = true
            ),
            BurungItem(
                id            = "4",
                nama          = "Cucak Rowo",
                jenis         = "Cucak Rowo",
                harga         = 1_200_000L,
                lokasi        = "Jogja, DIY",
                kondisi       = "Gacor",
                penjual       = "Pak Hendra",
                ratingPenjual = 4.8f,
                deskripsi     = "Cucak Rowo dengan isian rowo asli",
                emojiGambar   = "🦜",
                bgAmber       = true,
                stokTersedia  = true
            )
        )

        rvBurungPilihan.adapter = BurungMarketAdapter(dummyBurung)

        // ── Pengumuman click listeners ─────────────────────────────────────────
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

    /** Helper: navigasi ke Fragment melalui MainActivity.loadFragment() */
    private fun navigateToFragment(fragment: Fragment) {
        (activity as? MainActivity)?.loadFragment(fragment)
    }
}