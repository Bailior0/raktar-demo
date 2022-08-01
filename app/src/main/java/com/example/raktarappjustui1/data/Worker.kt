package com.example.raktarappjustui1.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Worker(
    var name: String,
    var email: String,
    var phoneNumber: String,
    var storages: List<Storage>
): Parcelable
