package com.example.raktarappjustui1.ui.pages.list

import co.zsmb.rainbowcake.base.RainbowCakeViewModel

class ListViewModel : RainbowCakeViewModel<ListViewState>(Loading) {

    fun setList() {
        viewState = ListContent(false)
    }
}
