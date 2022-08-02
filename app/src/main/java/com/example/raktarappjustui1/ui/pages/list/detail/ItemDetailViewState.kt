package com.example.raktarappjustui1.ui.pages.list.detail

import com.example.raktarappjustui1.data.Item

sealed class ItemDetailViewState

object Loading : ItemDetailViewState()

data class ItemDetailLoaded(
    var item: Item? = null,
    var isLoading: Boolean = true
) : ItemDetailViewState()
