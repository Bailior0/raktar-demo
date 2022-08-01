package com.example.raktarappjustui1.ui.pages.account

import co.zsmb.rainbowcake.base.RainbowCakeViewModel

class AccountViewModel : RainbowCakeViewModel<AccountViewState>(Loading) {

    fun setImport() {
        viewState = AccountContent(false)
    }
}
