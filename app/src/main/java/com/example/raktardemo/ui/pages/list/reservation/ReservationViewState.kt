package com.example.raktardemo.ui.pages.list.reservation

import com.example.raktardemo.data.model.Item

sealed class ReservationViewState

object Loading : ReservationViewState()

data class ReservationContent (
    var item: Item? = null,
    var isLoading: Boolean = true
) : ReservationViewState()
