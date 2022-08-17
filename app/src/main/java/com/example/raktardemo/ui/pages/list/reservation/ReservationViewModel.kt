package com.example.raktardemo.ui.pages.list.reservation

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktardemo.data.model.Reservation
import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.StoredItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor(
    private val reservationPresenter: ReservationPresenter
) : RainbowCakeViewModel<ReservationViewState>(Loading) {

    fun setReservation(item: StoredItem, storages: ArrayList<Storage>) {
        viewState = ReservationContent(item, storages, false)
    }

/*    fun setReservationGroup(group: List<StoredItem>, acqId: String?) {
        viewState = ReservationGroupContent(group, acqId, false)
    }*/

    fun onReservation(reservation: Reservation, item: StoredItem?, acqId: String?, group: List<StoredItem>) = execute {
        reservationPresenter.onReservation(reservation, item, acqId, group)
    }
}
