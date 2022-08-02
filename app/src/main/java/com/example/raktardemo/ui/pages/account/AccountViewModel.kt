package com.example.raktardemo.ui.pages.account

import co.zsmb.rainbowcake.base.RainbowCakeViewModel

class AccountViewModel : RainbowCakeViewModel<AccountViewState>(Loading) {

    fun setImport() {
        viewState = AccountContent(false)
    }
}
