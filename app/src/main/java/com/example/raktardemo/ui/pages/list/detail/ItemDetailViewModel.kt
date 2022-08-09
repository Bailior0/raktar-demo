package com.example.raktardemo.ui.pages.list.detail

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.StoredItem

class ItemDetailViewModel : RainbowCakeViewModel<ItemDetailViewState>(Loading) {

    fun setDetail(item: StoredItem, storages: ArrayList<Storage>) {
        viewState = ItemDetailLoaded(item, storages, false)
    }
}
