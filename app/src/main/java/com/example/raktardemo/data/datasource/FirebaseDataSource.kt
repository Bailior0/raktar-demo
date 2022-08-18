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
                for(item in items) {
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
                }
                this.trySend(items).isSuccess
            }
        awaitClose {
            Log.d("failure", "Cancelling items listener")
            listenerRegistration.remove()
        }
    }

    suspend fun getItemsOnce(): List<StoredItem> {
        val items = mutableListOf<StoredItem>()
        database.collection("storedItems").get()
            .addOnSuccessListener { documents ->
                for(document in documents)
                    items.add(document.toObject())
            }
            .addOnFailureListener { exception ->
                Log.d("failure", "Error getting documents: ", exception)
            }
            .await()

        return items
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
}