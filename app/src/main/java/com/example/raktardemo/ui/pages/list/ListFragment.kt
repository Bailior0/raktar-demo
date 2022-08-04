package com.example.raktardemo.ui.pages.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.extensions.exhaustive
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.navigator
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.ui.pages.list.detail.ItemDetailFragment
import com.example.raktardemo.ui.pages.list.release.ReleaseFragment
import com.example.raktardemo.ui.pages.list.reservation.ReservationFragment
import com.example.raktardemo.ui.views.helpers.FullScreenLoading
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : RainbowCakeFragment<ListViewState, ListViewModel>() {
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

        viewModel.setList()
    }

    override fun render(viewState: ListViewState) {
        (view as ComposeView).setContent {
            when (viewState) {
                is Loading -> FullScreenLoading()
                is ListContent -> {
                    com.example.raktardemo.ui.views.List(
                        items = viewState.list,
                        onClicked = ::onItemSelected,
                        onReleaseClicked = ::onReleaseSelected,
                        onReserveClicked = ::onReserveSelected
                    )
                }
            }.exhaustive
        }
    }

    private fun onItemSelected(item: StoredItem) {
        navigator?.add(ItemDetailFragment.newInstance(item))
    }

    private fun onReleaseSelected(items: ArrayList<StoredItem>) {
        navigator?.add(ReleaseFragment.newInstance(items))
    }

    private fun onReserveSelected(items: ArrayList<StoredItem>) {
        navigator?.add(ReservationFragment.newInstance(items))
    }
}