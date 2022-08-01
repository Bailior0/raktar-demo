package com.example.raktarappjustui1.ui.pages.stats

sealed class StatsViewState

object Loading : StatsViewState()

data class StatsContent(
    var isLoading: Boolean = true
) : StatsViewState()
