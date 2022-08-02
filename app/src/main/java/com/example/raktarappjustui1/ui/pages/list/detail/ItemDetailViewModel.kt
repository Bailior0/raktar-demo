package com.example.raktarappjustui1.ui.pages.list.detail

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktarappjustui1.data.Item

class ItemDetailViewModel : RainbowCakeViewModel<ItemDetailViewState>(Loading) {

    fun setDetail(item: Item) {
        viewState = ItemDetailLoaded(item, false)
    }
}
