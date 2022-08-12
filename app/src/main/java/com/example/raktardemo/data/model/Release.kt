package com.example.raktardemo.data.model

import android.os.Parcelable
import com.example.raktardemo.data.enums.PackageState
import com.example.raktardemo.data.enums.Quality
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Release(
    var id: String = UUID.randomUUID().toString(),
    var acqId: String = UUID.randomUUID().toString() ,
    var releaseDate: String = "",
    var quantity: Double = 0.0,
    var quality: Quality = Quality.Good,
    var packageState: PackageState? = null
): Parcelable
