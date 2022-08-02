package com.example.raktarappjustui1.ui.pages.list.acquisition

import com.example.raktarappjustui1.data.Item

sealed class AcquisitionViewState

object Loading : AcquisitionViewState()

data class AcquisitionContent (
    var item: Item? = null,
    var isLoading: Boolean = true
) : AcquisitionViewState()
