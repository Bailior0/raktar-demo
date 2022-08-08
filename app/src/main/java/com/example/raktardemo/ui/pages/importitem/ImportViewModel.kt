package com.example.raktardemo.ui.pages.importitem

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.domain.DatabaseInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImportViewModel @Inject constructor(private val databaseInteractor: DatabaseInteractor) : RainbowCakeViewModel<ImportViewState>(Loading) {

    fun setImport() = execute {
        viewState = ImportContent(storages = databaseInteractor.getStorages(), isLoading = false)
    }

    fun onItemAdded(item: StoredItem) = execute {
        databaseInteractor.onItemAdded(item)
    }
}
