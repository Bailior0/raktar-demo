package com.example.raktarappjustui1.ui.pages.list.moving

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
import com.example.raktarappjustui1.data.Item
import com.example.raktarappjustui1.ui.views.Moving
import com.example.raktarappjustui1.ui.views.helpers.FullScreenLoading
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovingFragment : RainbowCakeFragment<MovingViewState, MovingViewModel>() {
    override fun provideViewModel() = getViewModelFromFactory()

    companion object {
        private const val EXTRA_ITEM = "ITEM"


        fun newInstance(item: Item): MovingFragment {
            return MovingFragment().applyArgs {
                putParcelable(EXTRA_ITEM, item)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel.setMoving(arguments?.getParcelable(EXTRA_ITEM)!!)

        return ComposeView(requireContext()).apply {
            setContent {
                FullScreenLoading()
            }
        }
    }

    override fun render(viewState: MovingViewState) {
        (view as ComposeView).setContent {
            when (viewState) {
                is Loading -> FullScreenLoading()
                is MovingContent -> Moving(
                    item = viewState.item!!,
                    onIconClick = { navigator?.pop() }
                )
            }.exhaustive
        }
    }
}