package com.example.raktardemo.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Storage(
    var id: String = "0",
    var name: String = "",
    var address: String = "",
    var size: Double = 0.0,
    var description: String = "",
    var workers: List<Worker> = emptyList(),
    var accountableWorker: String = "",
    var items: List<StoredItem?> = emptyList()
) : Parcelable
