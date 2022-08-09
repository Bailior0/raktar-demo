package com.example.raktardemo.ui.pages.list.moving

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktardemo.data.enums.PackageState
import com.example.raktardemo.data.enums.PackageType
import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.ui.pages.importitem.ImportPresenter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovingViewModel @Inject constructor(
    private val movingPresenter: MovingPresenter
) : RainbowCakeViewModel<MovingViewState>(Loading) {

    fun setMoving(item: StoredItem, storages: ArrayList<Storage>, presentStorages: ArrayList<Storage>) {
        viewState = MovingContent(item, storages, presentStorages, false)
    }

    fun onMoving(item: StoredItem, quantity: Double, startStorage: Storage, destinationStorage: Storage, packageType: PackageType, packageState: PackageState?) = execute {
        movingPresenter.onMoving(item, quantity, startStorage,destinationStorage, packageType, packageState)
    }
}
