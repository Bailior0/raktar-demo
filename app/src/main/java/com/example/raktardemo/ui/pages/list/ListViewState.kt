package com.example.raktardemo.ui.pages.list

import com.example.raktardemo.data.model.StoredItem

sealed class ListViewState

object Loading : ListViewState()

data class ListContent(
    var list: List<StoredItem> = emptyList(),
    var isLoading: Boolean = true
) : ListViewState()
