package com.example.raktardemo.data.model

import android.os.Parcelable
import com.example.raktardemo.data.enums.PackageType
import com.example.raktardemo.data.enums.QuantityUnit
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    var id: String,
    var name: String,
    var category: Category,
    var manufacturer: String,
    var serialNumber: String,
    // TODO: vonalkód
    var type: PackageType,
    var quantityUnit: QuantityUnit,
    var defaultPackageQuantity: Double,
    var openable: Boolean, // on piece false by default
    var defaultPurchasePrice: Double?,
    var minimumStoredQuantity: Double?
): Parcelable
