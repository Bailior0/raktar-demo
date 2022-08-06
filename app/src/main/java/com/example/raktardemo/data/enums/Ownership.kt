package com.example.raktardemo.data.enums

import com.google.firebase.firestore.PropertyName

enum class Ownership(val translation: String) {
    @PropertyName("own")
    Own("saját"),
    @PropertyName("foreign")
    Foreign("külső")
}