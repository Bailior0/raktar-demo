package com.example.raktardemo.ui.pages.list.detail

import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.StoredItem

sealed class ItemDetailViewState

object Loading : ItemDetailViewState()

data class ItemDetailLoaded(
    var item: StoredItem? = null,
    var items: ArrayList<StoredItem> = emptyList<StoredItem>() as ArrayList<StoredItem>,
    var storages: ArrayList<Storage> = emptyList<Storage>() as ArrayList<Storage>,
    var presentStorages: ArrayList<Storage> = emptyList<Storage>() as ArrayList<Storage>,
    var isLoading: Boolean = true
) : ItemDetailViewState()
