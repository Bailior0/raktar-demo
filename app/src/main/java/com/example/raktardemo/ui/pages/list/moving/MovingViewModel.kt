package com.example.raktardemo.ui.pages.list.moving

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktardemo.data.model.Item

class MovingViewModel : RainbowCakeViewModel<MovingViewState>(Loading) {

    fun setMoving(item: Item) {
        viewState = MovingContent(item, false)
    }
}
