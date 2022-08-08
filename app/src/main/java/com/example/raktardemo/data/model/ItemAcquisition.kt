package com.example.raktardemo.data.model

import android.os.Parcelable
import com.example.raktardemo.data.enums.Ownership
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class ItemAcquisition (
    var id: String = UUID.randomUUID().toString(),
    var acquisitionDate: String = "",
    var acquisitionWorker: String = "",
    var expiryDate: String = "",
    var quantity: Double = 0.0,
    var acquisitionPrice: Double = 0.0,
    var pricePerUnit: Double = 0.0,
    var currentStorage: String = "",
    var ownedBy: Ownership = Ownership.Own,
    var packageCounts: List<Double> = emptyList(),
    var reserved: List<Double> = emptyList(),
    var released: List<Double> = emptyList()
): Parcelable