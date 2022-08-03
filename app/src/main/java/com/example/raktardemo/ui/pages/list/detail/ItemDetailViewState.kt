package com.example.raktardemo.ui.pages.list.detail

import com.example.raktardemo.data.model.StoredItem

sealed class ItemDetailViewState

object Loading : ItemDetailViewState()

data class ItemDetailLoaded(
    var item: StoredItem? = null,
    var isLoading: Boolean = true
) : ItemDetailViewState()
