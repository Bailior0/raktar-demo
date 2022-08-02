package com.example.raktardemo.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Worker(
    var id: String,
    var name: String,
    var email: String,
    var phoneNumber: String,
    var storages: List<Storage>
): Parcelable
