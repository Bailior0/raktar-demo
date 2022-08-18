package com.example.raktardemo.ui.pages.account

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountPresenter: AccountPresenter
) : RainbowCakeViewModel<AccountViewState>(Loading) {

    fun setImport() = execute {
        val storages = accountPresenter.getStorages()

        viewState = AccountContent(
            storages = storages,
            isLoading = false
        )
    }
}
