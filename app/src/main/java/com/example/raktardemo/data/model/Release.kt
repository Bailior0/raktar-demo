package com.example.raktardemo.data.model

import android.os.Parcelable
import com.example.raktardemo.data.enums.PackageState
import com.example.raktardemo.data.enums.Quality
import com.google.firebase.firestore.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class Release(
    var acqId: String = "0",
    var quantity: Double = 0.0,
    @get:Exclude
    var qualityEnum: Quality = Quality.Good,
    var quality: String = qualityEnum.toString().lowercase(),
    @get:Exclude
    var packageStateEnum: PackageState? = null,
    var packageState: String = packageStateEnum.toString().lowercase(),
): Parcelable
