package com.example.raktardemo.data.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class StoredItem (
    @DocumentId
    var id: String = "",
    var item: Item = Item(),
    var itemAcquisitions: List<ItemAcquisition> = emptyList(),
    var reservations: List<Reservation> = emptyList(),
    var releases: List<Release> = emptyList(),
    var movings: List<Moving> = emptyList(),
    var currentQuantity: Double = 0.0,
    var freeQuantity: Double = 0.0

): Parcelable {
    init {
        var cnt2 = 0.0
        for(acqItem in itemAcquisitions) {
            for(count in acqItem.packageCounts) {
                cnt2 += count
            }
        }
        freeQuantity = cnt2

        var cnt = freeQuantity
        for(resItem in itemAcquisitions) {
            for(reserved in resItem.reserved) {
                cnt += reserved
            }
        }
        currentQuantity = cnt
    }
}