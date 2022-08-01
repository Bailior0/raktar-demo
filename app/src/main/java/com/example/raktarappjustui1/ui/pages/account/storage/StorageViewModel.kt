package com.example.raktarappjustui1.ui.pages.account.storage

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktarappjustui1.data.Storage

class StorageViewModel : RainbowCakeViewModel<StorageViewState>(Loading) {

    fun setStorage(storage: Storage) {
        viewState = StorageLoaded(storage, false)
    }
}