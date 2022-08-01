package com.example.raktarappjustui1.ui.nav

import com.example.raktarappjustui1.ui.pages.list.ListFragment

sealed class NavViewState

object Loading : NavViewState()

data class NavContent(
    var isLoading: Boolean = true
) : NavViewState()
