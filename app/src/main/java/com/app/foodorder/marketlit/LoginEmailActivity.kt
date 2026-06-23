package com.app.foodorder.marketlit

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.foodorder.marketlit.db.MarketLitRepository
import com.app.foodorder.marketlit.model.User

class LoginEmailActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var repository: MarketLitRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeHelper.applyTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_email)

        sharedPreferences = getSharedPreferences("USER_SESSION", MODE_PRIVATE)
        repository = MarketLitRepository(this)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvGoToRegister = findViewById<TextView>(R.id.tvGoToRegister)
        val btnGoogle = findViewById<Button>(R.id.btnGoogle)
        val btnFacebook = findViewById<Button>(R.id.btnFacebook)
        val etEmail = findViewById<EditText>(R.id.etEmail)

        btnBack.setOnClickListener {
            val intent = Intent(this, LandingActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            if (email.isEmpty()) {
                etEmail.error = "Masukkan email atau no. HP"
                etEmail.requestFocus()
                return@setOnClickListener
            }

            val user = repository.getUserByEmail(email)
            if (user != null) {
                loginSuccess(user)
            } else {
                Toast.makeText(this, "Akun tidak ditemukan. Silakan daftar terlebih dahulu.", Toast.LENGTH_SHORT).show()
            }
        }

        btnGoogle.setOnClickListener {
            quickLogin("Google")
        }

        btnFacebook.setOnClickListener {
            quickLogin("Facebook")
        }

        tvGoToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginSuccess(user: com.app.foodorder.marketlit.model.User) {
        sharedPreferences.edit()
            .putBoolean("isLoggedIn", true)
            .putInt("USER_ID", user.id)
            .apply()

        getSharedPreferences("USER_PROFILE", MODE_PRIVATE).edit()
            .putString("USER_NAME", user.nama)
            .putString("USER_EMAIL", user.email)
            .putString("USER_HP", user.phone)
            .putString("USER_LOKASI", user.lokasi)
            .putString("USER_BURUNG", user.jenisBurungAndalan)
            .apply()

        Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun quickLogin(provider: String) {
        val email = "user_${provider.lowercase()}@marketlit.com"
        var user = repository.getUserByEmail(email)
        if (user == null) {
            val newUser = User(
                nama = "Pengguna $provider",
                email = email,
                role = "Pembeli"
            )
            val id = repository.insertUser(newUser)
            user = newUser.copy(id = id.toInt())
        }
        Toast.makeText(this, "Login $provider Berhasil!", Toast.LENGTH_SHORT).show()
        loginSuccess(user!!)
    }
}
