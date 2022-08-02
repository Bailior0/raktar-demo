package com.example.raktardemo.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoredItem (
    var id: String,
    var item: Item,
//    TODO: felhasznált itemeket a bevétekben külön tömbbe rakni, hogy legyen nyoma, de ne kelljen külön osztály?
    var itemAcquisitions: List<ItemAcquisition>,
    var reservations: List<Reservation>,
    var releases: List<Release>,

    var currentQuantity: Double/* {
        var cnt = freeQuantity
        for resItem in itemAcquisitions {
            for reserved in resItem.reserved {
                cnt += reserved
            }
        }
        return cnt
    }*/,

    var freeQuantity: Double/* {
        var cnt = 0.0
        for acqItem in itemAcquisitions {
            for count in acqItem.packageCounts {
                cnt += count
            }
        }
        return cnt
    }*/

): Parcelable