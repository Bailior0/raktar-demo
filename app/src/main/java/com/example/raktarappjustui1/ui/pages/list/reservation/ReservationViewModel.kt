package com.example.raktarappjustui1.ui.pages.list.reservation

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktarappjustui1.data.Item

class ReservationViewModel : RainbowCakeViewModel<ReservationViewState>(Loading) {

    fun setReservation(item: Item) {
        viewState = ReservationContent(item, false)
    }
}
