package com.example.raktardemo.ui.pages.list.acquisition

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktardemo.data.model.StoredItem

class AcquisitionViewModel : RainbowCakeViewModel<AcquisitionViewState>(Loading) {

    fun setAcquisition(item: StoredItem) {
        viewState = AcquisitionContent(item, false)
    }
}
