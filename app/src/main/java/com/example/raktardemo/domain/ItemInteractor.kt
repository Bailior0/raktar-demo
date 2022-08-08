package com.example.raktardemo.domain

import com.example.raktardemo.data.datasource.FirebaseDataSource
import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.StoredItem
import javax.inject.Inject

class DatabaseInteractor @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) {

    suspend fun getItems(): List<StoredItem> {
        return firebaseDataSource.getItems()
    }

    suspend fun getStorages(): List<Storage> {
        return firebaseDataSource.getStorages()
    }

    suspend fun onItemAdded(item: StoredItem) {
        firebaseDataSource.addItem(item)
    }
}