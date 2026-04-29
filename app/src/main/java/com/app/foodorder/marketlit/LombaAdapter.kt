package com.app.foodorder.marketlit

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LombaAdapter(private val lombaList: List<Lomba>) : RecyclerView.Adapter<LombaAdapter.LombaViewHolder>() {

    class LombaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvLombaTitle)
        val tvDate: TextView = itemView.findViewById(R.id.tvLombaDate)
        val tvPrize: TextView = itemView.findViewById(R.id.tvLombaPrize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LombaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lomba_card, parent, false)
        return LombaViewHolder(view)
    }

    override fun onBindViewHolder(holder: LombaViewHolder, position: Int) {
        val lomba = lombaList[position]
        holder.tvTitle.text = lomba.title
        holder.tvDate.text = lomba.dateLocation
        holder.tvPrize.text = lomba.prize

        // Sub CPMK-3: Intent membawa data object ke DetailLombaActivity
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailLombaActivity::class.java)
            intent.putExtra("EXTRA_LOMBA", lomba) // Ngirim data lomba yang di-klik
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = lombaList.size
}