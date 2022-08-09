package com.example.raktardemo.domain

import android.util.Log
import com.example.raktardemo.data.datasource.FirebaseDataSource
import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.StoredItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseInteractor @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) {

    suspend fun getItems(): Flow<List<StoredItem>> {
        return firebaseDataSource.getItems()
    }

    suspend fun getStorages(): List<Storage> {
        return firebaseDataSource.getStorages()
    }

    suspend fun onItemAdded(item: StoredItem) {
        firebaseDataSource.addItem(item)
    }

    suspend fun onItemUpdated(item: StoredItem) {
        firebaseDataSource.updateItem(item)
    }
}