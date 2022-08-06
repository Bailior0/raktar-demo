package com.example.raktardemo.data.enums

import com.google.firebase.firestore.PropertyName

enum class StatisticsType {
    @PropertyName("movements")
    Movements,
    @PropertyName("charts")
    Charts,
    @PropertyName("notifications")
    Notifications
}