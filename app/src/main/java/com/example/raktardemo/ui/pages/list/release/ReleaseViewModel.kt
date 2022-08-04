package com.example.raktardemo.ui.pages.list.release

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktardemo.data.model.StoredItem

class ReleaseViewModel : RainbowCakeViewModel<ReleaseViewState>(Loading) {

    fun setRelease(item: StoredItem) {
        viewState = ReleaseContent(item, false)
    }

    fun setReleaseGroup(group: List<StoredItem>, acqId: String?) {
        viewState = ReleaseGroupContent(group, acqId, false)
    }
}
