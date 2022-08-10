package com.example.raktardemo.ui.pages.stats

import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.StoredItem

sealed class StatsViewState

object Loading : StatsViewState()

data class StatsContent(
    var items: List<StoredItem> = emptyList(),
    var storages: List<Storage> = emptyList(),
    var isLoading: Boolean = true
) : StatsViewState()
