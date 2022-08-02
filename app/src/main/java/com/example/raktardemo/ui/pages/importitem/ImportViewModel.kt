package com.example.raktardemo.ui.pages.importitem

import co.zsmb.rainbowcake.base.RainbowCakeViewModel

class ImportViewModel : RainbowCakeViewModel<ImportViewState>(Loading) {

    fun setImport() {
        viewState = ImportContent(false)
    }
}
