package com.example.raktardemo.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Reservation(
    var reservationGoal: String = "",
    var reservationDate: String = "",
    var reservationGoalDate: String = "",
    var reservationQuantity: Double = 0.0,
    var cancelled: Boolean = false,
    var repeatAmount: Int = 0
): Parcelable
