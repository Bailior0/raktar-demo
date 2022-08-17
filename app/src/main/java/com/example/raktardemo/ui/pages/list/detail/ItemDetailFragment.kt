package com.example.raktardemo.ui.pages.list.detail

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
import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.ui.nav.NavFragment
import com.example.raktardemo.ui.pages.list.acquisition.AcquisitionFragment
import com.example.raktardemo.ui.pages.list.moving.MovingFragment
import com.example.raktardemo.ui.pages.list.release.ReleaseFragment
import com.example.raktardemo.ui.pages.list.reservation.ReservationFragment
import com.example.raktardemo.ui.views.ProductDetail
import com.example.raktardemo.ui.views.helpers.FullScreenLoading
import com.example.raktardemo.ui.views.theme.RaktarAppJustUi1Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemDetailFragment : RainbowCakeFragment<ItemDetailViewState, ItemDetailViewModel>() {
    override fun provideViewModel() = getViewModelFromFactory()

    companion object {
        private const val EXTRA_ITEM = "ITEM"
        private const val EXTRA_STORAGES = "STORAGES"
        private const val EXTRA_PRESENT_STORAGES = "PRESENT_STORAGES"

        fun newInstance(item: StoredItem, storages: ArrayList<Storage>, presentStorages: ArrayList<Storage>): ItemDetailFragment {
            return ItemDetailFragment().applyArgs {
                putParcelable(EXTRA_ITEM, item)
                putParcelableArrayList(EXTRA_STORAGES, storages)
                putParcelableArrayList(EXTRA_PRESENT_STORAGES, presentStorages)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel.setDetail(
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

    override fun render(viewState: ItemDetailViewState) {
        (view as ComposeView).setContent {
            RaktarAppJustUi1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    when (viewState) {
                        is Loading -> FullScreenLoading()
                        is ItemDetailLoaded -> ProductDetail(
                            product = viewState.item!!,
                            onIconClick = { navigator?.popUntil(NavFragment::class) },
                            onReservationClick = { onReservationSelected(viewState.item!!, viewState.storages) },
                            onAcquisitionClick = { onAcquisitionSelected(viewState.item!!, viewState.storages) },
                            onReleaseClick = { onReleaseSelected(viewState.item!!) },
                            onMovingClick = { onMovingSelected(viewState.item!!, viewState.storages, viewState.presentStorages) }
                        )
                    }.exhaustive
                }
            }
        }
    }

    private fun onReservationSelected(item: StoredItem, storages: ArrayList<Storage>) {
        navigator?.add(ReservationFragment.newInstance(item, storages))
    }

    private fun onAcquisitionSelected(item: StoredItem, storages: ArrayList<Storage>) {
        navigator?.add(AcquisitionFragment.newInstance(item, storages))
    }

    private fun onReleaseSelected(item: StoredItem) {
        navigator?.add(ReleaseFragment.newInstance(item))
    }

    private fun onMovingSelected(
        item: StoredItem,
        storages: ArrayList<Storage>,
        presentStorages: ArrayList<Storage>
    ) {
        navigator?.add(MovingFragment.newInstance(item, storages, presentStorages))
    }
}