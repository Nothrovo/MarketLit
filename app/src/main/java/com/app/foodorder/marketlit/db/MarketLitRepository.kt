package com.app.foodorder.marketlit.db

import android.content.ContentValues
import android.content.Context
import com.app.foodorder.marketlit.ChatMessage
import com.app.foodorder.marketlit.Lomba
import com.app.foodorder.marketlit.RiwayatLomba
import com.app.foodorder.marketlit.RiwayatTransaksi
import com.app.foodorder.marketlit.model.Breeder
import com.app.foodorder.marketlit.model.BurungItem
import com.app.foodorder.marketlit.model.User
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MarketLitRepository(context: Context) {

    private val dbHelper = MarketLitDbHelper(context)

    // ═══════════════════════════════════════════════════════════════════════════
    //  USERS — CRUD
    // ═══════════════════════════════════════════════════════════════════════════

    fun insertUser(user: User): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nama", user.nama)
            put("email", user.email)
            put("phone", user.phone)
            put("lokasi", user.lokasi)
            put("jenis_burung_andalan", user.jenisBurungAndalan)
            put("role", user.role)
            put("avatar", user.avatar)
        }
        return db.insert("users", null, values)
    }

    fun getUserById(id: Int): User? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users WHERE id = ?", arrayOf(id.toString()))
        var user: User? = null
        if (cursor.moveToFirst()) {
            user = User(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                nama = cursor.getString(cursor.getColumnIndexOrThrow("nama")),
                email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
                phone = cursor.getString(cursor.getColumnIndexOrThrow("phone")) ?: "",
                lokasi = cursor.getString(cursor.getColumnIndexOrThrow("lokasi")) ?: "",
                jenisBurungAndalan = cursor.getString(cursor.getColumnIndexOrThrow("jenis_burung_andalan")) ?: "",
                role = cursor.getString(cursor.getColumnIndexOrThrow("role")),
                avatar = cursor.getString(cursor.getColumnIndexOrThrow("avatar")) ?: ""
            )
        }
        cursor.close()
        return user
    }

    fun getUserByEmail(email: String): User? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", arrayOf(email))
        var user: User? = null
        if (cursor.moveToFirst()) {
            user = User(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                nama = cursor.getString(cursor.getColumnIndexOrThrow("nama")),
                email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
                phone = cursor.getString(cursor.getColumnIndexOrThrow("phone")) ?: "",
                lokasi = cursor.getString(cursor.getColumnIndexOrThrow("lokasi")) ?: "",
                jenisBurungAndalan = cursor.getString(cursor.getColumnIndexOrThrow("jenis_burung_andalan")) ?: "",
                role = cursor.getString(cursor.getColumnIndexOrThrow("role")),
                avatar = cursor.getString(cursor.getColumnIndexOrThrow("avatar")) ?: ""
            )
        }
        cursor.close()
        return user
    }

    fun getAllUsers(): List<User> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users", null)
        val list = mutableListOf<User>()
        while (cursor.moveToNext()) {
            list.add(
                User(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    nama = cursor.getString(cursor.getColumnIndexOrThrow("nama")),
                    email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    phone = cursor.getString(cursor.getColumnIndexOrThrow("phone")) ?: "",
                    lokasi = cursor.getString(cursor.getColumnIndexOrThrow("lokasi")) ?: "",
                    jenisBurungAndalan = cursor.getString(cursor.getColumnIndexOrThrow("jenis_burung_andalan")) ?: "",
                    role = cursor.getString(cursor.getColumnIndexOrThrow("role")),
                    avatar = cursor.getString(cursor.getColumnIndexOrThrow("avatar")) ?: ""
                )
            )
        }
        cursor.close()
        return list
    }

    fun updateUser(user: User): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nama", user.nama)
            put("email", user.email)
            put("phone", user.phone)
            put("lokasi", user.lokasi)
            put("jenis_burung_andalan", user.jenisBurungAndalan)
            put("role", user.role)
            put("avatar", user.avatar)
        }
        return db.update("users", values, "id = ?", arrayOf(user.id.toString()))
    }

    fun deleteUser(id: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete("users", "id = ?", arrayOf(id.toString()))
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  BREEDERS — CRUD
    // ═══════════════════════════════════════════════════════════════════════════

    fun insertBreeder(userId: Int, farmName: String, rating: Float, description: String): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("user_id", userId)
            put("farm_name", farmName)
            put("rating", rating)
            put("description", description)
        }
        return db.insert("breeders", null, values)
    }

    fun getBreederById(id: Int): Breeder? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            """SELECT b.id, b.user_id, u.nama, b.farm_name, u.lokasi, b.rating, u.avatar, b.description
               FROM breeders b JOIN users u ON b.user_id = u.id
               WHERE b.id = ?""",
            arrayOf(id.toString())
        )
        var breeder: Breeder? = null
        if (cursor.moveToFirst()) {
            breeder = Breeder(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id")),
                name = cursor.getString(cursor.getColumnIndexOrThrow("nama")),
                farmName = cursor.getString(cursor.getColumnIndexOrThrow("farm_name")),
                location = cursor.getString(cursor.getColumnIndexOrThrow("lokasi")) ?: "",
                rating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating")),
                emoji = cursor.getString(cursor.getColumnIndexOrThrow("avatar")) ?: "",
                description = cursor.getString(cursor.getColumnIndexOrThrow("description")) ?: ""
            )
        }
        cursor.close()
        return breeder
    }

    fun getAllBreeders(): List<Breeder> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            """SELECT b.id, b.user_id, u.nama, b.farm_name, u.lokasi, b.rating, u.avatar, b.description
               FROM breeders b JOIN users u ON b.user_id = u.id""",
            null
        )
        val list = mutableListOf<Breeder>()
        while (cursor.moveToNext()) {
            list.add(
                Breeder(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id")),
                    name = cursor.getString(cursor.getColumnIndexOrThrow("nama")),
                    farmName = cursor.getString(cursor.getColumnIndexOrThrow("farm_name")),
                    location = cursor.getString(cursor.getColumnIndexOrThrow("lokasi")) ?: "",
                    rating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating")),
                    emoji = cursor.getString(cursor.getColumnIndexOrThrow("avatar")) ?: "",
                    description = cursor.getString(cursor.getColumnIndexOrThrow("description")) ?: ""
                )
            )
        }
        cursor.close()
        return list
    }

    fun updateBreeder(id: Int, farmName: String, rating: Float, description: String): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("farm_name", farmName)
            put("rating", rating)
            put("description", description)
        }
        return db.update("breeders", values, "id = ?", arrayOf(id.toString()))
    }

    fun deleteBreeder(id: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete("breeders", "id = ?", arrayOf(id.toString()))
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  BURUNG ITEMS — CRUD
    // ═══════════════════════════════════════════════════════════════════════════

    fun insertBurungItem(item: BurungItem): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("id", item.id)
            put("nama", item.nama)
            put("jenis", item.jenis)
            put("harga", item.harga)
            put("lokasi", item.lokasi)
            put("kondisi", item.kondisi)
            put("penjual_id", item.penjualId)
            put("rating_penjual", item.ratingPenjual)
            put("emoji_gambar", item.emojiGambar)
            put("stok_tersedia", if (item.stokTersedia) 1 else 0)
            put("deskripsi", item.deskripsi)
            put("is_featured", if (item.isFeatured) 1 else 0)
            put("bg_amber", if (item.bgAmber) 1 else 0)
        }
        return db.insertWithOnConflict("burung_items", null, values, android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE)
    }

    fun getBurungItemById(id: String): BurungItem? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            """SELECT bi.*, u.nama AS penjual_nama
               FROM burung_items bi JOIN users u ON bi.penjual_id = u.id
               WHERE bi.id = ?""",
            arrayOf(id)
        )
        var item: BurungItem? = null
        if (cursor.moveToFirst()) {
            item = cursorToBurungItem(cursor)
        }
        cursor.close()
        return item
    }

    fun getAllBurungItems(): List<BurungItem> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            """SELECT bi.*, u.nama AS penjual_nama
               FROM burung_items bi JOIN users u ON bi.penjual_id = u.id""",
            null
        )
        val list = mutableListOf<BurungItem>()
        while (cursor.moveToNext()) {
            list.add(cursorToBurungItem(cursor))
        }
        cursor.close()
        return list
    }

    fun getBurungItemsByJenis(jenis: String): List<BurungItem> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            """SELECT bi.*, u.nama AS penjual_nama
               FROM burung_items bi JOIN users u ON bi.penjual_id = u.id
               WHERE bi.jenis = ?""",
            arrayOf(jenis)
        )
        val list = mutableListOf<BurungItem>()
        while (cursor.moveToNext()) {
            list.add(cursorToBurungItem(cursor))
        }
        cursor.close()
        return list
    }

    fun getFeaturedItems(): List<BurungItem> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            """SELECT bi.*, u.nama AS penjual_nama
               FROM burung_items bi JOIN users u ON bi.penjual_id = u.id
               WHERE bi.is_featured = 1 AND bi.stok_tersedia = 1""",
            null
        )
        val list = mutableListOf<BurungItem>()
        while (cursor.moveToNext()) {
            list.add(cursorToBurungItem(cursor))
        }
        cursor.close()
        return list
    }

    fun searchBurungItems(query: String): List<BurungItem> {
        val db = dbHelper.readableDatabase
        val q = "%$query%"
        val cursor = db.rawQuery(
            """SELECT bi.*, u.nama AS penjual_nama
               FROM burung_items bi JOIN users u ON bi.penjual_id = u.id
               WHERE bi.nama LIKE ? OR bi.jenis LIKE ? OR bi.lokasi LIKE ? OR u.nama LIKE ?""",
            arrayOf(q, q, q, q)
        )
        val list = mutableListOf<BurungItem>()
        while (cursor.moveToNext()) {
            list.add(cursorToBurungItem(cursor))
        }
        cursor.close()
        return list
    }

    fun getBurungItemsByPenjualId(penjualId: Int): List<BurungItem> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            """SELECT bi.*, u.nama AS penjual_nama
               FROM burung_items bi JOIN users u ON bi.penjual_id = u.id
               WHERE bi.penjual_id = ?""",
            arrayOf(penjualId.toString())
        )
        val list = mutableListOf<BurungItem>()
        while (cursor.moveToNext()) {
            list.add(cursorToBurungItem(cursor))
        }
        cursor.close()
        return list
    }

    fun updateBurungItem(item: BurungItem): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nama", item.nama)
            put("jenis", item.jenis)
            put("harga", item.harga)
            put("lokasi", item.lokasi)
            put("kondisi", item.kondisi)
            put("penjual_id", item.penjualId)
            put("rating_penjual", item.ratingPenjual)
            put("emoji_gambar", item.emojiGambar)
            put("stok_tersedia", if (item.stokTersedia) 1 else 0)
            put("deskripsi", item.deskripsi)
            put("is_featured", if (item.isFeatured) 1 else 0)
            put("bg_amber", if (item.bgAmber) 1 else 0)
        }
        return db.update("burung_items", values, "id = ?", arrayOf(item.id))
    }

    fun deleteBurungItem(id: String): Int {
        val db = dbHelper.writableDatabase
        return db.delete("burung_items", "id = ?", arrayOf(id))
    }

    private fun cursorToBurungItem(cursor: android.database.Cursor): BurungItem {
        return BurungItem(
            id = cursor.getString(cursor.getColumnIndexOrThrow("id")),
            nama = cursor.getString(cursor.getColumnIndexOrThrow("nama")),
            jenis = cursor.getString(cursor.getColumnIndexOrThrow("jenis")),
            harga = cursor.getLong(cursor.getColumnIndexOrThrow("harga")),
            lokasi = cursor.getString(cursor.getColumnIndexOrThrow("lokasi")),
            kondisi = cursor.getString(cursor.getColumnIndexOrThrow("kondisi")) ?: "",
            penjual = cursor.getString(cursor.getColumnIndexOrThrow("penjual_nama")),
            ratingPenjual = cursor.getFloat(cursor.getColumnIndexOrThrow("rating_penjual")),
            deskripsi = cursor.getString(cursor.getColumnIndexOrThrow("deskripsi")) ?: "",
            emojiGambar = cursor.getString(cursor.getColumnIndexOrThrow("emoji_gambar")) ?: "",
            bgAmber = cursor.getInt(cursor.getColumnIndexOrThrow("bg_amber")) == 1,
            isFeatured = cursor.getInt(cursor.getColumnIndexOrThrow("is_featured")) == 1,
            stokTersedia = cursor.getInt(cursor.getColumnIndexOrThrow("stok_tersedia")) == 1,
            penjualId = cursor.getInt(cursor.getColumnIndexOrThrow("penjual_id"))
        )
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  LOMBAS — CRUD
    // ═══════════════════════════════════════════════════════════════════════════

    fun insertLomba(lomba: Lomba): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("title", lomba.title)
            put("date_location", lomba.dateLocation)
            put("categories", lomba.categories)
            put("status", lomba.status)
            put("prize", lomba.prize)
            put("description", lomba.description)
        }
        return db.insert("lombas", null, values)
    }

    fun getLombaById(id: Int): Lomba? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM lombas WHERE id = ?", arrayOf(id.toString()))
        var lomba: Lomba? = null
        if (cursor.moveToFirst()) {
            lomba = cursorToLomba(cursor)
        }
        cursor.close()
        return lomba
    }

    fun getAllLomba(): List<Lomba> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM lombas", null)
        val list = mutableListOf<Lomba>()
        while (cursor.moveToNext()) {
            list.add(cursorToLomba(cursor))
        }
        cursor.close()
        return list
    }

    fun getLombaByStatus(status: String): List<Lomba> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM lombas WHERE status = ?", arrayOf(status))
        val list = mutableListOf<Lomba>()
        while (cursor.moveToNext()) {
            list.add(cursorToLomba(cursor))
        }
        cursor.close()
        return list
    }

    fun updateLomba(lomba: Lomba): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("title", lomba.title)
            put("date_location", lomba.dateLocation)
            put("categories", lomba.categories)
            put("status", lomba.status)
            put("prize", lomba.prize)
            put("description", lomba.description)
        }
        return db.update("lombas", values, "id = ?", arrayOf(lomba.id.toString()))
    }

    fun deleteLomba(id: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete("lombas", "id = ?", arrayOf(id.toString()))
    }

    private fun cursorToLomba(cursor: android.database.Cursor): Lomba {
        return Lomba(
            id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
            title = cursor.getString(cursor.getColumnIndexOrThrow("title")),
            dateLocation = cursor.getString(cursor.getColumnIndexOrThrow("date_location")),
            categories = cursor.getString(cursor.getColumnIndexOrThrow("categories")) ?: "",
            status = cursor.getString(cursor.getColumnIndexOrThrow("status")),
            prize = cursor.getString(cursor.getColumnIndexOrThrow("prize")) ?: "",
            description = cursor.getString(cursor.getColumnIndexOrThrow("description")) ?: ""
        )
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  RIWAYAT LOMBA — CRUD
    // ═══════════════════════════════════════════════════════════════════════════

    fun insertRiwayatLomba(userId: Int, lombaId: Int, hasil: String, tanggal: String): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("user_id", userId)
            put("lomba_id", lombaId)
            put("hasil", hasil)
            put("tanggal", tanggal)
        }
        return db.insert("riwayat_lombas", null, values)
    }

    fun getRiwayatLombaByUserId(userId: Int): List<RiwayatLomba> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            """SELECT rl.id, rl.user_id, rl.lomba_id, rl.hasil, rl.tanggal,
                      l.title AS nama_lomba, l.date_location AS lokasi
               FROM riwayat_lombas rl JOIN lombas l ON rl.lomba_id = l.id
               WHERE rl.user_id = ?
               ORDER BY rl.id DESC""",
            arrayOf(userId.toString())
        )
        val list = mutableListOf<RiwayatLomba>()
        while (cursor.moveToNext()) {
            list.add(
                RiwayatLomba(
                    namaLomba = cursor.getString(cursor.getColumnIndexOrThrow("nama_lomba")),
                    tanggal = cursor.getString(cursor.getColumnIndexOrThrow("tanggal")) ?: "",
                    lokasi = cursor.getString(cursor.getColumnIndexOrThrow("lokasi")) ?: "",
                    hasil = cursor.getString(cursor.getColumnIndexOrThrow("hasil")),
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id")),
                    lombaId = cursor.getInt(cursor.getColumnIndexOrThrow("lomba_id"))
                )
            )
        }
        cursor.close()
        return list
    }

    fun updateRiwayatLomba(id: Int, hasil: String, tanggal: String): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("hasil", hasil)
            put("tanggal", tanggal)
        }
        return db.update("riwayat_lombas", values, "id = ?", arrayOf(id.toString()))
    }

    fun deleteRiwayatLomba(id: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete("riwayat_lombas", "id = ?", arrayOf(id.toString()))
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  RIWAYAT TRANSAKSI — CRUD
    // ═══════════════════════════════════════════════════════════════════════════

    fun insertRiwayatTransaksi(userId: Int, namaItem: String, harga: Long, tanggal: String, tipe: String): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("user_id", userId)
            put("nama_item", namaItem)
            put("harga", harga)
            put("tanggal", tanggal)
            put("tipe", tipe)
        }
        return db.insert("riwayat_transaksis", null, values)
    }

    fun getRiwayatTransaksiByUserId(userId: Int): List<RiwayatTransaksi> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM riwayat_transaksis WHERE user_id = ? ORDER BY id DESC",
            arrayOf(userId.toString())
        )
        val list = mutableListOf<RiwayatTransaksi>()
        while (cursor.moveToNext()) {
            list.add(
                RiwayatTransaksi(
                    namaItem = cursor.getString(cursor.getColumnIndexOrThrow("nama_item")),
                    harga = cursor.getLong(cursor.getColumnIndexOrThrow("harga")),
                    tanggal = cursor.getString(cursor.getColumnIndexOrThrow("tanggal")),
                    tipe = cursor.getString(cursor.getColumnIndexOrThrow("tipe")),
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"))
                )
            )
        }
        cursor.close()
        return list
    }

    fun updateRiwayatTransaksi(id: Int, namaItem: String, harga: Long, tanggal: String, tipe: String): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nama_item", namaItem)
            put("harga", harga)
            put("tanggal", tanggal)
            put("tipe", tipe)
        }
        return db.update("riwayat_transaksis", values, "id = ?", arrayOf(id.toString()))
    }

    fun deleteRiwayatTransaksi(id: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete("riwayat_transaksis", "id = ?", arrayOf(id.toString()))
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  CHAT MESSAGES — CRUD
    // ═══════════════════════════════════════════════════════════════════════════

    fun insertChatMessage(senderId: Int, receiverId: Int, teks: String, waktu: String): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("sender_id", senderId)
            put("receiver_id", receiverId)
            put("teks", teks)
            put("waktu", waktu)
            put("is_read", 0)
        }
        return db.insert("chat_messages", null, values)
    }

    fun getChatMessages(userId1: Int, userId2: Int): List<ChatMessage> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            """SELECT * FROM chat_messages
               WHERE (sender_id = ? AND receiver_id = ?)
                  OR (sender_id = ? AND receiver_id = ?)
               ORDER BY id ASC""",
            arrayOf(userId1.toString(), userId2.toString(), userId2.toString(), userId1.toString())
        )
        val list = mutableListOf<ChatMessage>()
        while (cursor.moveToNext()) {
            val senderId = cursor.getInt(cursor.getColumnIndexOrThrow("sender_id"))
            list.add(
                ChatMessage(
                    teks = cursor.getString(cursor.getColumnIndexOrThrow("teks")),
                    isUser = senderId == userId1,
                    waktu = cursor.getString(cursor.getColumnIndexOrThrow("waktu")),
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    senderId = senderId,
                    receiverId = cursor.getInt(cursor.getColumnIndexOrThrow("receiver_id")),
                    isRead = cursor.getInt(cursor.getColumnIndexOrThrow("is_read")) == 1
                )
            )
        }
        cursor.close()
        return list
    }

    fun markMessageAsRead(id: Int): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply { put("is_read", 1) }
        return db.update("chat_messages", values, "id = ?", arrayOf(id.toString()))
    }

    fun deleteChatMessage(id: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete("chat_messages", "id = ?", arrayOf(id.toString()))
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  UTILITY
    // ═══════════════════════════════════════════════════════════════════════════

    fun getNextBurungItemId(): String {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT MAX(CAST(id AS INTEGER)) AS max_id FROM burung_items", null)
        var maxId = 0
        if (cursor.moveToFirst()) {
            maxId = cursor.getInt(0)
        }
        cursor.close()
        return (maxId + 1).toString()
    }

    fun getCurrentTimestamp(): String {
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    }

    fun getCurrentDate(): String {
        return SimpleDateFormat("dd MMM yyyy", Locale("id", "ID")).format(Date())
    }

    fun formatRupiah(amount: Long): String {
        return "Rp ${NumberFormat.getNumberInstance(Locale("id", "ID")).format(amount)}"
    }
}
