package com.example.raktarappjustui1.ui.pages.list.moving

import com.example.raktarappjustui1.data.Item

sealed class MovingViewState

object Loading : MovingViewState()

data class MovingContent (
    var item: Item? = null,
    var isLoading: Boolean = true
) : MovingViewState()
