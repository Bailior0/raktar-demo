package com.example.raktardemo.ui.pages.list.release

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktardemo.data.Group
import com.example.raktardemo.data.Item

class ReleaseViewModel : RainbowCakeViewModel<ReleaseViewState>(Loading) {

    fun setRelease(item: Item) {
        viewState = ReleaseContent(item, false)
    }

    fun setReleaseGroup(group: Group) {
        viewState = ReleaseGroupContent(group, false)
    }
}
