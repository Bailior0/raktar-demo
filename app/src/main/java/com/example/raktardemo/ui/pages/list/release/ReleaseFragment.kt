package com.example.raktardemo.ui.pages.list.release

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
import com.example.raktardemo.data.model.Release
import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.ui.views.Release
import com.example.raktardemo.ui.views.helpers.FullScreenLoading
import com.example.raktardemo.ui.views.theme.RaktarAppJustUi1Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReleaseFragment : RainbowCakeFragment<ReleaseViewState, ReleaseViewModel>() {
    override fun provideViewModel() = getViewModelFromFactory()

    companion object {
        private const val EXTRA_ITEM = "ITEM"
        private const val EXTRA_STORAGES = "STORAGES"
        private const val EXTRA_GROUP = "GROUP"
        private const val EXTRA_ACQID = "ACQID"

        fun newInstance(item: StoredItem, storages: ArrayList<Storage>): ReleaseFragment {
            return ReleaseFragment().applyArgs {
                putParcelable(EXTRA_ITEM, item)
                putParcelableArrayList(EXTRA_STORAGES, storages)
            }
        }

        fun newInstance(items: ArrayList<StoredItem>, acqId: String): ReleaseFragment {
            return ReleaseFragment().applyArgs {
                putParcelableArrayList(EXTRA_GROUP, items)
                putString(EXTRA_ACQID, acqId)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val item = arguments?.getParcelable<StoredItem>(EXTRA_ITEM)
        val group = arguments?.getParcelableArrayList<StoredItem>(EXTRA_GROUP)
        val acqId = arguments?.getString(EXTRA_ACQID)
        val storages: ArrayList<Storage> = arguments?.getParcelableArrayList(EXTRA_STORAGES) ?: ArrayList()

        if(item != null)
            viewModel.setRelease(item, storages)
        else if(group != null)
            viewModel.setReleaseGroup(group, acqId)

        return ComposeView(requireContext()).apply {
            setContent {
                FullScreenLoading()
            }
        }
    }

    override fun render(viewState: ReleaseViewState) {
        (view as ComposeView).setContent {
            RaktarAppJustUi1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    when (viewState) {
                        is Loading -> FullScreenLoading()
                        is ReleaseContent -> Release(
                            product = viewState.item!!,
                            storages = viewState.storages,
                            group = emptyList(),
                            acqId = null,
                            onIconClick = { navigator?.pop() },
                            onReleaseClick = ::onRelease
                        )
                        is ReleaseGroupContent -> Release(
                            product = null,
                            storages = emptyList(),
                            group = viewState.group,
                            acqId = viewState.acqId,
                            onIconClick = { navigator?.pop() },
                            onReleaseClick = ::onRelease
                        )
                    }.exhaustive
                }
            }
        }
    }

    private fun onRelease(release: Release, item: StoredItem?, selectedAcqId: String, chosenOpenedPackages: List<Pair<String, Double>>, acqId: String?, group: List<StoredItem>) {
        viewModel.onRelease(release, item, selectedAcqId, chosenOpenedPackages, acqId, group)
        navigator?.pop()
    }
}