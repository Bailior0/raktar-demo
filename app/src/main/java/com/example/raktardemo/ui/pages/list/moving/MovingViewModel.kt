package com.example.raktardemo.ui.pages.list.moving

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktardemo.data.model.StoredItem

class MovingViewModel : RainbowCakeViewModel<MovingViewState>(Loading) {

    fun setMoving(item: StoredItem) {
        viewState = MovingContent(item, false)
    }
}
