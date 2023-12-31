package com.example.raktardemo.ui.pages.list.release

import co.zsmb.rainbowcake.withIOContext
import com.example.raktardemo.data.model.Release
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.domain.DatabaseInteractor
import javax.inject.Inject

class ReleasePresenter @Inject constructor(
    private val databaseInteractor: DatabaseInteractor
) {

    suspend fun onRelease(release: Release, item: StoredItem?, selectedAcqId: String, chosenOpenedPackages: List<Pair<String, Double>>, acqId: String?, group: List<StoredItem>): Unit = withIOContext {
        databaseInteractor.onRelease(release, item, selectedAcqId, chosenOpenedPackages, acqId, group)
    }
}
