package com.example.expenses

import android.os.Parcel
import android.os.Parcelable

data class ReceiptRVModal (
    var url: String?,
    var store: String?,
    var total: Double,
    var payment: String?,
    var purchaseDate: String?,
    var thumbnail : String?,
    var products: ArrayList<ProductRVModal>,
    var countProducts : Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        arrayListOf<ProductRVModal>().apply {
            parcel.readList(this, ProductRVModal::class.java.classLoader)
        },
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
        parcel.writeString(store)
        parcel.writeDouble(total)
        parcel.writeString(payment)
        parcel.writeString(purchaseDate)
        parcel.writeString(thumbnail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReceiptRVModal> {
        override fun createFromParcel(parcel: Parcel): ReceiptRVModal {
            return ReceiptRVModal(parcel)
        }

        override fun newArray(size: Int): Array<ReceiptRVModal?> {
            return arrayOfNulls(size)
        }
    }
}