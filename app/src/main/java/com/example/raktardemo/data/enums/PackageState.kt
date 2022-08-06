package com.example.raktardemo.data.enums

import com.google.firebase.firestore.PropertyName

enum class PackageState {
    @PropertyName("opened")
    Opened,
    @PropertyName("full")
    Full
}