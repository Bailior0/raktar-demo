package com.example.raktarappjustui1.ui.pages.list.moving

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktarappjustui1.data.Item

class MovingViewModel : RainbowCakeViewModel<MovingViewState>(Loading) {

    fun setMoving(item: Item) {
        viewState = MovingContent(item, false)
    }
}
