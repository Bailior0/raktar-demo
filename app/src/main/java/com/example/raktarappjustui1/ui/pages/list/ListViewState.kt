package com.example.raktarappjustui1.ui.pages.list

sealed class ListViewState

object Loading : ListViewState()

data class ListContent(
    var isLoading: Boolean = true
) : ListViewState()
