package com.example.raktardemo.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Group(
    var number: Int,
    var items: List<Item>
): Parcelable
