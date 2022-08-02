package com.example.raktardemo.ui.pages.list.reservation

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktardemo.data.model.Item

class ReservationViewModel : RainbowCakeViewModel<ReservationViewState>(Loading) {

    fun setReservation(item: Item) {
        viewState = ReservationContent(item, false)
    }
}
