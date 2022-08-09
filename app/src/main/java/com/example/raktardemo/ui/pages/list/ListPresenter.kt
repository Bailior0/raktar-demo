package com.example.raktardemo.ui.pages.list

import co.zsmb.rainbowcake.withIOContext
import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.domain.DatabaseInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListPresenter @Inject constructor(
    private val databaseInteractor: DatabaseInteractor
) {
    suspend fun getItems(): Flow<List<StoredItem>> = withIOContext {
        databaseInteractor.getItems()
    }

    suspend fun getStorages(): List<Storage> = withIOContext {
        databaseInteractor.getStorages()
    }
}