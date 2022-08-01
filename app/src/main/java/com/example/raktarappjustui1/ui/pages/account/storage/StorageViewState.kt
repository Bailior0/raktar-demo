package com.example.raktarappjustui1.ui.pages.account.storage

import com.example.raktarappjustui1.data.Storage

sealed class StorageViewState

object Loading : StorageViewState()

data class StorageLoaded(
    var storage: Storage? = null,
    var isLoading: Boolean = true
) : StorageViewState()