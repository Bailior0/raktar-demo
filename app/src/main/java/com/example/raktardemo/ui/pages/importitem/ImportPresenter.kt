package com.example.raktardemo.ui.pages.importitem

import co.zsmb.rainbowcake.withIOContext
import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.domain.DatabaseInteractor
import javax.inject.Inject

class ImportPresenter @Inject constructor(
    private val databaseInteractor: DatabaseInteractor
) {
    suspend fun onItemAdd(item: StoredItem): Unit = withIOContext {
        databaseInteractor.onItemAdded(item)
    }

    suspend fun getStorages(): List<Storage> = withIOContext {
        databaseInteractor.getStorages()
    }
}