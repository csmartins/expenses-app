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
        val storeTypeTV: TextView = itemView.findViewById(R.id.idTVStoreType)
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
        holder.storeTypeTV.text = "Mercado"

//        holder.itemView.setOnClickListener {
//            val i = Intent(ctx, ...)
//        }
    }

}