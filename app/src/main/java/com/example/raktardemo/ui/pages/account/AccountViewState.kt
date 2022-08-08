package com.example.raktardemo.ui.pages.account

import com.example.raktardemo.data.model.Storage

sealed class AccountViewState

object Loading : AccountViewState()

data class AccountContent(
    var storages: List<Storage> = emptyList(),
    var isLoading: Boolean = true
) : AccountViewState()
