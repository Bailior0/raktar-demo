package com.example.raktardemo.ui.pages.list.moving

import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.StoredItem

sealed class MovingViewState

object Loading : MovingViewState()

data class MovingContent (
    var item: StoredItem? = null,
    var storages: ArrayList<Storage> = emptyList<Storage>() as ArrayList<Storage>,
    var presentStorages: ArrayList<Storage> = emptyList<Storage>() as ArrayList<Storage>,
    var isLoading: Boolean = true
) : MovingViewState()
