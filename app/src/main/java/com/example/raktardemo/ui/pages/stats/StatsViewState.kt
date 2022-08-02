package com.example.raktardemo.ui.pages.stats

sealed class StatsViewState

object Loading : StatsViewState()

data class StatsContent(
    var isLoading: Boolean = true
) : StatsViewState()
