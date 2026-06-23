package com.app.foodorder.marketlit

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PengaturanActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    private lateinit var etNama: EditText
    private lateinit var etEmail: EditText
    private lateinit var etHp: EditText
    private lateinit var etLokasi: EditText
    private lateinit var etJenisBurung: EditText
    private lateinit var spinnerTema: Spinner
    private lateinit var btnSimpan: Button
    private lateinit var btnBackPengaturan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeHelper.applyTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengaturan)

        prefs = getSharedPreferences("USER_PROFILE", MODE_PRIVATE)

        // Bind views
        etNama         = findViewById(R.id.etNama)
        etEmail        = findViewById(R.id.etEmail)
        etHp           = findViewById(R.id.etHp)
        etLokasi       = findViewById(R.id.etLokasi)
        etJenisBurung  = findViewById(R.id.etJenisBurung)
        spinnerTema    = findViewById(R.id.spinnerTema)
        btnSimpan      = findViewById(R.id.btnSimpan)
        btnBackPengaturan = findViewById(R.id.btnBackPengaturan)

        // Setup theme spinner
        val temaOptions = listOf("Default (Ikut HP)", "Light Mode", "Dark Mode")
        val temaAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            temaOptions
        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        spinnerTema.adapter = temaAdapter
        spinnerTema.setSelection(prefs.getInt("THEME_MODE", 0))

        // Load data dari SharedPreferences ke form
        loadData()

        // Tombol back → finish
        btnBackPengaturan.setOnClickListener { finish() }

        // Tombol simpan → save ke prefs → Toast → finish
        btnSimpan.setOnClickListener {
            saveData()
            Toast.makeText(this, "Profil berhasil diperbarui!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun loadData() {
        etNama.setText(prefs.getString("USER_NAME", ""))
        etEmail.setText(prefs.getString("USER_EMAIL", ""))
        etHp.setText(prefs.getString("USER_HP", ""))
        etLokasi.setText(prefs.getString("USER_LOKASI", ""))
        etJenisBurung.setText(prefs.getString("USER_BURUNG", ""))
    }

    private fun saveData() {
        prefs.edit()
            .putString("USER_NAME",    etNama.text.toString().trim())
            .putString("USER_EMAIL",   etEmail.text.toString().trim())
            .putString("USER_HP",      etHp.text.toString().trim())
            .putString("USER_LOKASI",  etLokasi.text.toString().trim())
            .putString("USER_BURUNG",  etJenisBurung.text.toString().trim())
            .putInt("THEME_MODE",      spinnerTema.selectedItemPosition)
            .apply()

        // Terapkan perubahan tema secara langsung
        ThemeHelper.applyTheme(this)
    }
}
