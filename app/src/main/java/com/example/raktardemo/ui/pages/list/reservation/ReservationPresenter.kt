package com.example.raktardemo.ui.pages.list.reservation

import co.zsmb.rainbowcake.withIOContext
import com.example.raktardemo.data.model.Reservation
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.domain.DatabaseInteractor
import javax.inject.Inject

class ReservationPresenter @Inject constructor(
    private val databaseInteractor: DatabaseInteractor
) {

    suspend fun onReservation(reservation: Reservation, item: StoredItem?, acqId: String?, group: List<StoredItem>): Unit = withIOContext {
        databaseInteractor.onReservation(reservation, item, acqId, group)
    }
}
