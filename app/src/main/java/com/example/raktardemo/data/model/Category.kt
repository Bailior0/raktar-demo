package com.example.raktardemo.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category (
    var id: String = "0",
    var name: String = ""
): Parcelable
