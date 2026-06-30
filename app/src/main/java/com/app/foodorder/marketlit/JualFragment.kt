package com.app.foodorder.marketlit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.foodorder.marketlit.databinding.FragmentJualBinding
import com.app.foodorder.marketlit.db.MarketLitRepository
import com.app.foodorder.marketlit.model.BurungItem
import com.app.foodorder.marketlit.ui.MarketplaceFragment

class JualFragment : Fragment() {

    private var _binding: FragmentJualBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: MarketLitRepository

    companion object {
        private const val REQUEST_IMAGE_PICK = 1001
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJualBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = MarketLitRepository(requireContext())

        setupTopBar()
        setupSpinners()
        setupUploadFoto()
        setupSubmit()
    }

    private fun setupTopBar() {
        binding.btnBackJual.setOnClickListener {
            (activity as? MainActivity)?.loadFragment(MarketplaceFragment())
        }
    }

    private fun setupSpinners() {
        val kategoriOptions = listOf("Pilih Kategori", "Burung", "Kandang", "Pakan", "Perlengkapan", "Peternak")
        val kategoriAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            kategoriOptions
        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        binding.spinnerKategori.adapter = kategoriAdapter

        val kondisiOptions = listOf("Pilih Kondisi", "Baru", "Bekas", "Jantan", "Betina")
        val kondisiAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            kondisiOptions
        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        binding.spinnerKondisi.adapter = kondisiAdapter
    }

    private fun setupUploadFoto() {
        binding.layoutUploadFoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
            startActivityForResult(Intent.createChooser(intent, "Pilih Foto Burung"), REQUEST_IMAGE_PICK)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            Toast.makeText(requireContext(), "Foto berhasil dipilih", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupSubmit() {
        binding.btnPasangIklan.setOnClickListener {
            val nama    = binding.etNamaBurung.text.toString().trim()
            val harga   = binding.etHarga.text.toString().trim()
            val deskripsi = binding.etDeskripsi.text.toString().trim()
            val kategoriPos = binding.spinnerKategori.selectedItemPosition
            val kondisiPos  = binding.spinnerKondisi.selectedItemPosition

            when {
                nama.isEmpty() -> {
                    binding.etNamaBurung.error = "Nama tidak boleh kosong"
                    binding.etNamaBurung.requestFocus()
                }
                harga.isEmpty() || harga == "0" -> {
                    binding.etHarga.error = "Masukkan harga yang valid"
                    binding.etHarga.requestFocus()
                }
                kategoriPos == 0 -> {
                    Toast.makeText(requireContext(), "Pilih kategori dulu ya!", Toast.LENGTH_SHORT).show()
                }
                kondisiPos == 0 -> {
                    Toast.makeText(requireContext(), "Pilih kondisi dulu ya!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val kategori = binding.spinnerKategori.selectedItem.toString()
                    val kondisi = binding.spinnerKondisi.selectedItem.toString()
                    submitIklan(nama, harga.toLong(), deskripsi, kategori, kondisi)
                }
            }
        }
    }

    private fun submitIklan(nama: String, harga: Long, deskripsi: String, kategori: String, kondisi: String) {
        val newItem = BurungItem(
            id = repository.getNextBurungItemId(),
            nama = nama,
            jenis = kategori,
            harga = harga,
            lokasi = "Lokasi Anda",
            kondisi = kondisi,
            penjual = "Anda",
            ratingPenjual = 0f,
            deskripsi = deskripsi,
            emojiGambar = "🐦",
            stokTersedia = true,
            penjualId = 1
        )
        repository.insertBurungItem(newItem)

        Toast.makeText(requireContext(), "Iklan berhasil dipasang! 🎉", Toast.LENGTH_SHORT).show()
        (activity as? MainActivity)?.loadFragment(MarketplaceFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
