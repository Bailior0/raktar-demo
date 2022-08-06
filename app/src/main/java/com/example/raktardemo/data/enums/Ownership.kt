package com.example.raktardemo.data.enums

import com.google.firebase.firestore.PropertyName

enum class Ownership {
    @PropertyName("own")
    Own,
    @PropertyName("foreign")
    Foreign
}