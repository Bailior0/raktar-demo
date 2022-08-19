package com.example.raktardemo.data.model

import android.os.Parcelable
import com.example.raktardemo.data.enums.PackageType
import com.example.raktardemo.data.enums.QuantityUnit
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Item(
    var id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var category: Category = Category(),
    var manufacturer: String = "",
    var serialNumber: String = "",
    var barCode: String = "",
    var type: PackageType = PackageType.Piece,
    var quantityUnit: QuantityUnit = QuantityUnit.Piece,
    var defaultPackageQuantity: Double = 0.0,
    var openable: Boolean = false,
    var defaultPurchasePrice: Double? = 0.0,
    var minimumStoredQuantity: Double? = 0.0
): Parcelable
