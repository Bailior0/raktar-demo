package com.example.raktardemo.ui.pages.list.moving

import com.example.raktardemo.data.model.StoredItem

sealed class MovingViewState

object Loading : MovingViewState()

data class MovingContent (
    var item: StoredItem? = null,
    var isLoading: Boolean = true
) : MovingViewState()
