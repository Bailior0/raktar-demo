package com.example.raktardemo.ui.pages.list.moving

import com.example.raktardemo.data.Item

sealed class MovingViewState

object Loading : MovingViewState()

data class MovingContent (
    var item: Item? = null,
    var isLoading: Boolean = true
) : MovingViewState()
