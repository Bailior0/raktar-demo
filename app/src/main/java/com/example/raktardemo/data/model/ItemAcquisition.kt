package com.example.raktardemo.data.model

import android.os.Parcelable
import com.example.raktardemo.data.enums.Ownership
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class ItemAcquisition (
    var id: String,
    var acquisitionDate: Date,
    var acquisitionWorker: String,
    var expiryDate: Date,
    var quantity: Double,
    var acquisitionPrice: Double,
    var pricePerUnit: Double,
    var currentStorage: String,
    var ownedBy: Ownership,
    var packageCounts: List<Double>,
    var reserved: List<Double>,
    var released: List<Double>
): Parcelable