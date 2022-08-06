package com.example.raktardemo.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoredItem (
    var id: String = "0",
    var item: Item = Item(),
//    TODO: felhasznált itemeket a bevétekben külön tömbbe rakni, hogy legyen nyoma, de ne kelljen külön osztály?
    var itemAcquisitions: List<ItemAcquisition> = emptyList(),
    var reservations: List<Reservation> = emptyList(),
    var releases: List<Release> = emptyList(),
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