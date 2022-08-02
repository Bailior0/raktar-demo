package com.example.raktardemo.ui.pages.importitem

sealed class ImportViewState

object Loading : ImportViewState()

data class ImportContent(
    var isLoading: Boolean = true
) : ImportViewState()
