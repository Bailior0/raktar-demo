package com.example.raktardemo.ui.pages.list.release

import com.example.raktardemo.data.model.StoredItem

sealed class ReleaseViewState

object Loading : ReleaseViewState()

data class ReleaseContent (
    var item: StoredItem? = null,
    var isLoading: Boolean = true
) : ReleaseViewState()

data class ReleaseGroupContent (
    var group: List<StoredItem?> = emptyList(),
    var acqId: String? = null,
    var isLoading: Boolean = true
) : ReleaseViewState()
