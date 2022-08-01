package com.example.raktarappjustui1.ui.nav

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktarappjustui1.ui.pages.list.ListFragment

class NavViewModel : RainbowCakeViewModel<NavViewState>(Loading) {

    fun setNav() = execute {
        viewState = NavContent(false)
    }
}
