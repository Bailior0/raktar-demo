package com.example.raktardemo.data.datasource

import android.util.Log
import com.example.raktardemo.data.model.*
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseDataSource @Inject constructor() {

    private val database = Firebase.firestore

    suspend fun getItems(): Flow<List<StoredItem>> = callbackFlow {
        val listenerRegistration = database.collection("storedItems")
            .addSnapshotListener { querySnapshot: QuerySnapshot?, firebaseFirestoreException: FirebaseFirestoreException? ->
                if (firebaseFirestoreException != null) {
                    cancel(message = "Error fetching items", cause = firebaseFirestoreException)
                    return@addSnapshotListener
                }
                val items = mutableListOf<StoredItem>()
                if (querySnapshot != null) {
                    for(document in querySnapshot) {
                        items.add(document.toObject())
                    }
                }
                this.trySend(items).isSuccess
            }
        awaitClose {
            Log.d("failure", "Cancelling items listener")
            listenerRegistration.remove()
        }
    }

    suspend fun addItem(item: StoredItem) {
        database.collection("storedItems").add(item)
            .addOnSuccessListener { documentReference ->
                Log.d("success", "DocumentSnapshot written with ID: $documentReference.")
            }
            .addOnFailureListener { exception ->
                Log.d("failure", "Error getting documents: ", exception)
            }.await()
    }

    suspend fun updateItem(item: StoredItem) {
        database.collection("storedItems").document(item.id).set(item)
            .addOnSuccessListener { documentReference ->
                Log.d("success", "DocumentSnapshot written with ID: $documentReference.")
            }
            .addOnFailureListener { exception ->
                Log.d("failure", "Error getting documents: ", exception)
            }.await()
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

    suspend fun getAccount(accountId: String): Worker {
        var account = Worker()
        database.collection("accounts").document(accountId).get()
            .addOnSuccessListener { documentSnapshot  ->
                account = documentSnapshot.toObject()!!
            }
            .addOnFailureListener { exception ->
                Log.d("failure", "Error getting document: ", exception)
            }
            .await()

        return account
    }

    suspend fun addAccount(account: Worker) {
        database.collection("accounts").add(account)
            .addOnSuccessListener { documentReference ->
                Log.d("success", "DocumentSnapshot written with ID: $documentReference.")
            }
            .addOnFailureListener { exception ->
                Log.d("failure", "Error getting documents: ", exception)
            }.await()
    }

    /*suspend fun setDefaultItems() {
        val item = StoredItem(
            item = Item(
                id = "0",
                name = "piroskábel",
                category = Category("0", "kábel"),
                manufacturer = "ASDKábelKft",
                serialNumber = "123123",
                type = PackageType.Package,
                quantityUnit = QuantityUnit.Meter,
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
                    ownedBy = Ownership.Own,
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
                type = PackageType.Piece,
                quantityUnit = QuantityUnit.Piece,
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
                    ownedBy = Ownership.Own,
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
                    ownedBy = Ownership.Own,
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