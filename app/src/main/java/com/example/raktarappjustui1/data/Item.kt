package com.example.raktarappjustui1.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    var name: String,
    var category: String,
    var quantityUnit: String,
    var quantity: Double
): Parcelable
