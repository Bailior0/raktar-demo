package com.example.raktardemo.data.model

import android.os.Parcelable
import com.example.raktardemo.data.enums.Ownership
import com.google.firebase.firestore.Exclude
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class ItemAcquisition (
    var id: String = "0",
    var acquisitionDate: String = "",
    var acquisitionWorker: String = "",
    var expiryDate: String = "",
    var quantity: Double = 0.0,
    var acquisitionPrice: Double = 0.0,
    var pricePerUnit: Double = 0.0,
    var currentStorage: String = "",
    @get:Exclude
    var ownedByEnum: Ownership = Ownership.Own,
    var ownedBy: String = ownedByEnum.toString().lowercase(),
    var packageCounts: List<Double> = emptyList(),
    var reserved: List<Double> = emptyList(),
    var released: List<Double> = emptyList()
): Parcelable