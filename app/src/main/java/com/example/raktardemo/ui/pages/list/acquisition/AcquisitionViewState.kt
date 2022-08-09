package com.example.raktardemo.ui.pages.list.acquisition

import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.StoredItem

sealed class AcquisitionViewState

object Loading : AcquisitionViewState()

data class AcquisitionContent (
    var item: StoredItem? = null,
    var storages: List<Storage> = emptyList(),
    var isLoading: Boolean = true
) : AcquisitionViewState()
