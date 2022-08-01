package com.example.raktarappjustui1.ui.pages.importitem

import co.zsmb.rainbowcake.base.RainbowCakeViewModel

class ImportViewModel : RainbowCakeViewModel<ImportViewState>(Loading) {

    fun setImport() {
        viewState = ImportContent(false)
    }
}
