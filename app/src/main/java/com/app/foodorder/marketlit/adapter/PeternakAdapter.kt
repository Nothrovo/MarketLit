package com.app.foodorder.marketlit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.foodorder.marketlit.R
import com.app.foodorder.marketlit.model.Breeder

class PeternakAdapter(
    private var items: List<Breeder>,
    private val onChatClick: (Breeder) -> Unit,
    private val onItemClick: (Breeder) -> Unit
) : RecyclerView.Adapter<PeternakAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvEmoji: TextView = itemView.findViewById(R.id.tvBreederEmoji)
        val tvName: TextView = itemView.findViewById(R.id.tvBreederName)
        val tvFarm: TextView = itemView.findViewById(R.id.tvBreederFarm)
        val tvLocation: TextView = itemView.findViewById(R.id.tvBreederLocation)
        val tvRating: TextView = itemView.findViewById(R.id.tvBreederRating)
        val btnChat: Button = itemView.findViewById(R.id.btnChatBreeder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_peternak_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvEmoji.text = item.emoji
        holder.tvName.text = item.name
        holder.tvFarm.text = item.farmName
        holder.tvLocation.text = "📍 ${item.location}"
        holder.tvRating.text = item.rating

        holder.btnChat.setOnClickListener { onChatClick(item) }
        holder.itemView.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<Breeder>) {
        items = newItems
        notifyDataSetChanged()
    }
}
