package com.example.raktardemo.data.enums

import com.google.firebase.firestore.PropertyName

enum class Quality {
    @PropertyName("good")
    Good,
    @PropertyName("faulty")
    Faulty,
    @PropertyName("other")
    Other
}