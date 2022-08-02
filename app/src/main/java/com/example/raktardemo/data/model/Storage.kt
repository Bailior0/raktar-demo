package com.example.raktardemo.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Storage(
    var id: String,
    var name: String,
    var address: String,
    var size: Double,
    var description: String,
    var workers: List<Worker>,
    var accountableWorker: String,
    var items: List<StoredItem?>
) : Parcelable
