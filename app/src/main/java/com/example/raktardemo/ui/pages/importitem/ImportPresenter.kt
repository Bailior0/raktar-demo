package com.example.raktardemo.ui.pages.importitem

import android.util.Log
import co.zsmb.rainbowcake.withIOContext
import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.domain.DatabaseInteractor
import kotlinx.coroutines.flow.Flow
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

    suspend fun getItemsOnce(): List<StoredItem> = withIOContext {
        databaseInteractor.getItemsOnce()
    }
}