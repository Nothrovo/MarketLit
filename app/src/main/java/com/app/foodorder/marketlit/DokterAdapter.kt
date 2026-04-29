// DokterAdapter.kt
package com.app.foodorder.marketlit

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
                // Info dasar
                tvDokterNama.text      = dokter.nama
                tvDokterSpesialis.text = "${dokter.spesialis} · ${dokter.pengalaman} thn"
                tvDokterRating.text    = buildRatingStars(dokter.rating) +
                        " ${dokter.rating} (${dokter.jumlahUlasan})"

                // Avatar emoji
                imgDokterAvatar.contentDescription = dokter.nama

                // Status badge
                when (dokter.status) {
                    DokterViewModel.Status.ONLINE -> {
                        tvDokterStatus.text = "● Online"
                        tvDokterStatus.setBackgroundResource(R.drawable.bg_badge_status_online)
                        tvDokterStatus.setTextColor(
                            itemView.context.getColor(R.color.green_dark)
                        )
                    }
                    DokterViewModel.Status.SIBUK -> {
                        tvDokterStatus.text = "Sibuk"
                        tvDokterStatus.setBackgroundResource(R.drawable.bg_badge_status_sibuk)
                        tvDokterStatus.setTextColor(
                            itemView.context.getColor(R.color.text_light)
                        )
                    }
                    DokterViewModel.Status.OFFLINE -> {
                        tvDokterStatus.text = "Offline"
                        tvDokterStatus.setBackgroundResource(R.drawable.bg_badge_status_sibuk)
                        tvDokterStatus.setTextColor(
                            itemView.context.getColor(R.color.text_light)
                        )
                    }
                }

                if (dokter.status == DokterViewModel.Status.ONLINE) {
                    btnDokterAksi.text = "Chat"
                    btnDokterAksi.setOnClickListener { onChatClick(dokter) }
                } else {
                    btnDokterAksi.text = "Jadwal"
                    btnDokterAksi.setOnClickListener { onJadwalClick(dokter) }
                }
            }
        }

        // Bintang rating berdasarkan nilai (misal 4.7 → ★★★★☆)
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
