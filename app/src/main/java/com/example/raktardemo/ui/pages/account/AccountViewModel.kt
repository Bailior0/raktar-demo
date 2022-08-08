package com.example.raktardemo.ui.pages.account

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktardemo.domain.DatabaseInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val databaseInteractor: DatabaseInteractor) : RainbowCakeViewModel<AccountViewState>(Loading) {

    fun setImport() = execute {
        viewState = AccountContent(storages = databaseInteractor.getStorages(), isLoading = false)
    }
}
