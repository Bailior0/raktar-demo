package com.example.raktarappjustui1.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Storage(
    var name: String,
    var address: String,
    var size: Double,
    var description: String,
    var workers: List<Worker>
) : Parcelable
