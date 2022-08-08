package com.example.raktardemo.ui.pages.list

import co.zsmb.rainbowcake.withIOContext
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.domain.DatabaseInteractor
import javax.inject.Inject

class ListPresenter @Inject constructor(
    private val databaseInteractor: DatabaseInteractor
) {
    suspend fun getItems(): List<StoredItem> = withIOContext {
        databaseInteractor.getItems()
    }
}