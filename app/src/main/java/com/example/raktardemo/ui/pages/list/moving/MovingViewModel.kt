package com.example.raktardemo.ui.pages.list.moving

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktardemo.data.enums.PackageState
import com.example.raktardemo.data.model.Moving
import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.StoredItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovingViewModel @Inject constructor(
    private val movingPresenter: MovingPresenter
) : RainbowCakeViewModel<MovingViewState>(Loading) {

    fun setMoving(item: StoredItem, storages: ArrayList<Storage>, presentStorages: ArrayList<Storage>) {
        viewState = MovingContent(item, storages, presentStorages, false)
    }

    fun onMoving(item: StoredItem, chosenOpenedPackages: List<Pair<String, Double>>, moving: Moving, packageState: PackageState?) = execute {
        movingPresenter.onMoving(item, chosenOpenedPackages, moving, packageState)
    }
}
