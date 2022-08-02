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
import com.example.raktardemo.data.enums.PackageType
import com.example.raktardemo.data.enums.QuantityUnit
import com.example.raktardemo.data.model.Category
import com.example.raktardemo.data.model.Item
import com.example.raktardemo.ui.pages.list.detail.ItemDetailFragment
import com.example.raktardemo.ui.pages.list.release.ReleaseFragment
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
                    val elem1 = Item(
                        id = "0",
                        name = "elem1",
                        category = Category("0", "kábel"),
                        manufacturer = "Dolog.Kft",
                        serialNumber = "0",
                        type = PackageType.Package,
                        quantityUnit = QuantityUnit.Meter,
                        defaultPackageQuantity = 1.0,
                        openable = false,
                        defaultPurchasePrice = null,
                        minimumStoredQuantity = null
                    )
                    val elem2 = Item(
                        id = "0",
                        name = "elem2",
                        category = Category("0", "kábel"),
                        manufacturer = "Dolog.Kft",
                        serialNumber = "0",
                        type = PackageType.Package,
                        quantityUnit = QuantityUnit.Meter,
                        defaultPackageQuantity = 1.0,
                        openable = false,
                        defaultPurchasePrice = null,
                        minimumStoredQuantity = null
                    )
                    val elem3 = Item(
                        id = "0",
                        name = "elem3",
                        category = Category("0", "kábel"),
                        manufacturer = "Dolog.Kft",
                        serialNumber = "0",
                        type = PackageType.Package,
                        quantityUnit = QuantityUnit.Meter,
                        defaultPackageQuantity = 1.0,
                        openable = false,
                        defaultPurchasePrice = null,
                        minimumStoredQuantity = null
                    )
                    val list = mutableListOf(elem1, elem2, elem3)

                    com.example.raktardemo.ui.views.List(
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

    private fun onReleaseSelected(items: ArrayList<Item>) {
        navigator?.add(ReleaseFragment.newInstance(items))
    }
}