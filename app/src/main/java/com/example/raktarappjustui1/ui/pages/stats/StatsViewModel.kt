package com.example.raktarappjustui1.ui.pages.stats

import co.zsmb.rainbowcake.base.RainbowCakeViewModel

class StatsViewModel : RainbowCakeViewModel<StatsViewState>(Loading) {

    fun setStats() {
        viewState = StatsContent(false)
    }
}
