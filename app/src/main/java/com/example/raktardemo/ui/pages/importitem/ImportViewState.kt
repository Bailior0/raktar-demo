package com.example.raktardemo.ui.pages.importitem

import com.example.raktardemo.data.model.Storage

sealed class ImportViewState

object Loading : ImportViewState()

data class ImportContent(
    var storages: List<Storage> = emptyList(),
    var isLoading: Boolean = true
) : ImportViewState()
