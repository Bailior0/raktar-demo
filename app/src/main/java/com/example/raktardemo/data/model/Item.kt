package com.example.raktardemo.data.model

import android.os.Parcelable
import com.example.raktardemo.data.enums.PackageType
import com.example.raktardemo.data.enums.QuantityUnit
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    var id: String = "0",
    var name: String = "",
    var category: Category = Category("0", ""),
    var manufacturer: String = "",
    var serialNumber: String = "",
    // TODO: vonalkód
    var type: PackageType = PackageType.Piece,
    var quantityUnit: QuantityUnit = QuantityUnit.Piece,
    var defaultPackageQuantity: Double = 0.0,
    var openable: Boolean = false, // on piece false by default
    var defaultPurchasePrice: Double? = null,
    var minimumStoredQuantity: Double? = null
): Parcelable
