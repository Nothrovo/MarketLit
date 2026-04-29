package com.app.foodorder.marketlit.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.foodorder.marketlit.R
import com.app.foodorder.marketlit.model.BurungItem
import com.app.foodorder.marketlit.ui.DetailBurungActivity
import java.text.NumberFormat
import java.util.Locale

class BurungMarketAdapter(
    private var items: List<BurungItem>
) : RecyclerView.Adapter<BurungMarketAdapter.BurungViewHolder>() {

    private val rupiahFormat = NumberFormat.getNumberInstance(Locale("id", "ID"))

    inner class BurungViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: CardView       = itemView.findViewById(R.id.cardBurung) // r`oot CardView
        val viewImgBg: View      = itemView.findViewById(R.id.viewImgBg)
        val tvEmoji: TextView    = itemView.findViewById(R.id.tvBirdEmoji)
        val tvBadge: TextView    = itemView.findViewById(R.id.tvBadge)
        val overlayTerjual: View = itemView.findViewById(R.id.overlayTerjual)
        val tvTerjual: TextView  = itemView.findViewById(R.id.tvTerjual)
        val tvNama: TextView     = itemView.findViewById(R.id.tvNamaBurung)
        val tvHarga: TextView    = itemView.findViewById(R.id.tvHarga)
        val tvLokasi: TextView   = itemView.findViewById(R.id.tvLokasi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BurungViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_burung_card, parent, false)
        return BurungViewHolder(v)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BurungViewHolder, position: Int) {
        val item = items[position]
        val ctx  = holder.itemView.context

        // Background warna card image
        holder.viewImgBg.setBackgroundColor(
            ContextCompat.getColor(
                ctx,
                if (item.bgAmber) R.color.a10ll else R.color.g30ll
            )
        )
        holder.itemView.setOnClickListener {
            val intent = Intent(ctx, DetailBurungActivity::class.java)
            // Kalau cara DetailBurungActivity.EXTRA_BURUNG masih merah,
            // pakai string manual saja sementara:
            intent.putExtra("EXTRA_BURUNG", item)
            ctx.startActivity(intent)
        }

        // Emoji burung
        holder.tvEmoji.text = item.emojiGambar

        // Badge kondisi
        holder.tvBadge.text = item.kondisi
        if (!item.stokTersedia) {
            holder.tvBadge.backgroundTintList =
                ContextCompat.getColorStateList(ctx, R.color.text_light)
            holder.tvBadge.text = "Terjual"
            holder.overlayTerjual.visibility = View.VISIBLE
            holder.tvTerjual.visibility      = View.VISIBLE
        } else {
            holder.tvBadge.backgroundTintList = null
            holder.overlayTerjual.visibility  = View.GONE
            holder.tvTerjual.visibility       = View.GONE
        }

        // Teks info
        holder.tvNama.text   = item.nama
        holder.tvHarga.text  = "Rp ${rupiahFormat.format(item.harga)}"
        holder.tvLokasi.text = item.lokasi

        // Click → Detail
        holder.itemView.setOnClickListener {
            val intent = Intent(ctx, DetailBurungActivity::class.java)
            // "EXTRA_BURUNG" adalah nama kuncinya, item adalah datanya
            intent.putExtra("EXTRA_BURUNG", item)
            ctx.startActivity(intent)
        }
    }

    /** Perbarui list (misal setelah filter/search) */
    fun updateData(newItems: List<BurungItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}