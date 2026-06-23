// JadwalDokterActivity.kt
package com.app.foodorder.marketlit

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.app.foodorder.marketlit.databinding.ActivityJadwalDokterBinding

class JadwalDokterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJadwalDokterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJadwalDokterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ── Ambil data dokter dari Intent ─────────────────────────────────────
        val dokterNama      = intent.getStringExtra("DOKTER_NAMA")      ?: "Dokter"
        val dokterSpesialis = intent.getStringExtra("DOKTER_SPESIALIS") ?: ""
        val dokterEmoji     = intent.getStringExtra("DOKTER_EMOJI")      ?: "👨‍⚕️"
        val dokterRating    = intent.getDoubleExtra("DOKTER_RATING", 4.9)

        setupHeader(dokterNama)
        setupDokterInfo(dokterNama, dokterSpesialis, dokterEmoji, dokterRating)
        setupJadwalkanVisit(dokterNama)
    }

    // ── Topbar header ─────────────────────────────────────────────────────────
    private fun setupHeader(nama: String) {
        binding.tvNamaDokterJadwal.text = nama
        binding.btnBackJadwal.setOnClickListener { finish() }
    }

    // ── Info dokter di card ───────────────────────────────────────────────────
    private fun setupDokterInfo(nama: String, spesialis: String, emoji: String, rating: Double) {
        binding.tvAvatarJadwal.text      = emoji
        binding.tvNamaInfoJadwal.text    = nama
        binding.tvSpesialisJadwal.text   = spesialis
        binding.tvRatingJadwal.text      = buildRatingStars(rating) + " $rating"
    }

    // ── Tombol Jadwalkan Visit ────────────────────────────────────────────────
    private fun setupJadwalkanVisit(dokterNama: String) {
        binding.btnJadwalkanVisit.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi Jadwal")
                .setMessage("Apakah kamu yakin ingin menjadwalkan konsultasi dengan $dokterNama?")
                .setPositiveButton("Ya, Jadwalkan") { dialog, _ ->
                    dialog.dismiss()
                    Toast.makeText(
                        this,
                        "Jadwal berhasil dibuat! Tim kami akan menghubungi kamu.",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    // ── Helper rating stars ───────────────────────────────────────────────────
    private fun buildRatingStars(rating: Double): String {
        val full  = rating.toInt()
        val empty = 5 - full
        return "★".repeat(full) + "☆".repeat(empty)
    }
}
