// DokterFragment.kt
package com.app.foodorder.marketlit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.app.foodorder.marketlit.databinding.FragmentDokterBinding

class DokterFragment : Fragment() {

    private var _binding: FragmentDokterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DokterViewModel by viewModels()
    private lateinit var dokterAdapter: DokterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDokterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupFilterChips()
        observeViewModel()
    }

    // ── RecyclerView list dokter ──────────────────────────────────────────────
    private fun setupRecyclerView() {
        dokterAdapter = DokterAdapter(
            onChatClick = { dokter ->
                // TODO: buka chat dengan dokter
            },
            onJadwalClick = { dokter ->
                // TODO: buka halaman jadwal konsultasi
            }
        )

        binding.rvDokterList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = dokterAdapter
        }
    }

    // ── Filter: Semua / Online / Rating ──────────────────────────────────────
    private fun setupFilterChips() {
        binding.chipGroupDokterFilter.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isEmpty()) return@setOnCheckedStateChangeListener
            val chip = group.findViewById<Chip>(checkedIds.first())
            viewModel.filter(chip?.text?.toString() ?: "Semua")
        }
    }

    // ── Observe LiveData ──────────────────────────────────────────────────────
    private fun observeViewModel() {
        viewModel.dokterList.observe(viewLifecycleOwner) { list ->
            dokterAdapter.submitList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
