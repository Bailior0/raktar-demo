package com.example.raktardemo.data.model

import android.os.Parcelable
import com.example.raktardemo.data.enums.Ownership
import com.example.raktardemo.data.enums.PackageType
import com.example.raktardemo.data.enums.QuantityUnit
import com.google.firebase.firestore.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    var id: String = "0",
    var name: String = "",
    var category: Category = Category("0", ""),
    var manufacturer: String = "",
    var serialNumber: String = "",
    // TODO: vonalkód
    @get:Exclude
    var typeEnum: PackageType = PackageType.Piece,
    var type: String = typeEnum.toString().lowercase(),
    @get:Exclude
    var quantityUnitEnum: QuantityUnit = QuantityUnit.Piece,
    var quantityUnit: String = quantityUnitEnum.toString().lowercase(),
    var defaultPackageQuantity: Double = 0.0,
    var openable: Boolean = false, // on piece false by default
    var defaultPurchasePrice: Double? = null,
    var minimumStoredQuantity: Double? = null
): Parcelable
