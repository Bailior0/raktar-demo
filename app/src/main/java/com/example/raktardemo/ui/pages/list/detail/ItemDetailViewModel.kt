package com.example.raktardemo.ui.pages.list.detail

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktardemo.data.model.StoredItem

class ItemDetailViewModel : RainbowCakeViewModel<ItemDetailViewState>(Loading) {

    fun setDetail(item: StoredItem) {
        viewState = ItemDetailLoaded(item, false)
    }
}
