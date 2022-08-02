package com.example.raktardemo.data.model

import android.os.Parcelable
import com.example.raktardemo.data.enums.PackageState
import com.example.raktardemo.data.enums.Quality
import kotlinx.parcelize.Parcelize

@Parcelize
data class Release(
    var acqId: String,
    var quantity: Double,
    var quality: Quality,
    var packageState: PackageState?
): Parcelable
