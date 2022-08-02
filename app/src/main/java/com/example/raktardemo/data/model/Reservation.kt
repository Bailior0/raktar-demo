package com.example.raktardemo.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Reservation(
    var reservationGoal: String,
    var reservationDate: Date,
    var reservationGoalDate: Date,
    var reservationQuantity: Double,
    var cancelled: Boolean,
    var repeatAmount: Int
): Parcelable
