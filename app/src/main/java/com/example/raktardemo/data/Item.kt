package com.example.raktardemo.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    var name: String,
    var category: String,
    var quantityUnit: String,
    var quantity: Double
): Parcelable
