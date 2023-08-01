package com.example.expenses

import android.content.Context
import android.util.Log
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class QRCodeScanner (
    appContext: Context
) {
    /**
     * From the docs: If you know which barcode formats you expect to read, you can improve the
     * speed of the barcode detector by configuring it to only detect those formats.
     */
    private val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_QR_CODE
        )
        .build()

    private val scanner = GmsBarcodeScanning.getClient(appContext, options)
    val barCodeResults = MutableStateFlow<String?>(null)

    suspend fun startScan() {
        try {
            val result = scanner.startScan().await()
            barCodeResults.value = result.rawValue
            result.rawValue?.let { Log.d("Barcode:", it) }
//            Timber.d(barCodeResults.value)
        } catch (e: Exception) {
            e.message?.let { Log.d("scan error: ", it) }
//            Timber.d("scan error: $e")
        }
    }

    /* alt:
    scanner.startScan()
    .addOnSuccessListener { barcode ->
        // Task completed successfully
    }
    .addOnCanceledListener {
        // Task canceled
    }
    .addOnFailureListener { e ->
        // Task failed with an exception
    }*/
}