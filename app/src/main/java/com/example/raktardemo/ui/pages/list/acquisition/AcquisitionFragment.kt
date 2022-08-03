package com.example.raktardemo.ui.pages.list.acquisition

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
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.ui.views.Acquisition
import com.example.raktardemo.ui.views.helpers.FullScreenLoading
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AcquisitionFragment : RainbowCakeFragment<AcquisitionViewState, AcquisitionViewModel>() {
    override fun provideViewModel() = getViewModelFromFactory()

    companion object {
        private const val EXTRA_ITEM = "ITEM"


        fun newInstance(item: StoredItem): AcquisitionFragment {
            return AcquisitionFragment().applyArgs {
                putParcelable(EXTRA_ITEM, item)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel.setAcquisition(arguments?.getParcelable(EXTRA_ITEM)!!)

        return ComposeView(requireContext()).apply {
            setContent {
                FullScreenLoading()
            }
        }
    }

    override fun render(viewState: AcquisitionViewState) {
        (view as ComposeView).setContent {
            when (viewState) {
                is Loading -> FullScreenLoading()
                is AcquisitionContent -> Acquisition(
                    product = viewState.item!!,
                    onIconClick = { navigator?.pop() },
                    onAcquisitionClick = ::onAcquisition
                )
            }.exhaustive
        }
    }

    private fun onAcquisition() {
        //TODO
    }
}