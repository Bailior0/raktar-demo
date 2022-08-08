package com.example.raktardemo.ui.pages.importitem

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.domain.DatabaseInteractor
import com.example.raktardemo.ui.pages.list.ListPresenter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImportViewModel @Inject constructor(
    private val importPresenter: ImportPresenter
) : RainbowCakeViewModel<ImportViewState>(Loading) {

    fun setImport() = execute {
        val storages = importPresenter.getStorages()

        viewState = ImportContent(
            storages = storages,
            isLoading = false
        )
    }

    /*fun setImport() = execute {
        viewState = ImportContent(storages = databaseInteractor.getStorages(), isLoading = false)
    }*/

    fun onItemAdded(item: StoredItem) = execute {
        importPresenter.onItemAdd(item)
    }

    /*fun onItemAdded(item: StoredItem) = execute {
        databaseInteractor.onItemAdded(item)
    }*/
}
