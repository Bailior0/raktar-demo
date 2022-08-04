package com.example.raktardemo.ui.pages.list.release

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
import com.example.raktardemo.ui.views.Release
import com.example.raktardemo.ui.views.helpers.FullScreenLoading
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReleaseFragment : RainbowCakeFragment<ReleaseViewState, ReleaseViewModel>() {
    override fun provideViewModel() = getViewModelFromFactory()

    companion object {
        private const val EXTRA_ITEM = "ITEM"
        private const val EXTRA_GROUP = "GROUP"

        fun newInstance(item: StoredItem): ReleaseFragment {
            return ReleaseFragment().applyArgs {
                putParcelable(EXTRA_ITEM, item)
            }
        }

        fun newInstance(items: ArrayList<StoredItem>): ReleaseFragment {
            return ReleaseFragment().applyArgs {
                putParcelableArrayList(EXTRA_GROUP, items)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val item = arguments?.getParcelable<StoredItem>(EXTRA_ITEM)
        val group = arguments?.getParcelableArrayList<StoredItem>(EXTRA_GROUP)

        if(item != null)
            viewModel.setRelease(item)
        else if(group != null)
            viewModel.setReleaseGroup(group)

        return ComposeView(requireContext()).apply {
            setContent {
                FullScreenLoading()
            }
        }
    }

    override fun render(viewState: ReleaseViewState) {
        (view as ComposeView).setContent {
            when (viewState) {
                is Loading -> FullScreenLoading()
                is ReleaseContent -> Release(
                    product = viewState.item!!,
                    group = emptyList(),
                    onIconClick = { navigator?.pop() },
                    onReleaseClick = ::onRelease
                )
                is ReleaseGroupContent -> Release(
                    product = null,
                    group = viewState.group,
                    onIconClick = { navigator?.pop() },
                    onReleaseClick = ::onRelease
                )
            }.exhaustive
        }
    }

    private fun onRelease() {
        //TODO
    }
}