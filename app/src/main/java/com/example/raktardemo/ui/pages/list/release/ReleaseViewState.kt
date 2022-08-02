package com.example.raktardemo.ui.pages.list.release

import com.example.raktardemo.data.model.Item

sealed class ReleaseViewState

object Loading : ReleaseViewState()

data class ReleaseContent (
    var item: Item? = null,
    var isLoading: Boolean = true
) : ReleaseViewState()

data class ReleaseGroupContent (
    var group: List<Item?> = emptyList(),
    var isLoading: Boolean = true
) : ReleaseViewState()
