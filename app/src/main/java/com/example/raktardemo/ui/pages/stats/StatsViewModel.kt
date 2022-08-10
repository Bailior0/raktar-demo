package com.example.raktardemo.ui.pages.stats

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val statsPresenter: StatsPresenter
) : RainbowCakeViewModel<StatsViewState>(Loading) {

    fun setStats() = execute {
        val storages = statsPresenter.getStorages()

        statsPresenter.getItems().collect {
            viewState = StatsContent(
                items = it,
                storages = storages,
                isLoading = false
                )
        }
    }
}
