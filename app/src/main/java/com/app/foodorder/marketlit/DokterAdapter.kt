// DokterAdapter.kt
package com.app.foodorder.marketlit

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.foodorder.marketlit.databinding.ItemDokterCardBinding

class DokterAdapter(
    private val onChatClick: (DokterViewModel.Dokter) -> Unit,
    private val onJadwalClick: (DokterViewModel.Dokter) -> Unit
) : ListAdapter<DokterViewModel.Dokter, DokterAdapter.DokterViewHolder>(DiffCallback()) {

    inner class DokterViewHolder(
        private val binding: ItemDokterCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dokter: DokterViewModel.Dokter) {
            binding.apply {
                // ── Avatar emoji ──────────────────────────────────────────────────
                tvAvatarDokter.text = dokter.avatarEmoji

                // Warna background avatar sesuai status
                val avatarBgColor = when (dokter.status) {
                    DokterViewModel.Status.ONLINE  -> Color.parseColor("#E8F5E9")
                    DokterViewModel.Status.SIBUK   -> Color.parseColor("#FFF8E1")
                    DokterViewModel.Status.OFFLINE -> Color.parseColor("#F0F0EE")
                }
                llAvatarDokter.setBackgroundColor(avatarBgColor)
                // Pertahankan bentuk bulat dari drawable dengan overlay warna
                llAvatarDokter.background = itemView.context.getDrawable(
                    R.drawable.bg_avatar_green
                )?.also { drawable ->
                    drawable.setTint(avatarBgColor)
                }

                // ── Info dasar ────────────────────────────────────────────────────
                tvNamaDokter.text      = dokter.nama
                tvSpesialisDokter.text = "${dokter.spesialis} · ${dokter.pengalaman} thn"
                tvRatingDokter.text    = buildRatingStars(dokter.rating) +
                        " ${dokter.rating} (${dokter.jumlahUlasan})"

                // ── Status badge ──────────────────────────────────────────────────
                when (dokter.status) {
                    DokterViewModel.Status.ONLINE -> {
                        tvStatusDokter.text = "● Online"
                        tvStatusDokter.setBackgroundResource(R.drawable.bg_badge_status_online)
                        tvStatusDokter.setTextColor(Color.parseColor("#1B5E20"))
                    }
                    DokterViewModel.Status.SIBUK -> {
                        tvStatusDokter.text = "Sibuk"
                        tvStatusDokter.setBackgroundResource(R.drawable.bg_badge_status_sibuk)
                        tvStatusDokter.setTextColor(Color.parseColor("#8A9590"))
                    }
                    DokterViewModel.Status.OFFLINE -> {
                        tvStatusDokter.text = "Offline"
                        tvStatusDokter.setBackgroundResource(R.drawable.bg_badge_status_sibuk)
                        tvStatusDokter.setTextColor(Color.parseColor("#8A9590"))
                    }
                }

                // ── Tombol Chat (aksi utama, selalu tampil) ───────────────────────
                btnAksiDokter.text = "Chat"
                btnAksiDokter.setOnClickListener { onChatClick(dokter) }

                // ── Tombol Jadwal (selalu tampil, outline) ────────────────────────
                btnJadwalDokter.setOnClickListener { onJadwalClick(dokter) }
            }
        }

        /** Bangun string bintang rating: 4.7 → ★★★★☆ */
        private fun buildRatingStars(rating: Double): String {
            val full  = rating.toInt()
            val empty = 5 - full
            return "★".repeat(full) + "☆".repeat(empty)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DokterViewHolder {
        val binding = ItemDokterCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DokterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DokterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<DokterViewModel.Dokter>() {
        override fun areItemsTheSame(old: DokterViewModel.Dokter, new: DokterViewModel.Dokter) =
            old.id == new.id
        override fun areContentsTheSame(old: DokterViewModel.Dokter, new: DokterViewModel.Dokter) =
            old == new
    }
}
