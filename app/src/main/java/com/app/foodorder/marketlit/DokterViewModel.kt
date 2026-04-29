// DokterViewModel.kt
package com.app.foodorder.marketlit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DokterViewModel : ViewModel() {

    // ── Data class dokter ─────────────────────────────────────────────────────
    data class Dokter(
        val id: String,
        val nama: String,
        val spesialis: String,
        val pengalaman: Int,        // dalam tahun
        val rating: Double,
        val jumlahUlasan: Int,
        val status: Status,
        val avatarUrl: String? = null,
        val avatarEmoji: String = "👨‍⚕️"
    )

    enum class Status { ONLINE, SIBUK, OFFLINE }

    // ── State ─────────────────────────────────────────────────────────────────
    private val _dokterList = MutableLiveData<List<Dokter>>()
    val dokterList: LiveData<List<Dokter>> = _dokterList

    private var semuaDokter: List<Dokter> = emptyList()

    init {
        loadDummyData()
    }

    // ── Filter: "Semua", "Online", "Rating ★" ────────────────────────────────
    fun filter(mode: String) {
        _dokterList.value = when (mode) {
            "Online"    -> semuaDokter.filter { it.status == Status.ONLINE }
            "Rating ★"  -> semuaDokter.sortedByDescending { it.rating }
            else        -> semuaDokter
        }
    }

    // ── Data dummy — ganti dengan API call nanti ──────────────────────────────
    private fun loadDummyData() {
        semuaDokter = listOf(
            Dokter(
                id = "d1",
                nama = "drh. Budi Santoso",
                spesialis = "Spesialis Kicauan",
                pengalaman = 10,
                rating = 4.9,
                jumlahUlasan = 128,
                status = Status.ONLINE,
                avatarEmoji = "👨‍⚕️"
            ),
            Dokter(
                id = "d2",
                nama = "drh. Sari Dewi",
                spesialis = "Penyakit & Infeksi",
                pengalaman = 7,
                rating = 4.7,
                jumlahUlasan = 94,
                status = Status.SIBUK,
                avatarEmoji = "👩‍⚕️"
            ),
            Dokter(
                id = "d3",
                nama = "drh. Agus Wibowo",
                spesialis = "Nutrisi & Pakan",
                pengalaman = 12,
                rating = 4.8,
                jumlahUlasan = 207,
                status = Status.ONLINE,
                avatarEmoji = "👨‍⚕️"
            ),
        )
        _dokterList.value = semuaDokter
    }
}