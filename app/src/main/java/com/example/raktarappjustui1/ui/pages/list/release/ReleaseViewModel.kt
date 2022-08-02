package com.example.raktarappjustui1.ui.pages.list.release

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktarappjustui1.data.Group
import com.example.raktarappjustui1.data.Item

class ReleaseViewModel : RainbowCakeViewModel<ReleaseViewState>(Loading) {

    fun setRelease(item: Item) {
        viewState = ReleaseContent(item, false)
    }

    fun setReleaseGroup(group: Group) {
        viewState = ReleaseGroupContent(group, false)
    }
}
