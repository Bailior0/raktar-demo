package com.example.raktarappjustui1.ui.pages.importitem

sealed class ImportViewState

object Loading : ImportViewState()

data class ImportContent(
    var isLoading: Boolean = true
) : ImportViewState()
