package com.app.foodorder.marketlit

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class LombaAdapter(
    var lombaList: List<Lomba>,
    val onDaftarClick: (Lomba) -> Unit
) : RecyclerView.Adapter<LombaAdapter.LombaViewHolder>() {

    class LombaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvLombaTitle)
        val tvDate: TextView = itemView.findViewById(R.id.tvLombaDate)
        val tvCategory: TextView = itemView.findViewById(R.id.tvLombaCategory)
        val tvStatus: TextView = itemView.findViewById(R.id.tvLombaStatus)
        val tvPrize: TextView = itemView.findViewById(R.id.tvLombaPrize)
        val btnDaftar: TextView = itemView.findViewById(R.id.btnDaftar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LombaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lomba_card, parent, false)
        return LombaViewHolder(view)
    }

    override fun onBindViewHolder(holder: LombaViewHolder, position: Int) {
        val lomba = lombaList[position]

        holder.tvTitle.text = lomba.title
        holder.tvDate.text = lomba.dateLocation
        holder.tvCategory.text = lomba.categories
        holder.tvPrize.text = lomba.prize

        // Set badge warna berdasarkan status
        val (bgColor, textColor) = when (lomba.status) {
            "Dibuka" -> Pair("#E8F5E9", "#1B5E20")
            "Segera" -> Pair("#FFF8E1", "#8A5220")
            else      -> Pair("#F0F0EE", "#8A9590") // Selesai
        }

        holder.tvStatus.text = lomba.status
        holder.tvStatus.setTextColor(Color.parseColor(textColor))

        val badgeDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(Color.parseColor(bgColor))
            cornerRadius = 100f  // Force full pill shape
        }
        holder.tvStatus.background = badgeDrawable

        // Klik tombol Daftar
        holder.btnDaftar.setOnClickListener {
            onDaftarClick(lomba)
            Toast.makeText(
                holder.itemView.context,
                "Mendaftar ke ${lomba.title}...",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Klik seluruh item → Detail
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailLombaActivity::class.java)
            intent.putExtra("EXTRA_LOMBA", lomba)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = lombaList.size

    fun updateData(list: List<Lomba>) {
        lombaList = list
        notifyDataSetChanged()
    }
}