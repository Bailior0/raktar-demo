package com.example.raktardemo.ui.nav

sealed class NavViewState

object Loading : NavViewState()

data class NavContent(
    var isLoading: Boolean = true
) : NavViewState()
