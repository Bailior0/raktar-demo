package com.example.raktarappjustui1.ui.pages.list.release

import com.example.raktarappjustui1.data.Group
import com.example.raktarappjustui1.data.Item

sealed class ReleaseViewState

object Loading : ReleaseViewState()

data class ReleaseContent (
    var item: Item? = null,
    var isLoading: Boolean = true
) : ReleaseViewState()

data class ReleaseGroupContent (
    var group: Group? = null,
    var isLoading: Boolean = true
) : ReleaseViewState()
