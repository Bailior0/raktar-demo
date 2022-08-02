package com.example.raktarappjustui1.ui.pages.list.reservation

import com.example.raktarappjustui1.data.Item

sealed class ReservationViewState

object Loading : ReservationViewState()

data class ReservationContent (
    var item: Item? = null,
    var isLoading: Boolean = true
) : ReservationViewState()
