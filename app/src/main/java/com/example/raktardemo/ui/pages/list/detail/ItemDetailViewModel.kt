package com.example.raktardemo.ui.pages.list.detail

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktardemo.data.model.Item

class ItemDetailViewModel : RainbowCakeViewModel<ItemDetailViewState>(Loading) {

    fun setDetail(item: Item) {
        viewState = ItemDetailLoaded(item, false)
    }
}
