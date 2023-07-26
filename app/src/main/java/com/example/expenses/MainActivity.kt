package com.example.expenses

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
//import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var mRequestQueue: RequestQueue
    lateinit var loadingPB: ProgressBar
    lateinit var receiptsList: ArrayList<ReceiptRVModal>
//    lateinit var mAddReceiptButton: FloatingActionButton
    val storesThumbs = mapOf(
        "HORTIFRUTI" to "https://hortifruti.com.br/venia-static/icons/hortifruti_192.png",
        "RAIADROGASIL" to "https://logodownload.org/wp-content/uploads/2018/01/droga-raia-logo.png",
        "LEADER" to "https://institucional.lojasleader.com.br/wp-content/uploads/2022/06/logo-slogan-vermelho.jpg",
//        "LEADER" to "https://institucional.lojasleader.com.br/wp-content/uploads/2022/04/Leader-Slogan-01.png",
        "PACHECO" to "https://logodownload.org/wp-content/uploads/2022/08/drogarias-pacheco-logo-4.png",
        "Mundial" to "https://rjempregos.net/wp-content/uploads/2023/02/supermercados-mundial.png",
        "ZONA SUL" to "https://classificadosbarra.com.br/wp-content/uploads/2021/04/supermercadosZonaSul.png",
    )

    val DEFAULT_STORE_THUMB = "https://bolademeia.org/wp-content/uploads/2020/02/Lojinha-do-Bola.png"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        mAddReceiptButton = findViewById(R.id.addReceipt)
//        mAddReceiptButton.setOnClickListener(View.OnClickListener {
//            var ctx: Context
//            val i = Intent(this, QRCodeScannerActivity::class.java)
//            this.startActivity(i)
//        })
//        loadingPB = findViewById(R.id.idLoadingPB)
//        loadingPB.visibility = View.VISIBLE
        receiptsList = getReceiptsData()
        val adapter = ReceiptRVAdapter(receiptsList, this@MainActivity)
        val layoutManager = LinearLayoutManager(this)
        val mRecyclerView = findViewById<RecyclerView>(R.id.idRVReceipts) as RecyclerView

        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = adapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun extractReceiptValues(items: JSONArray): ArrayList<ReceiptRVModal> {
        val receipts = ArrayList<ReceiptRVModal>()
        for (i in 0 until items.length()) {
            val item = items.getJSONObject(i)
            val url = item.optString("url")
            val store = item.optString("store")
            val tmpTotal = item.optString("total").replace(',','.')
            val total = tmpTotal.toDouble()
            val payment = item.optString("payment")
            val tmpDate = item.optString("datetime")
            val purchaseDate = tmpDate.take(16)
            val productsArrayList: ArrayList<ProductRVModal> = ArrayList()
            val productsArray = item.getJSONArray("products")
            if (productsArray.length() != 0) {
                for (j in 0 until productsArray.length()) {
                    val productItem = productsArray.getJSONObject(j)
                    val productName = productItem.optString("name")
                    val productType = productItem.optString("type")
                    val tmpQuantity = productItem.optString("product_quantity").replace(',','.')
                    val productQuantity = tmpQuantity.toDouble()
                    val productUnitType = productItem.optString("unity_type")
                    val tmpValue = productItem.optString("total_value").replace(',','.')
                    val productValue = tmpValue.toDouble()

                    val product = ProductRVModal(
                        productName,
                        productType,
                        productQuantity,
                        productUnitType,
                        productValue
                    )
                    productsArrayList.add(product)
                }
            }

            val thumbnail = getThumbImage(store)

            val receipt = ReceiptRVModal(
                url,
                store,
                total,
                payment,
                purchaseDate,
                thumbnail,
                productsArrayList,
                productsArrayList.size
            )
            receipts.add(receipt)
        }

        return receipts
    }

    private fun getThumbImage(store: String): String {
        val stores = storesThumbs.keys.toList()
        val thumbKey = stores.find{ item -> item in store}
        return storesThumbs.getOrDefault(thumbKey, DEFAULT_STORE_THUMB)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getReceiptsData(): ArrayList<ReceiptRVModal> {
        receiptsList = ArrayList()
        val url = "http://10.0.2.2:8000/api/receipts"

        val httpAsync = Fuel.get("http://10.0.2.2:8000/api/receipts")
            .responseString() { request, response, result ->
                request.header("Accept", "application/json")
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        ex.printStackTrace()
                        Toast.makeText(this@MainActivity, "No receipts found..", Toast.LENGTH_SHORT).show()

                    }
                    is Result.Success -> {
                        val itemsString = result.get()
                        val items = JSONObject(itemsString)
                        val receipts = items.getJSONArray("receipts")
                        receiptsList = extractReceiptValues(receipts)
                    }
                }
            }
        httpAsync.join()
        return receiptsList
    }
}