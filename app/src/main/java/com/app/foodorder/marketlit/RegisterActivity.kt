package com.app.foodorder.marketlit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.foodorder.marketlit.db.MarketLitRepository
import com.app.foodorder.marketlit.model.User

class RegisterActivity : AppCompatActivity() {

    private var selectedRole = "Pembeli"
    private lateinit var repository: MarketLitRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeHelper.applyTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        repository = MarketLitRepository(this)

        val btnBackReg = findViewById<ImageView>(R.id.btnBackReg)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvGoToLogin = findViewById<TextView>(R.id.tvGoToLogin)
        val etNama = findViewById<EditText>(R.id.etRegNama)
        val etEmail = findViewById<EditText>(R.id.etRegEmail)
        val etPhone = findViewById<EditText>(R.id.etRegPhone)

        val rolePembeli = findViewById<TextView>(R.id.rolePembeli)
        val rolePenjual = findViewById<TextView>(R.id.rolePenjual)
        val rolePeternak = findViewById<TextView>(R.id.rolePeternak)

        val roles = listOf(rolePembeli, rolePenjual, rolePeternak)
        for (role in roles) {
            role.setOnClickListener { view ->
                val clicked = view as TextView
                selectedRole = clicked.text.toString().replace(Regex("[^a-zA-Z]"), "")

                roles.forEach {
                    it.setBackgroundResource(R.drawable.bg_role_inactive)
                    it.setTextColor(androidx.core.content.ContextCompat.getColor(this, R.color.text_mid))
                }

                clicked.setBackgroundResource(R.drawable.bg_role_active)
                clicked.setTextColor(androidx.core.content.ContextCompat.getColor(this, R.color.bg_white))
            }
        }

        btnBackReg.setOnClickListener { finish() }

        btnRegister.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val phone = etPhone.text.toString().trim()

            when {
                nama.isEmpty() -> {
                    etNama.error = "Nama tidak boleh kosong"
                    etNama.requestFocus()
                }
                email.isEmpty() -> {
                    etEmail.error = "Email tidak boleh kosong"
                    etEmail.requestFocus()
                }
                else -> {
                    val existing = repository.getUserByEmail(email)
                    if (existing != null) {
                        etEmail.error = "Email sudah terdaftar"
                        etEmail.requestFocus()
                        return@setOnClickListener
                    }

                    val user = User(
                        nama = nama,
                        email = email,
                        phone = phone,
                        role = selectedRole
                    )
                    val userId = repository.insertUser(user)

                    if (userId > 0) {
                        val prefs = getSharedPreferences("USER_SESSION", MODE_PRIVATE)
                        prefs.edit()
                            .putBoolean("isLoggedIn", true)
                            .putInt("USER_ID", userId.toInt())
                            .apply()

                        getSharedPreferences("USER_PROFILE", MODE_PRIVATE).edit()
                            .putString("USER_NAME", nama)
                            .putString("USER_EMAIL", email)
                            .putString("USER_HP", phone)
                            .apply()

                        Toast.makeText(this, "Akun $selectedRole berhasil dibuat!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Gagal membuat akun. Coba lagi.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        tvGoToLogin.setOnClickListener { finish() }
    }
}
