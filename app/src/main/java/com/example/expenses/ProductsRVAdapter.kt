package com.example.expenses

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


internal class ProductsRVAdapter (
    private var productsList: ArrayList<ProductRVModal>,
    private var ctx: Context
) : RecyclerView.Adapter<ProductsRVAdapter.ProductViewHolder>() {
    val GENERIC_PRODUCT_IMAGE = "https://w7.pngwing.com/pngs/360/603/png-transparent-milk-milk-carton-angle-photography-logo.png"

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productTypeTV: TextView = itemView.findViewById(R.id.idTVProductType)
        val productNameTV: TextView = itemView.findViewById(R.id.idTVProductName)
        val productQuantityTV: TextView = itemView.findViewById(R.id.idTVProductQuantity)
//        val productUnityTV: TextView = itemView.findViewById(R.id.idTVProductUnity)
        val productPricesTV: TextView = itemView.findViewById(R.id.idTVProductPrices)
        val productIV: ImageView = itemView.findViewById(R.id.idIVProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.product_item,
            parent,
            false
        )
        return ProductViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val productInfo = productsList.get(position)
        Picasso.get().load(GENERIC_PRODUCT_IMAGE).into(holder.productIV)

        holder.productNameTV.text = "Biscoito da Vaquinha"
        holder.productTypeTV.text = "Bixcoito"
        holder.productQuantityTV.text = "Quantidade: ${productInfo.productQuantity.toString()} ${productInfo.unityType}"
//        holder.productUnityTV.text = productInfo.unityType
        holder.productPricesTV.text = "Valor: R$ %.2f".format(productInfo.totalValue)
    }
}