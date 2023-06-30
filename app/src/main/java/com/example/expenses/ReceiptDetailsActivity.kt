package com.example.expenses

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.io.Serializable

class ReceiptDetailsActivity  : AppCompatActivity() {
    // creating variables for strings,text view,
    // image views and button.
    lateinit var storeTypeTV: TextView
    lateinit var marketAndDateTV: TextView
    lateinit var totalAndPaymentTypeTV: TextView
    lateinit var numberProductsTV: TextView
    lateinit var storeIV: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.receipt_details)

        storeTypeTV = findViewById(R.id.idTVStoreType)
        marketAndDateTV = findViewById(R.id.idTVMarketAndDate)
        totalAndPaymentTypeTV = findViewById(R.id.idTVTotalAndPaymentType)
        numberProductsTV = findViewById(R.id.idTVNoProducts)
        storeIV = findViewById(R.id.idIVStoreLogo)

        val marketType = "Mercado"
        val marketName = intent.getStringExtra("store")
        val date = intent.getStringExtra("purchaseDate")
        val total = intent.getDoubleExtra("total",0.00)
        val paymentType = intent.getStringExtra("payment")
        val marketLogoLink = intent.getStringExtra("marketLogoLink")
        val products = intent.getParcelableArrayListExtra<ProductRVModal>("products")

        storeTypeTV.text = marketType
        marketAndDateTV.text = "$marketName - $date"
        totalAndPaymentTypeTV.text = "R$ $total - $paymentType"
        numberProductsTV.text = "${products?.size} produtos"
        Picasso.get().load(marketLogoLink).into(storeIV);

        val adapter = products?.let { ProductsRVAdapter(it, this@ReceiptDetailsActivity) }
        val layoutManager = LinearLayoutManager(this)
        val mRecyclerView = findViewById<RecyclerView>(R.id.idRVProducts) as RecyclerView

        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = adapter
    }
}