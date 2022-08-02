package com.example.raktarappjustui1.ui.pages.list.acquisition

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktarappjustui1.data.Item

class AcquisitionViewModel : RainbowCakeViewModel<AcquisitionViewState>(Loading) {

    fun setAcquisition(item: Item) {
        viewState = AcquisitionContent(item, false)
    }
}
