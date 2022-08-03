package com.example.raktardemo.ui.pages.list

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktardemo.domain.DatabaseInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val databaseInteractor: DatabaseInteractor) : RainbowCakeViewModel<ListViewState>(Loading) {

    fun setList() = execute {
        viewState = ListContent(list = databaseInteractor.getItems(), isLoading = false)
    }
}
