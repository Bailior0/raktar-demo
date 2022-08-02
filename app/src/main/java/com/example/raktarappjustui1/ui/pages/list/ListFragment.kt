package com.example.raktarappjustui1.ui.pages.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.extensions.exhaustive
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.navigator
import com.example.raktarappjustui1.data.Group
import com.example.raktarappjustui1.data.Item
import com.example.raktarappjustui1.ui.pages.list.detail.ItemDetailFragment
import com.example.raktarappjustui1.ui.pages.list.release.ReleaseFragment
import com.example.raktarappjustui1.ui.views.helpers.FullScreenLoading
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
                    val elem1 = Item(
                        name = "elem1",
                        category = "kábel",
                        quantityUnit = "m",
                        quantity = 0.0
                    )
                    val elem2 = Item(
                        name = "elem2",
                        category = "kábel",
                        quantityUnit = "m",
                        quantity = 0.0
                    )
                    val elem3 = Item(
                        name = "elem3",
                        category = "kábel",
                        quantityUnit = "m",
                        quantity = 0.0
                    )
                    val list = mutableListOf(elem1, elem2, elem3)

                    com.example.raktarappjustui1.ui.views.List(
                        items = list,
                        onClicked = ::onItemSelected,
                        onReleaseClicked = ::onReleaseSelected
                    )
                }
            }.exhaustive
        }
    }

    private fun onItemSelected(item: Item) {
        navigator?.add(ItemDetailFragment.newInstance(item))
    }

    private fun onReleaseSelected(group: Group) {
        navigator?.add(ReleaseFragment.newInstance(group))
    }
}