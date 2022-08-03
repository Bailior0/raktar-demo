package com.example.raktardemo.ui.pages.list.reservation

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktardemo.data.model.StoredItem

class ReservationViewModel : RainbowCakeViewModel<ReservationViewState>(Loading) {

    fun setReservation(item: StoredItem) {
        viewState = ReservationContent(item, false)
    }
}
