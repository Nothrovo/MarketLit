package com.app.foodorder.marketlit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class DashboardFragment : Fragment() {

    // Fungsi ini gunanya buat "memasang" layout XML ke dalam Fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Hubungkan dengan file fragment_dashboard.xml yang kita buat tadi
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Kalau lo mau bikin tombol klik atau logic lainnya,
        // kodenya taruh di sini (mirip onCreate di Activity)
    }
}