package com.example.raktarappjustui1.ui.pages.account.storage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.extensions.exhaustive
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.extensions.applyArgs
import co.zsmb.rainbowcake.navigation.navigator
import com.example.raktarappjustui1.data.Storage
import com.example.raktarappjustui1.ui.nav.NavFragment
import com.example.raktarappjustui1.ui.views.StorageDetail
import com.example.raktarappjustui1.ui.views.helpers.FullScreenLoading
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StorageFragment : RainbowCakeFragment<StorageViewState, StorageViewModel>() {
    override fun provideViewModel() = getViewModelFromFactory()

    companion object {
        private const val EXTRA_STORAGE = "STORAGE"


        fun newInstance(storage: Storage): StorageFragment {
            return StorageFragment().applyArgs {
                putParcelable(EXTRA_STORAGE, storage)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel.setStorage(arguments?.getParcelable(EXTRA_STORAGE)!!)

        return ComposeView(requireContext()).apply {
            setContent {
                FullScreenLoading()
            }
        }
    }

    override fun render(viewState: StorageViewState) {
        (view as ComposeView).setContent {
            when (viewState) {
                is Loading -> FullScreenLoading()
                is StorageLoaded -> StorageDetail(
                    storage = viewState.storage!!,
                    onIconClick = { navigator?.popUntil(NavFragment::class) }
                )
            }.exhaustive
        }
    }
}