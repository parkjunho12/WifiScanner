package com.datau.wifiandusim

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var wifiManager: WifiManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if(!wifiManager.isWifiEnabled)Toast.makeText(applicationContext,"wifi fail",Toast.LENGTH_SHORT).show()
        else{
            wifiSearch(applicationContext)
        }
    }

    private fun wifiSearch(context: Context){

        val wifiScanReceiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
                if (success) {
                    scanSuccess()
                } else {
                    scanFailure()
                }
            }
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        context.registerReceiver(wifiScanReceiver, intentFilter)

        val success = wifiManager.startScan()
        if (!success) {

            // scan failure handling
            scanFailure()
        }

    }
    private fun scanSuccess() {
        val results = wifiManager.scanResults
        for (result in results){
            Toast.makeText(applicationContext, result.SSID,Toast.LENGTH_SHORT).show()
        }

    }

    private fun scanFailure() {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        val results = wifiManager.scanResults
        Log.d("wifi",results[0].SSID)
        for (result in results){
            Toast.makeText(applicationContext, result.SSID,Toast.LENGTH_SHORT).show()
        }

    }
    override fun onResume() {
        super.onResume()


    }

    override fun onPause() {
        super.onPause()

    }
}
