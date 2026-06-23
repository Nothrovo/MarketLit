package com.app.foodorder.marketlit.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MarketLitDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "MarketLit.db"
        const val DATABASE_VERSION = 1
    }

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.setForeignKeyConstraintsEnabled(true)
    }

    override fun onCreate(db: SQLiteDatabase) {
        createTables(db)
        seedData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS chat_messages")
        db.execSQL("DROP TABLE IF EXISTS riwayat_transaksis")
        db.execSQL("DROP TABLE IF EXISTS riwayat_lombas")
        db.execSQL("DROP TABLE IF EXISTS lombas")
        db.execSQL("DROP TABLE IF EXISTS burung_items")
        db.execSQL("DROP TABLE IF EXISTS breeders")
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    private fun createTables(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nama TEXT NOT NULL,
                email TEXT UNIQUE NOT NULL,
                phone TEXT,
                lokasi TEXT,
                jenis_burung_andalan TEXT,
                role TEXT NOT NULL,
                avatar TEXT
            )
        """)

        db.execSQL("""
            CREATE TABLE breeders (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                farm_name TEXT NOT NULL,
                rating REAL DEFAULT 0.0,
                description TEXT,
                FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
            )
        """)

        db.execSQL("""
            CREATE TABLE burung_items (
                id TEXT PRIMARY KEY,
                nama TEXT NOT NULL,
                jenis TEXT NOT NULL,
                harga INTEGER NOT NULL,
                lokasi TEXT NOT NULL,
                kondisi TEXT,
                penjual_id INTEGER NOT NULL,
                rating_penjual REAL,
                emoji_gambar TEXT,
                stok_tersedia INTEGER DEFAULT 1,
                deskripsi TEXT,
                is_featured INTEGER DEFAULT 0,
                bg_amber INTEGER DEFAULT 0,
                FOREIGN KEY(penjual_id) REFERENCES users(id) ON DELETE CASCADE
            )
        """)

        db.execSQL("""
            CREATE TABLE lombas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                date_location TEXT NOT NULL,
                categories TEXT,
                status TEXT NOT NULL,
                prize TEXT,
                description TEXT
            )
        """)

        db.execSQL("""
            CREATE TABLE riwayat_lombas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                lomba_id INTEGER NOT NULL,
                hasil TEXT NOT NULL,
                tanggal TEXT,
                FOREIGN KEY(user_id) REFERENCES users(id),
                FOREIGN KEY(lomba_id) REFERENCES lombas(id)
            )
        """)

        db.execSQL("""
            CREATE TABLE riwayat_transaksis (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                nama_item TEXT NOT NULL,
                harga INTEGER NOT NULL,
                tanggal TEXT NOT NULL,
                tipe TEXT NOT NULL,
                FOREIGN KEY(user_id) REFERENCES users(id)
            )
        """)

        db.execSQL("""
            CREATE TABLE chat_messages (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                sender_id INTEGER NOT NULL,
                receiver_id INTEGER NOT NULL,
                teks TEXT NOT NULL,
                waktu TEXT NOT NULL,
                is_read INTEGER DEFAULT 0,
                FOREIGN KEY(sender_id) REFERENCES users(id),
                FOREIGN KEY(receiver_id) REFERENCES users(id)
            )
        """)
    }

    private fun seedData(db: SQLiteDatabase) {
        // ── Seed Users ───────────────────────────────────────────────────────
        db.execSQL("INSERT INTO users (nama, email, phone, lokasi, jenis_burung_andalan, role, avatar) VALUES ('Pak Joko', 'pakjoko@marketlit.com', '081234567001', 'Jakarta Selatan', 'Murai Batu', 'Peternak', '👨‍🌾')")
        db.execSQL("INSERT INTO users (nama, email, phone, lokasi, jenis_burung_andalan, role, avatar) VALUES ('Bu Siti', 'busiti@marketlit.com', '081234567002', 'Bandung', 'Kenari', 'Peternak', '👩‍🌾')")
        db.execSQL("INSERT INTO users (nama, email, phone, lokasi, jenis_burung_andalan, role, avatar) VALUES ('Mas Rudi', 'masrudi@marketlit.com', '081234567003', 'Semarang', 'Cucak Hijau', 'Peternak', '🧑‍🌾')")
        db.execSQL("INSERT INTO users (nama, email, phone, lokasi, jenis_burung_andalan, role, avatar) VALUES ('Bang Deni', 'bangdeni@marketlit.com', '081234567004', 'Malang', 'Anis Kembang', 'Peternak', '👨‍🌾')")
        db.execSQL("INSERT INTO users (nama, email, phone, lokasi, jenis_burung_andalan, role, avatar) VALUES ('Pak Hadi', 'pakhadi@marketlit.com', '081234567005', 'Solo', 'Perkutut', 'Peternak', '👨‍🌾')")
        db.execSQL("INSERT INTO users (nama, email, phone, lokasi, jenis_burung_andalan, role, avatar) VALUES ('Mas Andi', 'masandi@marketlit.com', '081234567006', 'Surabaya', 'Lovebird', 'Penjual', '👤')")
        db.execSQL("INSERT INTO users (nama, email, phone, lokasi, jenis_burung_andalan, role, avatar) VALUES ('Pak Budi', 'pakbudi@marketlit.com', '081234567007', 'Yogyakarta', 'Kacer', 'Penjual', '👤')")
        db.execSQL("INSERT INTO users (nama, email, phone, lokasi, jenis_burung_andalan, role, avatar) VALUES ('Toko KicauJaya', 'kicaujaya@marketlit.com', '081234567008', 'Bekasi', '', 'Penjual', '🏪')")
        db.execSQL("INSERT INTO users (nama, email, phone, lokasi, jenis_burung_andalan, role, avatar) VALUES ('Toko Kayu Mas', 'kayumas@marketlit.com', '081234567009', 'Bandung', '', 'Penjual', '🏪')")
        db.execSQL("INSERT INTO users (nama, email, phone, lokasi, jenis_burung_andalan, role, avatar) VALUES ('SupplyBurung', 'supply@marketlit.com', '081234567010', 'Online', '', 'Penjual', '🏪')")
        db.execSQL("INSERT INTO users (nama, email, phone, lokasi, jenis_burung_andalan, role, avatar) VALUES ('PetShop Sehat', 'petshop@marketlit.com', '081234567011', 'Online', '', 'Penjual', '🏪')")
        db.execSQL("INSERT INTO users (nama, email, phone, lokasi, jenis_burung_andalan, role, avatar) VALUES ('NutriKicau', 'nutri@marketlit.com', '081234567012', 'Online', '', 'Penjual', '🏪')")
        db.execSQL("INSERT INTO users (nama, email, phone, lokasi, jenis_burung_andalan, role, avatar) VALUES ('AksesoriKicau', 'aksesori@marketlit.com', '081234567013', 'Jakarta', '', 'Penjual', '🏪')")

        // ── Seed Breeders ────────────────────────────────────────────────────
        db.execSQL("INSERT INTO breeders (user_id, farm_name, rating, description) VALUES (1, 'Joko Murai Farm', 4.9, 'Spesialis penangkaran Murai Batu ekor panjang dengan trah juara dan mental petarung.')")
        db.execSQL("INSERT INTO breeders (user_id, farm_name, rating, description) VALUES (2, 'Siti Kenari Jaya', 4.7, 'Fokus pada breeding Kenari Yorkshire dan Kenari lokal kualitas suara nyaring panjang.')")
        db.execSQL("INSERT INTO breeders (user_id, farm_name, rating, description) VALUES (3, 'Rudi Hijau Farm', 4.6, 'Pakar breeding Cucak Hijau dan Anis Merah siap kontes dengan pakan herbal alami.')")
        db.execSQL("INSERT INTO breeders (user_id, farm_name, rating, description) VALUES (4, 'Deni Anis Kembang', 4.7, 'Penangkaran Anis Kembang mandiri, sehat, lincah, dan garansi gacor ring terdaftar.')")
        db.execSQL("INSERT INTO breeders (user_id, farm_name, rating, description) VALUES (5, 'Hadi Perkutut Luhur', 4.4, 'Pelestari Perkutut Lokal pilihan dengan katuranggan bagus dan suara merdu klasik.')")

        // ── Seed Burung Items ────────────────────────────────────────────────
        db.execSQL("INSERT INTO burung_items (id, nama, jenis, harga, lokasi, kondisi, penjual_id, rating_penjual, emoji_gambar, stok_tersedia, deskripsi, is_featured, bg_amber) VALUES ('1', 'Murai Batu Medan Gacor', 'Burung', 2500000, 'Jakarta Selatan', 'Gacor', 1, 4.9, '🐦', 1, 'Murai Batu Medan, gacor isian banyak, bodi panjang, ekor rapi. Siap lomba. Sudah makan voer & kroto.', 1, 0)")
        db.execSQL("INSERT INTO burung_items (id, nama, jenis, harga, lokasi, kondisi, penjual_id, rating_penjual, emoji_gambar, stok_tersedia, deskripsi, is_featured, bg_amber) VALUES ('2', 'Kenari Yorkshire F2', 'Burung', 850000, 'Bandung', 'Siap Lomba', 2, 4.7, '🐤', 1, 'Kenari Yorkshire F2, warna kuning solid, suara panjang, jinak dan sehat. Sudah vaksin ND.', 0, 1)")
        db.execSQL("INSERT INTO burung_items (id, nama, jenis, harga, lokasi, kondisi, penjual_id, rating_penjual, emoji_gambar, stok_tersedia, deskripsi, is_featured, bg_amber) VALUES ('3', 'Lovebird Dakocan Ngekek', 'Burung', 650000, 'Surabaya', 'Ngekek Panjang', 6, 4.8, '💚', 1, 'LB Dakocan ngekek panjang, mental bagus, sudah sering ikut latber dan selalu juara kelas B.', 0, 0)")
        db.execSQL("INSERT INTO burung_items (id, nama, jenis, harga, lokasi, kondisi, penjual_id, rating_penjual, emoji_gambar, stok_tersedia, deskripsi, is_featured, bg_amber) VALUES ('4', 'Kacer Poci Betina', 'Burung', 400000, 'Yogyakarta', 'Sehat', 7, 4.5, '🐦', 0, 'Kacer poci betina, lincah, makan voer, cocok untuk master atau ternak.', 0, 0)")
        db.execSQL("INSERT INTO burung_items (id, nama, jenis, harga, lokasi, kondisi, penjual_id, rating_penjual, emoji_gambar, stok_tersedia, deskripsi, is_featured, bg_amber) VALUES ('5', 'Cucak Hijau Full Isian', 'Burung', 1200000, 'Semarang', 'Full Isian', 3, 4.6, '🦜', 1, 'Cucak hijau full isian, isian murai, kenari, dan ciblek. Mental besi, sudah juara di beberapa event regional.', 1, 0)")
        db.execSQL("INSERT INTO burung_items (id, nama, jenis, harga, lokasi, kondisi, penjual_id, rating_penjual, emoji_gambar, stok_tersedia, deskripsi, is_featured, bg_amber) VALUES ('6', 'Perkutut Lokal Manggung', 'Burung', 300000, 'Solo', 'Manggung', 5, 4.4, '🕊️', 1, 'Perkutut lokal, sudah manggung rutin. Suara merdu dan nyaring. Harga nego.', 0, 1)")
        db.execSQL("INSERT INTO burung_items (id, nama, jenis, harga, lokasi, kondisi, penjual_id, rating_penjual, emoji_gambar, stok_tersedia, deskripsi, is_featured, bg_amber) VALUES ('7', 'Sangkar Bulat Minimalis', 'Kandang', 150000, 'Bekasi', 'Baru', 8, 4.8, '🧰', 1, 'Sangkar bambu bulat finishing halus, ukuran 40cm, cocok untuk lovebird dan kenari.', 0, 1)")
        db.execSQL("INSERT INTO burung_items (id, nama, jenis, harga, lokasi, kondisi, penjual_id, rating_penjual, emoji_gambar, stok_tersedia, deskripsi, is_featured, bg_amber) VALUES ('8', 'Anis Kembang Siap Gacor', 'Burung', 750000, 'Malang', 'Gacor', 4, 4.7, '🐦', 1, 'Anis kembang jantan dewasa, gacor isian lengkap, bodi padat, ekor panjang. Bisa nego tipis.', 0, 0)")
        db.execSQL("INSERT INTO burung_items (id, nama, jenis, harga, lokasi, kondisi, penjual_id, rating_penjual, emoji_gambar, stok_tersedia, deskripsi, is_featured, bg_amber) VALUES ('9', 'Sangkar Jati Premium', 'Kandang', 450000, 'Bandung', 'Baru', 9, 4.8, '🏠', 1, 'Sangkar kayu jati ukir, finishing halus', 0, 1)")
        db.execSQL("INSERT INTO burung_items (id, nama, jenis, harga, lokasi, kondisi, penjual_id, rating_penjual, emoji_gambar, stok_tersedia, deskripsi, is_featured, bg_amber) VALUES ('10', 'Jangkrik Kering 1kg', 'Pakan', 45000, 'Online', 'Baru', 10, 4.6, '🌾', 1, 'Jangkrik kering kualitas premium', 0, 0)")
        db.execSQL("INSERT INTO burung_items (id, nama, jenis, harga, lokasi, kondisi, penjual_id, rating_penjual, emoji_gambar, stok_tersedia, deskripsi, is_featured, bg_amber) VALUES ('11', 'Vitamin Burung Kicau', 'Perlengkapan', 35000, 'Online', 'Baru', 11, 4.5, '💊', 1, 'Suplemen vitamin lengkap untuk burung kicau', 0, 1)")
        db.execSQL("INSERT INTO burung_items (id, nama, jenis, harga, lokasi, kondisi, penjual_id, rating_penjual, emoji_gambar, stok_tersedia, deskripsi, is_featured, bg_amber) VALUES ('12', 'Voer Breder Premium 1kg', 'Pakan', 28000, 'Online', 'Baru', 12, 4.7, '🌿', 1, 'Voer premium tinggi protein, untuk semua jenis burung kicau', 0, 0)")
        db.execSQL("INSERT INTO burung_items (id, nama, jenis, harga, lokasi, kondisi, penjual_id, rating_penjual, emoji_gambar, stok_tersedia, deskripsi, is_featured, bg_amber) VALUES ('13', 'Tempat Minum Otomatis', 'Perlengkapan', 22000, 'Jakarta', 'Baru', 13, 4.3, '💧', 1, 'Tempat minum anti tumpah dengan kapasitas 250ml', 0, 1)")

        // ── Seed Lombas ──────────────────────────────────────────────────────
        db.execSQL("INSERT INTO lombas (title, date_location, categories, status, prize, description) VALUES ('🏆 Kicau Mania Cup 2025', '📅 20 Mei 2025 · Surabaya', '🐦 Murai Batu, Kacer', 'Dibuka', 'Rp 50.000.000', 'Lomba bergengsi tahunan untuk para Kicau Mania. Siapkan burung jagoanmu! Hadiah total mencapai 50 juta rupiah dengan kategori Murai Batu dan Kacer kelas dunia.')")
        db.execSQL("INSERT INTO lombas (title, date_location, categories, status, prize, description) VALUES ('🎵 Festival Kicau Nusantara', '📅 15 Juni 2025 · Jakarta', '🐦 Semua kategori', 'Dibuka', 'Rp 80.000.000', 'Festival kicau terbesar di Nusantara dengan juri profesional dan hadiah fantastis. Terbuka untuk semua jenis burung kicau dengan total hadiah 80 juta rupiah.')")
        db.execSQL("INSERT INTO lombas (title, date_location, categories, status, prize, description) VALUES ('🏅 Latber Spesial Minggu', '📅 10 Mei 2025 · Bandung', '🐦 Kenari, Lovebird', 'Segera', 'Rp 5.000.000', 'Latihan bersama spesial hari minggu untuk melatih mental tanding burung. Cocok untuk pemula maupun senior. Pendaftaran ditutup 3 hari sebelum acara.')")
        db.execSQL("INSERT INTO lombas (title, date_location, categories, status, prize, description) VALUES ('🎖️ Piala Gubernur 2024', '📅 1 Des 2024 · Jogja', '🐦 Cucak Rowo', 'Selesai', 'Rp 100.000.000', 'Lomba bergengsi berhadiah 100 juta rupiah yang telah berhasil diselenggarakan. Terima kasih kepada semua peserta yang telah ikut berpartisipasi dalam Piala Gubernur 2024.')")

        // ── Seed Riwayat Lomba (untuk demo, user_id=1) ──────────────────────
        db.execSQL("INSERT INTO riwayat_lombas (user_id, lomba_id, hasil, tanggal) VALUES (1, 1, 'Peserta', '10 Mei 2025')")
        db.execSQL("INSERT INTO riwayat_lombas (user_id, lomba_id, hasil, tanggal) VALUES (1, 2, 'Peserta', '22 Feb 2025')")
        db.execSQL("INSERT INTO riwayat_lombas (user_id, lomba_id, hasil, tanggal) VALUES (1, 4, 'Juara 1', '14 Agt 2024')")

        // ── Seed Riwayat Transaksi (untuk demo, user_id=1) ──────────────────
        db.execSQL("INSERT INTO riwayat_transaksis (user_id, nama_item, harga, tanggal, tipe) VALUES (1, 'Murai Batu Medan', 2500000, '18 Jun 2025', 'Dibeli')")
        db.execSQL("INSERT INTO riwayat_transaksis (user_id, nama_item, harga, tanggal, tipe) VALUES (1, 'Sangkar Premium Bambu', 450000, '03 Mei 2025', 'Dijual')")
        db.execSQL("INSERT INTO riwayat_transaksis (user_id, nama_item, harga, tanggal, tipe) VALUES (1, 'Kenari F1 Betina', 850000, '28 Apr 2025', 'Dibeli')")
    }
}
