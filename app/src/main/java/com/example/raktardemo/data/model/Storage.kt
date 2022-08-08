package com.example.raktardemo.data.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize

@Parcelize
data class Storage(
    @DocumentId
    var id: String = "",
    var name: String = "",
    var address: String = "",
    var size: Double = 0.0,
    var description: String = ""
) : Parcelable
