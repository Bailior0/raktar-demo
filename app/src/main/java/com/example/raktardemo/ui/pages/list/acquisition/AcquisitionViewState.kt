package com.example.raktardemo.ui.pages.list.acquisition

import com.example.raktardemo.data.model.Item

sealed class AcquisitionViewState

object Loading : AcquisitionViewState()

data class AcquisitionContent (
    var item: Item? = null,
    var isLoading: Boolean = true
) : AcquisitionViewState()
