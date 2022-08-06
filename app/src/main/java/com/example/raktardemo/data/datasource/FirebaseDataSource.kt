package com.example.raktardemo.data.datasource

import android.util.Log
import com.example.raktardemo.data.model.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseDataSource @Inject constructor() {

    private val database = Firebase.firestore

    suspend fun getItems(): List<StoredItem> {
        val items = mutableListOf<StoredItem>()
        database.collection("storedItems").get()
            .addOnSuccessListener { documents ->
                for(document in documents) {
                    items.add(document.toObject())
                }
            }
            .addOnFailureListener { exception ->
                Log.d("failure", "Error getting documents: ", exception)
            }
            .await()

        return items
    }

    suspend fun getStorages(): List<Storage> {
        val storages = mutableListOf<Storage>()
        database.collection("storages").get()
            .addOnSuccessListener { documents ->
                for(document in documents)
                    storages.add(document.toObject())
            }
            .addOnFailureListener { exception ->
                Log.d("failure", "Error getting documents: ", exception)
            }
            .await()

        return storages
    }


    /*suspend fun setDefaultItems() {
        val item = StoredItem(
            item = Item(
                id = "0",
                name = "piroskábel",
                category = Category("0", "kábel"),
                manufacturer = "ASDKábelKft",
                serialNumber = "123123",
                type = PackageType.Package.toString().lowercase(),
                quantityUnit = QuantityUnit.Meter.toString().lowercase(),
                defaultPackageQuantity = 305.0,
                openable = true,
                defaultPurchasePrice = 1.0,
                minimumStoredQuantity = 1.0
            ),
            itemAcquisitions = mutableListOf(
                ItemAcquisition(
                    id = "3",
                    acquisitionDate = "2022-06-30 10:00:39",
                    acquisitionWorker = "123",
                    expiryDate = "2022-10-30 10:00:39",
                    quantity = 5.0,
                    acquisitionPrice = 2135.0,
                    pricePerUnit = 7.0,
                    currentStorage = "mkw5CFraSEImpTJ99MhB",
                    ownedBy = Ownership.Own.toString().lowercase(),
                    packageCounts = mutableListOf(305.0, 34.0, 305.0, 20.0, 30.0),
                    reserved = emptyList(),
                    released = emptyList()
                )
            )
        )
        val item2 = StoredItem(
            item = Item(
                id = "1",
                name = "MacbookPro",
                category = Category("1", "laptop"),
                manufacturer = "Apple",
                serialNumber = "1234",
                type = PackageType.Piece.toString().lowercase(),
                quantityUnit = QuantityUnit.Piece.toString().lowercase(),
                defaultPackageQuantity = 1.0,
                openable = false,
                defaultPurchasePrice = 1600.0,
                minimumStoredQuantity = 1.0
            ),
            itemAcquisitions = mutableListOf(
                ItemAcquisition(
                    id = "0",
                    acquisitionDate = "2022-07-28 19:49:39",
                    acquisitionWorker = "123",
                    expiryDate = "2022-09-29 19:49:39",
                    quantity = 6.0,
                    acquisitionPrice = 4800.0,
                    pricePerUnit = 800.0,
                    currentStorage = "mkw5CFraSEImpTJ99MhB",
                    ownedBy = Ownership.Own.toString().lowercase(),
                    packageCounts = mutableListOf(1.0),
                    reserved = emptyList(),
                    released = emptyList()
                ),
                ItemAcquisition(
                    id = "0",
                    acquisitionDate = "2022-07-28 19:49:39",
                    acquisitionWorker = "123",
                    expiryDate = "2022-09-29 19:49:39",
                    quantity = 2.0,
                    acquisitionPrice = 1600.0,
                    pricePerUnit = 800.0,
                    currentStorage = "mkw5CFraSEImpTJ99MhB",
                    ownedBy = Ownership.Own.toString().lowercase(),
                    packageCounts = mutableListOf(1.0),
                    reserved = emptyList(),
                    released = emptyList()
                )
            )
        )
        database.collection("storedItems").add(item).await()
        database.collection("storedItems").add(item2).await()
    }*/
}