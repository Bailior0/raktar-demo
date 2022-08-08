package com.example.raktardemo.ui.pages.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.extensions.exhaustive
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.navigator
import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.Worker
import com.example.raktardemo.ui.pages.account.storage.StorageFragment
import com.example.raktardemo.ui.views.Account
import com.example.raktardemo.ui.views.helpers.FullScreenLoading
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : RainbowCakeFragment<AccountViewState, AccountViewModel>() {
    override fun provideViewModel() = getViewModelFromFactory()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                FullScreenLoading()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setImport()
    }

    override fun render(viewState: AccountViewState) {
        (view as ComposeView).setContent {
            when (viewState) {
                is Loading -> FullScreenLoading()
                is AccountContent -> {
                    val worker = Worker(
                        id = "0",
                        name = "Raktáros Réka",
                        email = "raktaros.reka@raktar.hu",
                        phoneNumber = "+36 10 111-1111"
                    )

                    Account(
                        worker = worker,
                        storageList = viewState.storages,
                        onClicked = ::onStorageSelected,
                        onLogoutClick = ::onLogout
                    )
                }
            }.exhaustive
        }
    }

    private fun onStorageSelected(storage: Storage) {
        navigator?.add(StorageFragment.newInstance(storage))
    }

    private fun onLogout() {
        //TODO
    }
}