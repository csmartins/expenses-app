package com.example.expenses

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

internal class ReceiptRVAdapter (
    private var receiptList: ArrayList<ReceiptRVModal>,
    private var ctx: Context
) : RecyclerView.Adapter<ReceiptRVAdapter.ReceiptViewHolder>() {

    class ReceiptViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val storeNameTV: TextView = itemView.findViewById(R.id.idTVStoreName)
        val storeTypeAndDateTV: TextView = itemView.findViewById(R.id.idTVStoreTypeAndDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.receipt_item,
            parent,
            false
        )
        return ReceiptViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return receiptList.size
    }

    override fun onBindViewHolder(holder: ReceiptViewHolder, position: Int) {
        val receiptInfo = receiptList.get(position)

        holder.storeNameTV.text = receiptInfo.store
        holder.storeTypeAndDateTV.text = "Mercado - " + receiptInfo.purchaseDate

        // below line is use to add on click listener for our item of recycler view.
//        holder.itemView.setOnClickListener {
//            // inside on click listener method we are calling a new activity
//            // and passing all the data of that item in next intent.
//            val i = Intent(ctx, ReceiptDetailsActivity::class.java)
//            i.putExtra("store", receiptInfo.store)
//            i.putExtra("total", receiptInfo.total)
//            i.putExtra("payment", receiptInfo.payment)
//
////            val purchaseDateate = "$receiptInfo.purchaseDate.day-$receiptInfo.purchaseDate.month-$receiptInfo.purchaseDate.year"
//            i.putExtra("purchaseDate", receiptInfo.purchaseDate)
////            i.putExtra("products", receiptInfo.products)
//
//            // after passing that data we are starting our new intent.
//            ctx.startActivity(i)
//        }
    }

}