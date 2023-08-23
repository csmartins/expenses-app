package com.example.expenses

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.await
import com.github.kittinunf.result.Result
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import org.json.JSONObject

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
//    var receiptAPIResult = ""
    private val appContext = appContext

    suspend fun startScan() {
        try {
            val result = scanner.startScan().await()
            barCodeResults.value = result.rawValue
            result.rawValue?.let { Log.d("Barcode:", it) }
            val nfceURL = barCodeResults.value
            var receiptSent = false
            val httpAsync = Fuel.post("http://192.168.0.11:8000/api/receipt")
                .body("{ \"url\" : \"$nfceURL\" }")
                .header("Content-Type", "application/json")
                .response() { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            Log.e("QRCodeScanner:", "Failed to send receipt")
                            val ex = result.getException()
                            ex.printStackTrace()
//                            receiptAPIResult = "Erro ao enviar nota fiscal"
                        }
                        is Result.Success -> {
                            receiptSent = true
                            Log.d("QRCodeScanner:", "Receipt sent with success.")
//                            receiptAPIResult = "Nota fiscal enviada"
                        }
                    }
                }
            httpAsync.join()
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