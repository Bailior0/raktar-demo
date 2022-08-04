package com.example.raktardemo.ui.pages.list.reservation

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.ui.pages.list.release.ReleaseGroupContent

class ReservationViewModel : RainbowCakeViewModel<ReservationViewState>(Loading) {

    fun setReservation(item: StoredItem) {
        viewState = ReservationContent(item, false)
    }

    fun setReservationGroup(group: List<StoredItem>, acqId: String?) {
        viewState = ReservationGroupContent(group, acqId, false)
    }
}
