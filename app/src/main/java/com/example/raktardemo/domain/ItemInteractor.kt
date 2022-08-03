package com.example.raktardemo.domain

import com.example.raktardemo.data.datasource.FirebaseDataSource
import com.example.raktardemo.data.model.StoredItem
import javax.inject.Inject

class DatabaseInteractor @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) {

    suspend fun getItems(): List<StoredItem> {
        return firebaseDataSource.getItems()
    }
}