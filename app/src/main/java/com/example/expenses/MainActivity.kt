package com.example.expenses

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.se.omapi.SEService.OnConnectedListener
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.HttpResponse
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var mRequestQueue: RequestQueue
    lateinit var loadingPB: ProgressBar
    lateinit var receiptsList: ArrayList<ReceiptRVModal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        loadingPB = findViewById(R.id.idLoadingPB)
//        loadingPB.visibility = View.VISIBLE
        receiptsList = getReceiptsData()
        val adapter = ReceiptRVAdapter(receiptsList, this@MainActivity)
        val layoutManager = LinearLayoutManager(this)
        val mRecyclerView = findViewById<RecyclerView>(R.id.idRVReceipts) as RecyclerView

        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = adapter
    }

    private fun extractReceiptValues(items: JSONArray): ArrayList<ReceiptRVModal> {
        val receipts = ArrayList<ReceiptRVModal>()
        for (i in 0 until items.length()) {
            val item = items.getJSONObject(i)
            val url = item.optString("url")
            val store = item.optString("store")
            val tmpTotal = item.optString("total").replace(',','.')
            val total = tmpTotal.toDouble()
            val payment = item.optString("payment")
            val purchaseDate = item.optString("datetime")

            val productsArrayList: ArrayList<ProductRVModal> = ArrayList()
            val productsArray = item.getJSONArray("products")
            if (productsArray.length() != 0) {
                for (j in 0 until productsArray.length()) {
                    val productItem = productsArray.getJSONObject(j)
                    val tmpQuantity = productItem.optString("product_quantity").replace(',','.')
                    val productQuantity = tmpQuantity.toDouble()
                    val productUnitType = productItem.optString("unity_type")
                    val tmpValue = productItem.optString("total_value").replace(',','.')
                    val productValue = tmpValue.toDouble()

                    val product = ProductRVModal(
                        productQuantity,
                        productUnitType,
                        productValue
                    )
                    productsArrayList.add(product)
                }
            }

            val receipt = ReceiptRVModal(
                url,
                store,
                total,
                payment,
                purchaseDate,
                productsArrayList
            )
            receipts.add(receipt)
        }

        return receipts
    }

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