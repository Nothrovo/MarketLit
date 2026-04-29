package com.app.foodorder.marketlit

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

// Sub CPMK-8: Broadcast Receiver untuk memantau status internet
class NetworkReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val isNotConnected = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
            if (isNotConnected) {
                Toast.makeText(context, "Koneksi terputus! Fitur offline aktif.", Toast.LENGTH_LONG).show()
            }
        }
    }
}