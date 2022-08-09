package com.example.raktardemo.ui.pages.list.acquisition

import co.zsmb.rainbowcake.withIOContext
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.domain.DatabaseInteractor
import javax.inject.Inject

class AcquisitionPresenter @Inject constructor(
    private val databaseInteractor: DatabaseInteractor
)  {
    suspend fun onAcquisition(item: StoredItem): Unit = withIOContext {
        databaseInteractor.onItemUpdated(item)
    }
}
