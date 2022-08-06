package com.example.raktardemo.data.enums

import com.google.firebase.firestore.PropertyName

enum class Quality(val translation: String) {
    @PropertyName("good")
    Good("jó"),
    @PropertyName("faulty")
    Faulty("hibás"),
    @PropertyName("other")
    Other("egyéb")
}