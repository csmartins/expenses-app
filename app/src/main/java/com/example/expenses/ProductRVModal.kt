package com.example.expenses

import android.os.Parcel
import android.os.Parcelable

class ProductRVModal (
    var productQuantity: Double,
    var unityType: String?,
    var totalValue: Double,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readString(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(productQuantity)
        parcel.writeString(unityType)
        parcel.writeDouble(totalValue)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductRVModal> {
        override fun createFromParcel(parcel: Parcel): ProductRVModal {
            return ProductRVModal(parcel)
        }

        override fun newArray(size: Int): Array<ProductRVModal?> {
            return arrayOfNulls(size)
        }
    }

}