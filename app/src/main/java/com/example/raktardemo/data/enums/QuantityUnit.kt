package com.example.raktardemo.data.enums

import com.google.firebase.firestore.PropertyName

enum class QuantityUnit(val translation: String) {
    @PropertyName("liter")
    Liter("liter"),
    @PropertyName("kilogram")
    Kilogram("kilogramm"),
    @PropertyName("piece")
    Piece("darab"),
    @PropertyName("meter")
    Meter("méter"),
    @PropertyName("other")
    Other("egyéb")
}