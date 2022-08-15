package com.example.raktardemo.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Moving(
    var id: String = UUID.randomUUID().toString(),
    var quantity: Double = 0.0,
    var movingDate: String = "",
    var sourceStorage: String = "",
    var destinationStorage: String = ""
): Parcelable
