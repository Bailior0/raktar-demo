package com.example.raktardemo.ui.pages.list.detail

import com.example.raktardemo.data.Item

sealed class ItemDetailViewState

object Loading : ItemDetailViewState()

data class ItemDetailLoaded(
    var item: Item? = null,
    var isLoading: Boolean = true
) : ItemDetailViewState()
