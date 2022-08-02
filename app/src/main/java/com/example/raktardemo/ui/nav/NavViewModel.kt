package com.example.raktardemo.ui.nav

import co.zsmb.rainbowcake.base.RainbowCakeViewModel

class NavViewModel : RainbowCakeViewModel<NavViewState>(Loading) {

    fun setNav() {
        viewState = NavContent(false)
    }
}
