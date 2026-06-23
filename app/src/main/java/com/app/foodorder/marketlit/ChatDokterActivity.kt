// ChatDokterActivity.kt
package com.app.foodorder.marketlit

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.foodorder.marketlit.databinding.ActivityChatDokterBinding
import java.text.SimpleDateFormat
import java.util.*

class ChatDokterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatDokterBinding
    private lateinit var chatAdapter: ChatAdapter
    private val chatMessages = ArrayList<ChatMessage>()
    private val handler = Handler(Looper.getMainLooper())

    private var chatType = "DOKTER"

    // ── Balasan auto dokter ───────────────────────────────────────────────────
    private val autoReplies = listOf(
        "Baik, saya mengerti kondisi burung Anda.",
        "Apa gejala yang terlihat?",
        "Coba berikan pakan yang lebih bergizi.",
        "Saya rekomendasikan konsultasi tatap muka juga.",
        "Bisa ceritakan lebih detail mengenai kondisi burung Anda?",
        "Berapa lama sudah menunjukkan gejala tersebut?",
        "Pastikan kandang bersih dan ventilasi cukup baik."
    )

    // ── Balasan auto peternak ─────────────────────────────────────────────────
    private val breederAutoReplies = listOf(
        "Burung tersebut dalam kondisi sehat dan rajin berkicau.",
        "Stok burung kami selalu terawat dengan pakan pilihan.",
        "Bisa dinego tipis ya, silakan ajukan penawaran.",
        "Boleh dipantau langsung ke lokasi kandang ternak kami.",
        "Kami melayani pengiriman bergaransi hidup sampai tujuan.",
        "Kualitas trah indukan terjamin juara kontes.",
        "Pakan dan vitamin rutin selalu kami berikan setiap hari."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatDokterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ── Ambil data dokter dari Intent ─────────────────────────────────────
        val dokterNama      = intent.getStringExtra("DOKTER_NAMA")      ?: "Dokter"
        val dokterSpesialis = intent.getStringExtra("DOKTER_SPESIALIS") ?: ""
        val dokterStatus    = intent.getStringExtra("DOKTER_STATUS")     ?: "ONLINE"
        val dokterEmoji     = intent.getStringExtra("DOKTER_EMOJI")      ?: "👨‍⚕️"
        chatType = intent.getStringExtra("CHAT_TYPE") ?: "DOKTER"

        setupHeader(dokterNama, dokterStatus, dokterEmoji)
        setupRecyclerView()
        setupSendButton()

        // ── Pesan awal (hardcoded, delay per pesan) ───────────────
        if (chatType == "PETERNAK") {
            sendDokterMessageDelayed("Halo! Saya ${dokterNama} dari ${dokterSpesialis}. 👋", 500)
            sendDokterMessageDelayed("Selamat datang di toko kami. Ada burung atau perlengkapan yang ingin Anda tanyakan?", 1300)
            sendDokterMessageDelayed("Saya siap melayani konsultasi pembelian dan negosiasi.", 2200)
        } else {
            sendDokterMessageDelayed("Halo! Saya ${dokterNama}. 👋", 500)
            sendDokterMessageDelayed("Selamat datang di layanan konsultasi MarketLit.", 1300)
            sendDokterMessageDelayed("Ada yang bisa saya bantu untuk burung kesayangan Anda?", 2200)
        }
    }

    // ── Header topbar ─────────────────────────────────────────────────────────
    private fun setupHeader(nama: String, status: String, emoji: String) {
        binding.tvNamaDokterChat.text = nama
        binding.tvAvatarChat.text     = emoji
        binding.tvStatusChat.text     = when (status) {
            "ONLINE"  -> "● Online"
            "SIBUK"   -> "Sibuk"
            else      -> "Offline"
        }
        binding.tvStatusChat.setTextColor(
            if (status == "ONLINE") Color.parseColor("#A5D6A7")
            else Color.parseColor("#BDBDBD")
        )
        binding.btnBackChat.setOnClickListener { finish() }
    }

    // ── RecyclerView bubble chat ──────────────────────────────────────────────
    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(chatMessages)
        binding.rvChat.apply {
            layoutManager = LinearLayoutManager(this@ChatDokterActivity).apply {
                stackFromEnd = true
            }
            adapter = chatAdapter
        }
    }

    // ── Kirim pesan user ──────────────────────────────────────────────────────
    private fun setupSendButton() {
        binding.btnKirim.setOnClickListener {
            val teks = binding.etPesan.text.toString().trim()
            if (teks.isEmpty()) return@setOnClickListener

            addMessage(ChatMessage(teks, isUser = true))
            binding.etPesan.setText("")

            // Auto-reply dengan delay acak 1–2 detik
            val delay = (1000..2000L).random()
            handler.postDelayed({
                val replies = if (chatType == "PETERNAK") breederAutoReplies else autoReplies
                val reply = replies.random()
                addMessage(ChatMessage(reply, isUser = false))
            }, delay)
        }
    }

    private fun sendDokterMessageDelayed(teks: String, delayMs: Long) {
        handler.postDelayed({
            addMessage(ChatMessage(teks, isUser = false))
        }, delayMs)
    }

    private fun addMessage(msg: ChatMessage) {
        chatMessages.add(msg)
        chatAdapter.notifyItemInserted(chatMessages.size - 1)
        binding.rvChat.scrollToPosition(chatMessages.size - 1)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}

// ── Data class pesan chat ─────────────────────────────────────────────────────
data class ChatMessage(
    val teks: String,
    val isUser: Boolean,
    val waktu: String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
)

// ── Adapter bubble chat ───────────────────────────────────────────────────────
class ChatAdapter(
    private val messages: List<ChatMessage>
) : RecyclerView.Adapter<ChatAdapter.BubbleViewHolder>() {

    inner class BubbleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val llBubbleDokter: ViewGroup = itemView.findViewById(R.id.llBubbleDokter)
        val llBubbleUser: ViewGroup   = itemView.findViewById(R.id.llBubbleUser)
        val tvPesanDokter: TextView   = itemView.findViewById(R.id.tvPesanDokter)
        val tvPesanUser: TextView     = itemView.findViewById(R.id.tvPesanUser)
        val tvWaktuDokter: TextView   = itemView.findViewById(R.id.tvWaktuDokter)
        val tvWaktuUser: TextView     = itemView.findViewById(R.id.tvWaktuUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BubbleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_bubble, parent, false)
        return BubbleViewHolder(view)
    }

    override fun onBindViewHolder(holder: BubbleViewHolder, position: Int) {
        val msg = messages[position]
        if (msg.isUser) {
            holder.llBubbleUser.visibility   = View.VISIBLE
            holder.llBubbleDokter.visibility = View.GONE
            holder.tvPesanUser.text          = msg.teks
            holder.tvWaktuUser.text          = msg.waktu
        } else {
            holder.llBubbleDokter.visibility = View.VISIBLE
            holder.llBubbleUser.visibility   = View.GONE
            holder.tvPesanDokter.text        = msg.teks
            holder.tvWaktuDokter.text        = msg.waktu
        }
    }

    override fun getItemCount() = messages.size
}
