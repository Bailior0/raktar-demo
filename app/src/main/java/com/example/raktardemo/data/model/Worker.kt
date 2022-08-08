package com.example.raktardemo.data.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize

@Parcelize
data class Worker(
    @DocumentId
    var id: String = "",
    var name: String = "",
    var email: String = "",
    var phoneNumber: String = ""
): Parcelable
