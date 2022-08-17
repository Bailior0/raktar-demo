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
import com.example.raktardemo.data.model.Storage
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
                        storages = viewState.storages,
                        onClicked = ::onItemSelected,
                        onReleaseClicked = ::onReleaseSelected,
                        onReserveClicked = ::onReserveSelected
                    )
                }
            }.exhaustive
        }
    }

    private fun onItemSelected(item: StoredItem, storages: List<Storage>, presentStorages: List<Storage>) {
        navigator?.add(ItemDetailFragment.newInstance(item, storages as ArrayList<Storage>, presentStorages as ArrayList<Storage>))
    }

    private fun onReleaseSelected(items: ArrayList<StoredItem>, acquisitionId: String) {
        navigator?.add(ReleaseFragment.newInstance(items, acquisitionId))
    }

    private fun onReserveSelected(items: ArrayList<StoredItem>, acquisitionId: String) {
        //navigator?.add(ReservationFragment.newInstance(items, acquisitionId))
    }
}