package com.example.raktardemo.ui.pages.account.storage

import com.example.raktardemo.data.Storage

sealed class StorageViewState

object Loading : StorageViewState()

data class StorageLoaded(
    var storage: Storage? = null,
    var isLoading: Boolean = true
) : StorageViewState()