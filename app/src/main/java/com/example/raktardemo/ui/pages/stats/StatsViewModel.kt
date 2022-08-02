package com.example.raktardemo.ui.pages.stats

import co.zsmb.rainbowcake.base.RainbowCakeViewModel

class StatsViewModel : RainbowCakeViewModel<StatsViewState>(Loading) {

    fun setStats() {
        viewState = StatsContent(false)
    }
}
