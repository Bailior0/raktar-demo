package com.example.raktardemo.domain

import android.util.Log
import com.example.raktardemo.data.datasource.FirebaseDataSource
import com.example.raktardemo.data.enums.PackageState
import com.example.raktardemo.data.enums.PackageType
import com.example.raktardemo.data.model.ItemAcquisition
import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.StoredItem
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

    suspend fun onMoving(item: StoredItem, quantity: Double, startStorage: Storage, destinationStorage: Storage, packageState: PackageState?) {
        if(item.item.type == PackageType.Package && packageState == PackageState.Opened) {
            moveOpenedPackage(item, quantity, startStorage, destinationStorage)
        }
        else if(item.item.type == PackageType.Package && packageState == PackageState.Full) {
            moveFullPackage(item, quantity, startStorage, destinationStorage)
        }
        else {
            moveItemPiece(item, quantity, startStorage, destinationStorage)
        }
    }

    private suspend fun moveOpenedPackage(item: StoredItem, quantity: Double, startStorage: Storage, destinationStorage: Storage) {
        var localQuantity = quantity
        val localItem: StoredItem = item
        val acqs = mutableListOf<ItemAcquisition>()

        for(acq in localItem.itemAcquisitions) {

            val newAcquisition: ItemAcquisition = acq.copy()
            val oldAcquisition: ItemAcquisition = acq.copy()
            val newAcqPackages = mutableListOf<Double>()
            val oldAcqPackages: MutableList<Double> = acq.packageCounts as MutableList<Double>

            if(acq.currentStorage == startStorage.id) {
                for(packages in acq.packageCounts.reversed()) {

                    if(packages == localQuantity) {
                        oldAcqPackages.remove(packages)
                        newAcqPackages.add(packages)
                        localQuantity = 0.0
                        break
                    }

                    else if(packages < localQuantity) {
                        oldAcqPackages.remove(packages)
                        newAcqPackages.add(packages)
                        localQuantity -= packages
                        continue
                    }

                    else if(packages > localQuantity) {
                        val newPackage = packages - localQuantity
                        oldAcqPackages.remove(packages)
                        oldAcqPackages.add(newPackage)
                        newAcqPackages.add(localQuantity)
                        localQuantity = 0.0
                        break
                    }
                }

                newAcquisition.packageCounts = newAcqPackages
                newAcquisition.currentStorage = destinationStorage.id
                newAcquisition.acquisitionDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
                newAcquisition.quantity = newAcquisition.packageCounts.size.toDouble()
                newAcquisition.acquisitionPrice = newAcquisition.quantity * newAcquisition.pricePerUnit

                if(oldAcqPackages.isNotEmpty()) {
                    oldAcquisition.packageCounts = oldAcqPackages
                    oldAcquisition.quantity = oldAcquisition.packageCounts.size.toDouble()
                    oldAcquisition.acquisitionPrice = oldAcquisition.quantity * oldAcquisition.pricePerUnit

                    acqs.add(oldAcquisition)
                }

                acqs.add(newAcquisition)
            }

            if(localQuantity == 0.0)
                break
        }

        localItem.itemAcquisitions = acqs

        onItemUpdated(localItem)
    }

    private suspend fun moveFullPackage(item: StoredItem, quantity: Double, startStorage: Storage, destinationStorage: Storage) {
        var localQuantity = quantity
        val localItem: StoredItem = item
        val acqs = mutableListOf<ItemAcquisition>()

        for(acq in localItem.itemAcquisitions) {

            val newAcquisition: ItemAcquisition = acq.copy()
            val oldAcquisition: ItemAcquisition = acq.copy()
            val newAcqPackages = mutableListOf<Double>()
            val oldAcqPackages: MutableList<Double> = acq.packageCounts as MutableList<Double>

            if(localQuantity != 0.0 && acq.currentStorage == startStorage.id) {
                for(packages in acq.packageCounts.reversed()) {

                    if(packages == localQuantity) {
                        oldAcqPackages.remove(packages)
                        newAcqPackages.add(packages)
                        localQuantity = 0.0
                        break
                    }

                    else if(packages < localQuantity) {
                        oldAcqPackages.remove(packages)
                        newAcqPackages.add(packages)
                        localQuantity -= packages
                        continue
                    }

                    else if(packages > localQuantity) {
                        oldAcqPackages.remove(packages)
                        newAcqPackages.add(packages)
                        localQuantity = 0.0
                        break
                    }
                }

                newAcquisition.packageCounts = newAcqPackages
                newAcquisition.currentStorage = destinationStorage.id
                newAcquisition.acquisitionDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
                newAcquisition.quantity = newAcquisition.packageCounts.size.toDouble()
                newAcquisition.acquisitionPrice = newAcquisition.quantity * newAcquisition.pricePerUnit

                if(oldAcqPackages.isNotEmpty()) {
                    oldAcquisition.packageCounts = oldAcqPackages
                    oldAcquisition.quantity = oldAcquisition.packageCounts.size.toDouble()
                    oldAcquisition.acquisitionPrice = oldAcquisition.quantity * oldAcquisition.pricePerUnit

                    acqs.add(oldAcquisition)
                }

                acqs.add(newAcquisition)
            }
            else {
                acqs.add(oldAcquisition)
            }
        }

        localItem.itemAcquisitions = acqs

        onItemUpdated(localItem)
    }

    private suspend fun moveItemPiece(item: StoredItem, quantity: Double, startStorage: Storage, destinationStorage: Storage) {
        var localQuantity = quantity
        val localItem: StoredItem = item
        val acqs = mutableListOf<ItemAcquisition>()

        for(acq in localItem.itemAcquisitions) {

            val newAcquisition: ItemAcquisition = acq.copy()
            val oldAcquisition: ItemAcquisition = acq.copy()
            var newQuantity: Double = 0.0
            var oldQuantity: Double = acq.quantity

            if(localQuantity != 0.0 && acq.currentStorage == startStorage.id) {
                if(acq.quantity == localQuantity) {
                    oldQuantity = 0.0
                    newQuantity += localQuantity
                    localQuantity = 0.0
                }

                else if(acq.quantity < localQuantity) {
                    localQuantity -= oldQuantity
                    oldQuantity = 0.0
                    newQuantity += localQuantity
                }

                else if(acq.quantity > localQuantity) {
                    oldQuantity -= localQuantity
                    newQuantity += localQuantity
                    localQuantity = 0.0
                }

                newAcquisition.quantity = newQuantity
                newAcquisition.packageCounts = mutableListOf(newQuantity)
                newAcquisition.currentStorage = destinationStorage.id
                newAcquisition.acquisitionDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
                newAcquisition.acquisitionPrice = newAcquisition.quantity * newAcquisition.pricePerUnit

                if(oldQuantity != 0.0) {
                    oldAcquisition.quantity = oldQuantity
                    oldAcquisition.packageCounts = mutableListOf(oldQuantity)
                    oldAcquisition.acquisitionPrice = oldAcquisition.quantity * oldAcquisition.pricePerUnit

                    acqs.add(oldAcquisition)
                }
                acqs.add(newAcquisition)
            }
            else {
                acqs.add(oldAcquisition)
            }
        }

        localItem.itemAcquisitions = acqs

        onItemUpdated(localItem)
    }
}