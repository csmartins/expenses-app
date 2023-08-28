package com.example.expenses

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import java.net.URL

fun sendReceipt(nfceURL: String?) {
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
                Log.d("QRCodeScanner:", "Receipt sent with success.")
                //                            receiptAPIResult = "Nota fiscal enviada"
            }
        }
    }
    httpAsync.join()
}

fun formatReceiptURL(key: String) : String {
    val baseURL = "http://www4.fazenda.rj.gov.br/consultaNFCe/QRCode?p="
    val defaultParameters = "|2|1|2|da7ae607e7059ea7d2249e2a59b23386dee0a32f"
    return baseURL.plus(key).plus(defaultParameters)
}

fun validateAccessKey(key: String) : Boolean {
    return key != "" && key.length == 44
}