package com.example.raktardemo.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Category (
    var id: String = UUID.randomUUID().toString(),
    var name: String = ""
): Parcelable
