package com.app.foodorder.marketlit

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.foodorder.marketlit.db.MarketLitRepository
import com.app.foodorder.marketlit.model.User

class PengaturanActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private lateinit var repository: MarketLitRepository

    private lateinit var etNama: EditText
    private lateinit var etEmail: EditText
    private lateinit var etHp: EditText
    private lateinit var etLokasi: EditText
    private lateinit var etJenisBurung: EditText
    private lateinit var spinnerTema: Spinner
    private lateinit var btnSimpan: Button
    private lateinit var btnBackPengaturan: Button

    private var userId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeHelper.applyTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengaturan)

        prefs = getSharedPreferences("USER_PROFILE", MODE_PRIVATE)
        repository = MarketLitRepository(this)
        userId = getSharedPreferences("USER_SESSION", MODE_PRIVATE).getInt("USER_ID", 0)

        etNama         = findViewById(R.id.etNama)
        etEmail        = findViewById(R.id.etEmail)
        etHp           = findViewById(R.id.etHp)
        etLokasi       = findViewById(R.id.etLokasi)
        etJenisBurung  = findViewById(R.id.etJenisBurung)
        spinnerTema    = findViewById(R.id.spinnerTema)
        btnSimpan      = findViewById(R.id.btnSimpan)
        btnBackPengaturan = findViewById(R.id.btnBackPengaturan)

        val temaOptions = listOf("Default (Ikut HP)", "Light Mode", "Dark Mode")
        val temaAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            temaOptions
        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        spinnerTema.adapter = temaAdapter
        spinnerTema.setSelection(prefs.getInt("THEME_MODE", 0))

        loadData()

        btnBackPengaturan.setOnClickListener { finish() }

        btnSimpan.setOnClickListener {
            saveData()
            Toast.makeText(this, "Profil berhasil diperbarui!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun loadData() {
        if (userId > 0) {
            val user = repository.getUserById(userId)
            if (user != null) {
                etNama.setText(user.nama)
                etEmail.setText(user.email)
                etHp.setText(user.phone)
                etLokasi.setText(user.lokasi)
                etJenisBurung.setText(user.jenisBurungAndalan)
                return
            }
        }
        etNama.setText(prefs.getString("USER_NAME", ""))
        etEmail.setText(prefs.getString("USER_EMAIL", ""))
        etHp.setText(prefs.getString("USER_HP", ""))
        etLokasi.setText(prefs.getString("USER_LOKASI", ""))
        etJenisBurung.setText(prefs.getString("USER_BURUNG", ""))
    }

    private fun saveData() {
        val nama = etNama.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val hp = etHp.text.toString().trim()
        val lokasi = etLokasi.text.toString().trim()
        val burung = etJenisBurung.text.toString().trim()

        prefs.edit()
            .putString("USER_NAME", nama)
            .putString("USER_EMAIL", email)
            .putString("USER_HP", hp)
            .putString("USER_LOKASI", lokasi)
            .putString("USER_BURUNG", burung)
            .putInt("THEME_MODE", spinnerTema.selectedItemPosition)
            .apply()

        if (userId > 0) {
            val currentUser = repository.getUserById(userId)
            if (currentUser != null) {
                val updatedUser = currentUser.copy(
                    nama = nama,
                    email = email,
                    phone = hp,
                    lokasi = lokasi,
                    jenisBurungAndalan = burung
                )
                repository.updateUser(updatedUser)
            }
        }

        ThemeHelper.applyTheme(this)
    }
}
