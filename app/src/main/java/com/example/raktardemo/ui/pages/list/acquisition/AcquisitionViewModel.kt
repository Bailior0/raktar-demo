package com.example.raktardemo.ui.pages.list.acquisition

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.StoredItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AcquisitionViewModel @Inject constructor(
    private val acquisitionPresenter: AcquisitionPresenter
) : RainbowCakeViewModel<AcquisitionViewState>(Loading) {

    fun setAcquisition(item: StoredItem, items: List<StoredItem>, storages: List<Storage>) {
        viewState = AcquisitionContent(item, items, storages, false)
    }

    fun onAcquisition(item: StoredItem) = execute  {
        acquisitionPresenter.onAcquisition(item)
    }
}
