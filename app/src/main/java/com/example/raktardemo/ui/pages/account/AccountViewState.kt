package com.example.raktardemo.ui.pages.account

sealed class AccountViewState

object Loading : AccountViewState()

data class AccountContent(
    var isLoading: Boolean = true
) : AccountViewState()
