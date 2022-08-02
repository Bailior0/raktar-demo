package com.example.raktardemo.ui.pages.list

import co.zsmb.rainbowcake.base.RainbowCakeViewModel

class ListViewModel : RainbowCakeViewModel<ListViewState>(Loading) {

    fun setList() {
        viewState = ListContent(false)
    }
}
