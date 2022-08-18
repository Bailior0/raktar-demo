package com.example.raktardemo.ui.pages.importitem

import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.StoredItem

sealed class ImportViewState

object Loading : ImportViewState()

data class ImportContent(
    var storages: List<Storage> = emptyList(),
    var products: List<StoredItem> = emptyList(),
    var isLoading: Boolean = true
) : ImportViewState()
