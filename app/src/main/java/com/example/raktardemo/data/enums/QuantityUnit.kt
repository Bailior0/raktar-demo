package com.example.raktardemo.data.enums

import com.google.firebase.firestore.PropertyName

enum class QuantityUnit {
    @PropertyName("liter")
    Liter,
    @PropertyName("kilogram")
    Kilogram,
    @PropertyName("piece")
    Piece,
    @PropertyName("meter")
    Meter,
    @PropertyName("other")
    Other
}