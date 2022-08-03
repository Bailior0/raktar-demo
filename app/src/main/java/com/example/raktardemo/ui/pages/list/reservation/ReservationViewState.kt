package com.example.raktardemo.ui.pages.list.reservation

import com.example.raktardemo.data.model.StoredItem

sealed class ReservationViewState

object Loading : ReservationViewState()

data class ReservationContent (
    var item: StoredItem? = null,
    var isLoading: Boolean = true
) : ReservationViewState()
