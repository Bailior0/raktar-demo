package com.example.raktardemo.ui.pages.list.acquisition

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktardemo.data.model.Item

class AcquisitionViewModel : RainbowCakeViewModel<AcquisitionViewState>(Loading) {

    fun setAcquisition(item: Item) {
        viewState = AcquisitionContent(item, false)
    }
}
