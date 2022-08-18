package com.example.raktardemo.ui.pages.list.moving

import co.zsmb.rainbowcake.withIOContext
import com.example.raktardemo.data.enums.PackageState
import com.example.raktardemo.data.model.Moving
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.domain.DatabaseInteractor
import javax.inject.Inject

class MovingPresenter @Inject constructor(
    private val databaseInteractor: DatabaseInteractor
) {

    suspend fun onMoving(item: StoredItem, chosenOpenedPackages: List<Pair<String, Double>>, moving: Moving, packageState: PackageState?): Unit = withIOContext {
        databaseInteractor.onMoving(item, chosenOpenedPackages, moving, packageState)
    }
}
