package com.example.raktarappjustui1.ui.nav

sealed class NavViewState

object Loading : NavViewState()

data class NavContent(
    var isLoading: Boolean = true
) : NavViewState()
