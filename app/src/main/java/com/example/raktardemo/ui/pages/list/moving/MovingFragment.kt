package com.example.raktardemo.ui.pages.list.moving

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.extensions.exhaustive
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.extensions.applyArgs
import co.zsmb.rainbowcake.navigation.navigator
import com.example.raktardemo.data.enums.PackageState
import com.example.raktardemo.data.model.Moving
import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.ui.views.Moving
import com.example.raktardemo.ui.views.helpers.FullScreenLoading
import com.example.raktardemo.ui.views.theme.RaktarAppJustUi1Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovingFragment : RainbowCakeFragment<MovingViewState, MovingViewModel>() {
    override fun provideViewModel() = getViewModelFromFactory()

    companion object {
        private const val EXTRA_ITEM = "ITEM"
        private const val EXTRA_STORAGES = "STORAGES"
        private const val EXTRA_PRESENT_STORAGES = "PRESENT_STORAGES"

        fun newInstance(item: StoredItem, storages: ArrayList<Storage>, presentStorages: ArrayList<Storage>): MovingFragment {
            return MovingFragment().applyArgs {
                putParcelable(EXTRA_ITEM, item)
                putParcelableArrayList(EXTRA_STORAGES, storages)
                putParcelableArrayList(EXTRA_PRESENT_STORAGES, presentStorages)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel.setMoving(
            arguments?.getParcelable(EXTRA_ITEM)!!,
            arguments?.getParcelableArrayList(EXTRA_STORAGES)!!,
            arguments?.getParcelableArrayList(EXTRA_PRESENT_STORAGES)!!
        )

        return ComposeView(requireContext()).apply {
            setContent {
                FullScreenLoading()
            }
        }
    }

    override fun render(viewState: MovingViewState) {
        (view as ComposeView).setContent {
            RaktarAppJustUi1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    when (viewState) {
                        is Loading -> FullScreenLoading()
                        is MovingContent -> Moving(
                            product = viewState.item!!,
                            storages = viewState.storages,
                            presentStorages = viewState.presentStorages,
                            onIconClick = { navigator?.pop() },
                            onMovingClick = ::onMoving
                        )
                    }.exhaustive
                }
            }
        }
    }

    private fun onMoving(item: StoredItem, chosenOpenedPackages: List<Pair<String, Double>>, moving: Moving, packageState: PackageState?) {
        viewModel.onMoving(item, chosenOpenedPackages, moving, packageState)
        navigator?.pop()
    }
}