package ru.yoequilibrium.testdatabindingrange

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.ObservableField

class ProductsFilterParams : Parcelable {
    var minPrice = 0f

    var maxPrice = Float.MAX_VALUE

    @Transient
    val selectedPrice: ObservableField<List<Float?>> = ObservableField(listOf(minPrice,maxPrice))

    constructor() {}
    private constructor(`in`: Parcel) {
        minPrice = `in`.readFloat()
        maxPrice = `in`.readFloat()
        val priceList = listOf<Float>()
        `in`.readList(priceList,Float.javaClass.classLoader)
        selectedPrice.set(priceList)
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeFloat(minPrice)
        dest.writeFloat(maxPrice)
        dest.writeList(selectedPrice.get())
    }

    override fun describeContents(): Int {
        return 0
    }

    /*fun getMinPrice(): Int {
        return Math.round(minPrice - 0.5f)
    }

    fun getMaxPrice(): Int {
        return Math.round(maxPrice + 0.5f)
    }*/

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ProductsFilterParams?> = object :
            Parcelable.Creator<ProductsFilterParams?> {
            override fun createFromParcel(`in`: Parcel): ProductsFilterParams {
                return ProductsFilterParams(`in`)
            }

            override fun newArray(size: Int): Array<ProductsFilterParams?> {
                return arrayOfNulls(size)
            }
        }
    }
}