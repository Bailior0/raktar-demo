package com.example.raktardemo.data.enums

import com.google.firebase.firestore.PropertyName

enum class PackageType(val translation: String) {
    @PropertyName("package")
    Package("csomag"),
    @PropertyName("piece")
    Piece("egység")
}