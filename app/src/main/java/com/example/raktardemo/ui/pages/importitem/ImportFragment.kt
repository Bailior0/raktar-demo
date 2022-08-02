package com.example.raktardemo.ui.pages.importitem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.extensions.exhaustive
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.navigator
import com.example.raktardemo.data.model.Item
import com.example.raktardemo.ui.pages.list.detail.ItemDetailFragment
import com.example.raktardemo.ui.views.NewItem
import com.example.raktardemo.ui.views.helpers.FullScreenLoading
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImportFragment : RainbowCakeFragment<ImportViewState, ImportViewModel>() {
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

        viewModel.setImport()
    }

    override fun render(viewState: ImportViewState) {
        (view as ComposeView).setContent {
            when (viewState) {
                is Loading -> FullScreenLoading()
                is ImportContent -> NewItem(
                    onAddClicked = ::onItemAdded
                )
            }.exhaustive
        }
    }

    private fun onItemAdded(item: Item) {
        //TODO
    }
}