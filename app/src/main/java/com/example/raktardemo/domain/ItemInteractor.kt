package com.example.raktardemo.domain

import android.util.Log
import com.example.raktardemo.data.datasource.FirebaseDataSource
import com.example.raktardemo.data.enums.PackageState
import com.example.raktardemo.data.enums.PackageType
import com.example.raktardemo.data.model.*
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DatabaseInteractor @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) {

    suspend fun getItems(): Flow<List<StoredItem>> {
        return firebaseDataSource.getItems()
    }

    suspend fun getItemsOnce(): List<StoredItem> {
        return firebaseDataSource.getItemsOnce()
    }

    suspend fun onItemAdded(item: StoredItem) {
        firebaseDataSource.addItem(item)
    }

    suspend fun onItemUpdated(item: StoredItem) {
        var cnt2 = 0.0
        for(acqItem in item.itemAcquisitions) {
            for(count in acqItem.packageCounts) {
                cnt2 += count
            }
        }
        item.freeQuantity = cnt2

        var cnt = item.freeQuantity
        for(resItem in item.itemAcquisitions) {
            for(reserved in resItem.reserved) {
                cnt += reserved
            }
        }
        item.currentQuantity = cnt

        firebaseDataSource.updateItem(item)
    }

    suspend fun getStorages(): List<Storage> {
        return firebaseDataSource.getStorages()
    }

    suspend fun onMoving(item: StoredItem, chosenOpenedPackages: List<Pair<String, Double>>, moving: Moving, packageState: PackageState?) {
        if(item.item.type == PackageType.Package && packageState == PackageState.Opened) {
            moveOpenedPackage(item, chosenOpenedPackages, moving)
        }
        else if(item.item.type == PackageType.Package && packageState == PackageState.Full) {
            moveFullPackage(item, moving)
        }
        else {
            moveItemPiece(item, moving)
        }
    }

    private suspend fun moveOpenedPackage(item: StoredItem, chosenOpenedPackages: List<Pair<String, Double>>, moving: Moving) {
        val localItem: StoredItem = item.copy()

        val acqs = mutableListOf<ItemAcquisition>()

        val moves = item.movings as MutableList<Moving>
        moves.add(moving)
        localItem.movings = moves

        for(acq in localItem.itemAcquisitions) {

            val newAcquisition: ItemAcquisition = acq.copy()
            val oldAcquisition: ItemAcquisition = acq.copy()
            val newAcqPackages = mutableListOf<Double>()
            val oldAcqPackages: MutableList<Double> = acq.packageCounts as MutableList<Double>

            if(acq.currentStorage == moving.sourceStorage) {
                for(packages in acq.packageCounts.sorted()) {
                    for(chosenPackage in chosenOpenedPackages) {
                        if (acq.id == chosenPackage.first && packages == chosenPackage.second) {
                            oldAcqPackages.remove(packages)
                            newAcqPackages.add(packages)
                        }
                    }
                }

                if(newAcqPackages.isNotEmpty()) {
                    newAcquisition.packageCounts = newAcqPackages
                    newAcquisition.currentStorage = moving.destinationStorage
                    newAcquisition.acquisitionDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
                    newAcquisition.acquisitionPrice = newAcquisition.packageCounts.size.toDouble() * newAcquisition.pricePerUnit
                    newAcquisition.released = emptyList()
                    newAcquisition.reserved = emptyList()

                    acqs.add(newAcquisition)
                }


                oldAcquisition.packageCounts = oldAcqPackages
                oldAcquisition.acquisitionPrice = oldAcquisition.packageCounts.size.toDouble() * oldAcquisition.pricePerUnit

                acqs.add(oldAcquisition)
            }
        }

        localItem.itemAcquisitions = acqs

        onItemUpdated(localItem)
    }

    private suspend fun moveFullPackage(item: StoredItem, moving: Moving) {
        var localQuantity = moving.quantity/item.item.defaultPackageQuantity
        val localItem: StoredItem = item.copy()

        val acqs = mutableListOf<ItemAcquisition>()

        val moves = item.movings as MutableList<Moving>
        moves.add(moving)
        localItem.movings = moves

        for(acq in localItem.itemAcquisitions) {

            val newAcquisition: ItemAcquisition = acq.copy()
            val oldAcquisition: ItemAcquisition = acq.copy()
            val newAcqPackages = mutableListOf<Double>()
            val oldAcqPackages: MutableList<Double> = acq.packageCounts as MutableList<Double>

            if(acq.currentStorage == moving.sourceStorage) {
                if(localQuantity != 0.0) {
                    for(packages in acq.packageCounts.sorted()) {

                        if(packages == item.item.defaultPackageQuantity) {
                            oldAcqPackages.remove(packages)
                            newAcqPackages.add(packages)
                            localQuantity--
                        }

                        if(localQuantity == 0.0)
                            break
                    }

                    if(newAcqPackages.isNotEmpty()) {
                        newAcquisition.packageCounts = newAcqPackages
                        newAcquisition.currentStorage = moving.destinationStorage
                        newAcquisition.acquisitionDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
                        newAcquisition.acquisitionPrice = newAcquisition.packageCounts.size.toDouble() * newAcquisition.pricePerUnit
                        newAcquisition.released = emptyList()
                        newAcquisition.reserved = emptyList()

                        acqs.add(newAcquisition)
                    }
                }

                oldAcquisition.packageCounts = oldAcqPackages
                oldAcquisition.acquisitionPrice = oldAcquisition.packageCounts.size.toDouble() * oldAcquisition.pricePerUnit

                acqs.add(oldAcquisition)
            }
        }

        localItem.itemAcquisitions = acqs

        onItemUpdated(localItem)
    }

    private suspend fun moveItemPiece(item: StoredItem, moving: Moving) {
        var localQuantity = moving.quantity
        val localItem: StoredItem = item.copy()

        val acqs = mutableListOf<ItemAcquisition>()

        val moves = item.movings as MutableList<Moving>
        moves.add(moving)
        localItem.movings = moves

        for(acq in localItem.itemAcquisitions) {

            val newAcquisition: ItemAcquisition = acq.copy()
            val oldAcquisition: ItemAcquisition = acq.copy()
            var newQuantity = 0.0
            var oldQuantity: Double = acq.quantity

            if(acq.currentStorage == moving.sourceStorage) {
                if(localQuantity != 0.0) {
                    if(acq.quantity == localQuantity) {
                        oldQuantity = 0.0
                        newQuantity += localQuantity
                        localQuantity = 0.0
                    }

                    else if(acq.quantity < localQuantity) {
                        localQuantity -= oldQuantity
                        newQuantity += oldQuantity
                        oldQuantity = 0.0
                    }

                    else if(acq.quantity > localQuantity) {
                        oldQuantity -= localQuantity
                        newQuantity += localQuantity
                        localQuantity = 0.0
                    }

                    if(newQuantity != 0.0) {
                        newAcquisition.packageCounts = mutableListOf(newQuantity)
                        newAcquisition.currentStorage = moving.destinationStorage
                        newAcquisition.acquisitionDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
                        newAcquisition.acquisitionPrice = newQuantity * newAcquisition.pricePerUnit
                        newAcquisition.released = emptyList()
                        newAcquisition.reserved = emptyList()

                        acqs.add(newAcquisition)
                    }
                }

                oldAcquisition.quantity = oldQuantity
                if(oldQuantity == 0.0)
                    oldAcquisition.packageCounts = emptyList()
                else
                    oldAcquisition.packageCounts = mutableListOf(oldQuantity)

                acqs.add(oldAcquisition)
            }
        }

        localItem.itemAcquisitions = acqs

        onItemUpdated(localItem)
    }

    suspend fun onRelease(release: Release, item: StoredItem?, chosenOpenedPackages: List<Pair<String, Double>>, acqId: String?, group: List<StoredItem>) {
        if(acqId != null) {
            releaseAcquisition(release, acqId, group)
        }
        else if(item != null && release.packageState == PackageState.Opened && item.item.type == PackageType.Package && chosenOpenedPackages.isNotEmpty()) {
            releaseOpenedPackage(release, item, chosenOpenedPackages)
        }
        else if(item != null && ((release.packageState == PackageState.Opened && item.item.type == PackageType.Package && chosenOpenedPackages.isEmpty()) || item.item.type == PackageType.Piece)) {
            releaseOpenableItems(release, item)
        }
        else if(item != null && release.packageState == PackageState.Full && item.item.type == PackageType.Package){
            releaseFullPackage(release, item)
        }
    }

    private suspend fun releaseAcquisition(release: Release, acqId: String, group: List<StoredItem>) {
        val localGroup = group.toSet().toList()
        for(item in localGroup) {
            val newRelease = release.copy()
            val itemReleases = item.releases as MutableList<Release>

            for(acq in item.itemAcquisitions) {
                if(acq.groupingId == acqId) {
                    for(leave in acq.packageCounts)
                        newRelease.quantity += leave

                    val released = acq.released as MutableList<Double>
                    released.addAll(acq.packageCounts)
                    acq.packageCounts = emptyList()
                    acq.released = released
                }
            }

            newRelease.acqId = acqId
            itemReleases.add(newRelease)

            onItemUpdated(item)
        }
    }

    private suspend fun releaseOpenedPackage(release: Release, item: StoredItem, chosenOpenedPackages: List<Pair<String, Double>>) {
        val localItem: StoredItem = item.copy()
        val itemReleases = item.releases as MutableList<Release>
        itemReleases.add(release)

        for(acq in localItem.itemAcquisitions) {

            val newRelease = acq.released as MutableList<Double>
            val newPackages = acq.packageCounts as MutableList<Double>

            for(packages in acq.packageCounts.sorted()) {
                for(chosenPackage in chosenOpenedPackages) {
                    if (acq.id == chosenPackage.first && packages == chosenPackage.second) {
                        newPackages.remove(packages)
                        newRelease.add(packages)
                    }
                }
            }

            acq.packageCounts = newPackages
            acq.acquisitionPrice = acq.packageCounts.size.toDouble() * acq.pricePerUnit

            acq.released = newRelease
        }

        onItemUpdated(localItem)
    }

    private suspend fun releaseFullPackage(release: Release, item: StoredItem) {
        var localQuantity = release.quantity/item.item.defaultPackageQuantity
        val localItem: StoredItem = item.copy()
        val itemReleases = item.releases as MutableList<Release>
        itemReleases.add(release)

        for(acq in localItem.itemAcquisitions) {

            val newRelease = acq.released as MutableList<Double>
            val newPackages = acq.packageCounts as MutableList<Double>

            if(localQuantity != 0.0) {
                for(packages in acq.packageCounts.sorted()) {

                    if(packages == item.item.defaultPackageQuantity) {
                        newPackages.remove(packages)
                        newRelease.add(packages)
                        localQuantity--
                    }

                    if(localQuantity == 0.0)
                        break
                }
            }

            acq.packageCounts = newPackages
            acq.acquisitionPrice = acq.packageCounts.size.toDouble() * acq.pricePerUnit

            acq.released = newRelease
        }

        onItemUpdated(item)
    }

    private suspend fun releaseOpenableItems(release: Release, item: StoredItem) {
        var localQuantity = release.quantity
        val localItem: StoredItem = item.copy()
        val itemReleases = item.releases as MutableList<Release>
        itemReleases.add(release)

        for(acq in localItem.itemAcquisitions) {
            val newRelease = acq.released as MutableList<Double>
            val newPackages = acq.packageCounts as MutableList<Double>

            for(packages in acq.packageCounts.sorted()) {

                if(packages == localQuantity) {
                    newPackages.remove(packages)
                    newRelease.add(packages)
                    localQuantity = 0.0
                    break
                }

                else if(packages < localQuantity) {
                    newPackages.remove(packages)
                    newRelease.add(packages)
                    localQuantity -= packages
                    continue
                }

                else if(packages > localQuantity) {
                    val newPackage = packages - localQuantity
                    newPackages.remove(packages)
                    newPackages.add(newPackage)
                    newRelease.add(localQuantity)
                    localQuantity = 0.0
                    break
                }
            }

            acq.packageCounts = newPackages
            acq.quantity = acq.packageCounts.size.toDouble()
            acq.acquisitionPrice = acq.quantity * acq.pricePerUnit

            acq.released = newRelease

            if(localQuantity == 0.0)
                break
        }

        onItemUpdated(item)
    }

    suspend fun onReservation(reservation: Reservation, item: StoredItem?, acqId: String?, group: List<StoredItem>) {
        if(acqId != null) {
            reserveAcquisition(reservation, acqId!!, group)
        }
        else if(item != null && item.item.type == PackageType.Package && !item.item.openable) {
            reserveUnOpenablePackage(reservation, item)
        }
        else if(item != null) {
            reserveOpenablePackage(reservation, item)
        }
    }

    private suspend fun reserveAcquisition(reservation: Reservation, acqId: String, group: List<StoredItem>) {
        val localGroup = group.toSet().toList()
        for(item in localGroup) {
            val newReservation = reservation.copy()
            val itemReservations = item.reservations as MutableList<Reservation>

            for(acq in item.itemAcquisitions) {
                if(acq.groupingId == acqId) {
                    for(reserve in acq.packageCounts)
                        newReservation.reservationQuantity += reserve

                    val reserved = acq.reserved as MutableList<Double>
                    reserved.addAll(acq.packageCounts)
                    acq.packageCounts = emptyList()
                    acq.reserved = reserved
                }
            }

            newReservation.repeatAmount = 1
            newReservation.acqId = acqId
            itemReservations.add(newReservation)

            Log.i("dolog", item.toString())
            //onItemUpdated(item)
        }
    }

    private suspend fun reserveOpenablePackage(reservation: Reservation, item: StoredItem) {
        var localQuantity = reservation.reservationQuantity * reservation.repeatAmount
        val localItem: StoredItem = item.copy()
        val itemReservations = item.reservations as MutableList<Reservation>
        itemReservations.add(reservation)

        for(acq in localItem.itemAcquisitions) {
            val newReserved = acq.reserved as MutableList<Double>
            val newPackages = acq.packageCounts as MutableList<Double>

            for(packages in acq.packageCounts.sorted()) {

                if(packages == localQuantity) {
                    newPackages.remove(packages)
                    newReserved.add(packages)
                    localQuantity = 0.0
                    break
                }

                else if(packages < localQuantity) {
                    newPackages.remove(packages)
                    newReserved.add(packages)
                    localQuantity -= packages
                    continue
                }

                else if(packages > localQuantity) {
                    val newPackage = packages - localQuantity
                    newPackages.remove(packages)
                    newPackages.add(newPackage)
                    newReserved.add(localQuantity)
                    localQuantity = 0.0
                    break
                }
            }

            acq.packageCounts = newPackages
            acq.acquisitionPrice = acq.packageCounts.size.toDouble() * acq.pricePerUnit

            acq.reserved = newReserved

            if(localQuantity == 0.0)
                break
        }

        //onItemUpdated(item)
    }

    private suspend fun reserveUnOpenablePackage(reservation: Reservation, item: StoredItem) {
        var localQuantity = reservation.reservationQuantity * reservation.repeatAmount
        val localItem: StoredItem = item.copy()
        val itemReservations = item.reservations as MutableList<Reservation>
        itemReservations.add(reservation)

        for(acq in localItem.itemAcquisitions) {
            val newReserved = acq.reserved as MutableList<Double>
            val newPackages = acq.packageCounts as MutableList<Double>

            for(packages in acq.packageCounts.sorted()) {

                if(packages == localQuantity) {
                    newPackages.remove(packages)
                    newReserved.add(packages)
                    localQuantity = 0.0
                    break
                }

                else if(packages < localQuantity) {
                    newPackages.remove(packages)
                    newReserved.add(packages)
                    localQuantity -= packages
                    continue
                }

                else if(packages > localQuantity) {
                    newPackages.remove(packages)
                    newReserved.add(packages)
                    localQuantity = 0.0
                    break
                }
            }

            acq.packageCounts = newPackages
            acq.acquisitionPrice = acq.packageCounts.size.toDouble() * acq.pricePerUnit

            acq.reserved = newReserved

            if(localQuantity == 0.0)
                break
        }

        //onItemUpdated(item)
    }
}