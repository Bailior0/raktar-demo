package com.example.raktardemo.ui.pages.account

import co.zsmb.rainbowcake.withIOContext
import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.domain.DatabaseInteractor
import javax.inject.Inject

class AccountPresenter @Inject constructor(
    private val databaseInteractor: DatabaseInteractor
) {
    suspend fun getStorages(): List<Storage> = withIOContext {
        databaseInteractor.getStorages()
    }
}