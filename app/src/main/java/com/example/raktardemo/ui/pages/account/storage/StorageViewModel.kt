package com.example.raktardemo.ui.pages.account.storage

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktardemo.data.Storage

class StorageViewModel : RainbowCakeViewModel<StorageViewState>(Loading) {

    fun setStorage(storage: Storage) {
        viewState = StorageLoaded(storage, false)
    }
}