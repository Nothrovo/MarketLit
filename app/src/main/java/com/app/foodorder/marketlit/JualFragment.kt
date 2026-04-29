// JualFragment.kt
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
import androidx.navigation.fragment.findNavController
import com.app.foodorder.marketlit.databinding.FragmentJualBinding

class JualFragment : Fragment() {

    private var _binding: FragmentJualBinding? = null
    private val binding get() = _binding!!

    // Request code untuk image picker
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

        setupTopBar()
        setupSpinners()
        setupUploadFoto()
        setupSubmit()
    }

    // ── Tombol back di top bar ────────────────────────────────────────────────
    private fun setupTopBar() {
        binding.btnBackJual.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    // ── Spinner Kategori & Kondisi ────────────────────────────────────────────
    private fun setupSpinners() {
        // Kategori
        val kategoriOptions = listOf("Pilih Kategori", "Burung", "Kandang", "Pakan", "Perlengkapan", "Peternak")
        val kategoriAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            kategoriOptions
        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        binding.spinnerKategori.adapter = kategoriAdapter

        // Kondisi
        val kondisiOptions = listOf("Pilih Kondisi", "Baru", "Bekas", "Jantan", "Betina")
        val kondisiAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            kondisiOptions
        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        binding.spinnerKondisi.adapter = kondisiAdapter
    }

    // ── Upload foto dari galeri ───────────────────────────────────────────────
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
            // TODO: tampilkan preview foto yang dipilih
            Toast.makeText(requireContext(), "Foto berhasil dipilih", Toast.LENGTH_SHORT).show()
        }
    }

    // ── Validasi & Submit ─────────────────────────────────────────────────────
    private fun setupSubmit() {
        binding.btnPasangIklan.setOnClickListener {
            val nama    = binding.etNamaBurung.text.toString().trim()
            val harga   = binding.etHarga.text.toString().trim()
            val deskripsi = binding.etDeskripsi.text.toString().trim()
            val kategori = binding.spinnerKategori.selectedItemPosition  // 0 = belum pilih
            val kondisi  = binding.spinnerKondisi.selectedItemPosition

            // Validasi sederhana
            when {
                nama.isEmpty() -> {
                    binding.etNamaBurung.error = "Nama tidak boleh kosong"
                    binding.etNamaBurung.requestFocus()
                }
                harga.isEmpty() || harga == "0" -> {
                    binding.etHarga.error = "Masukkan harga yang valid"
                    binding.etHarga.requestFocus()
                }
                kategori == 0 -> {
                    Toast.makeText(requireContext(), "Pilih kategori dulu ya!", Toast.LENGTH_SHORT).show()
                }
                kondisi == 0 -> {
                    Toast.makeText(requireContext(), "Pilih kondisi dulu ya!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    submitIklan(nama, harga.toLong(), deskripsi)
                }
            }
        }
    }

    private fun submitIklan(nama: String, harga: Long, deskripsi: String) {
        Toast.makeText(requireContext(), "Iklan berhasil dipasang! 🎉", Toast.LENGTH_SHORT).show()

        // Kembali ke Market setelah sukses
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
