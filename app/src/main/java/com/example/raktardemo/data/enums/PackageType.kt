package com.example.raktardemo.data.enums

import com.google.firebase.firestore.PropertyName

enum class PackageType {
    @PropertyName("package")
    Package,
    @PropertyName("piece")
    Piece
}