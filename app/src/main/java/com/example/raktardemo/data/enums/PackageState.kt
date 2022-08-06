package com.example.raktardemo.data.enums

import com.google.firebase.firestore.PropertyName

enum class PackageState(val translation: String) {
    @PropertyName("opened")
    Opened("bontott"),
    @PropertyName("full")
    Full("bontatlan")
}