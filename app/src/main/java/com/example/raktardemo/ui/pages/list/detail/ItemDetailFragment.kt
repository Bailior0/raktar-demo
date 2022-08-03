package com.example.raktardemo.ui.pages.list.detail

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
import com.example.raktardemo.ui.nav.NavFragment
import com.example.raktardemo.ui.pages.list.acquisition.AcquisitionFragment
import com.example.raktardemo.ui.pages.list.moving.MovingFragment
import com.example.raktardemo.ui.pages.list.release.ReleaseFragment
import com.example.raktardemo.ui.pages.list.reservation.ReservationFragment
import com.example.raktardemo.ui.views.ProductDetail
import com.example.raktardemo.ui.views.helpers.FullScreenLoading
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemDetailFragment : RainbowCakeFragment<ItemDetailViewState, ItemDetailViewModel>() {
    override fun provideViewModel() = getViewModelFromFactory()

    companion object {
        private const val EXTRA_ITEM = "ITEM"


        fun newInstance(item: StoredItem): ItemDetailFragment {
            return ItemDetailFragment().applyArgs {
                putParcelable(EXTRA_ITEM, item)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel.setDetail(arguments?.getParcelable(EXTRA_ITEM)!!)

        return ComposeView(requireContext()).apply {
            setContent {
                FullScreenLoading()
            }
        }
    }

    override fun render(viewState: ItemDetailViewState) {
        (view as ComposeView).setContent {
            when (viewState) {
                is Loading -> FullScreenLoading()
                is ItemDetailLoaded -> ProductDetail(
                    product = viewState.item!!,
                    onIconClick = { navigator?.popUntil(NavFragment::class) },
                    onReservationClick = { onReservationSelected(viewState.item!!) },
                    onAcquisitionClick = { onAcquisitionSelected(viewState.item!!) },
                    onReleaseClick = { onReleaseSelected(viewState.item!!) },
                    onMovingClick = { onMovingSelected(viewState.item!!) }
                )
            }.exhaustive
        }
    }

    private fun onReservationSelected(item: StoredItem) {
        navigator?.add(ReservationFragment.newInstance(item))
    }

    private fun onAcquisitionSelected(item: StoredItem) {
        navigator?.add(AcquisitionFragment.newInstance(item))
    }

    private fun onReleaseSelected(item: StoredItem) {
        navigator?.add(ReleaseFragment.newInstance(item))
    }

    private fun onMovingSelected(item: StoredItem) {
        navigator?.add(MovingFragment.newInstance(item))
    }
}