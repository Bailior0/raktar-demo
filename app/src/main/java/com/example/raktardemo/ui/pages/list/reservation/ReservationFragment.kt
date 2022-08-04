package com.example.raktardemo.ui.pages.list.reservation

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
import com.example.raktardemo.data.model.Reservation
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.ui.pages.list.release.ReleaseFragment
import com.example.raktardemo.ui.views.Reservation
import com.example.raktardemo.ui.views.helpers.FullScreenLoading
import com.example.raktardemo.ui.views.theme.RaktarAppJustUi1Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationFragment : RainbowCakeFragment<ReservationViewState, ReservationViewModel>() {
    override fun provideViewModel() = getViewModelFromFactory()

    companion object {
        private const val EXTRA_ITEM = "ITEM"
        private const val EXTRA_GROUP = "GROUP"

        fun newInstance(item: StoredItem): ReservationFragment {
            return ReservationFragment().applyArgs {
                putParcelable(EXTRA_ITEM, item)
            }
        }

        fun newInstance(items: ArrayList<StoredItem>): ReservationFragment {
            return ReservationFragment().applyArgs {
                putParcelableArrayList(EXTRA_GROUP, items)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        //viewModel.setReservation(arguments?.getParcelable(EXTRA_ITEM)!!)
        val item = arguments?.getParcelable<StoredItem>(ReservationFragment.EXTRA_ITEM)
        val group = arguments?.getParcelableArrayList<StoredItem>(ReservationFragment.EXTRA_GROUP)

        if(item != null)
            viewModel.setReservation(item)
        else if(group != null)
            viewModel.setReservationGroup(group)

        return ComposeView(requireContext()).apply {
            setContent {
                FullScreenLoading()
            }
        }
    }

    override fun render(viewState: ReservationViewState) {
        (view as ComposeView).setContent {
            RaktarAppJustUi1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    when (viewState) {
                        is Loading -> FullScreenLoading()
                        is ReservationContent -> Reservation(
                            product = viewState.item!!,
                            group = emptyList(),
                            onIconClick = { navigator?.pop() },
                            onReservationClick = ::onReservation
                        )
                        is ReservationGroupContent -> Reservation(
                            product = null,
                            group = viewState.group,
                            onIconClick = { navigator?.pop() },
                            onReservationClick = ::onReservation
                        )
                    }.exhaustive
                }
            }
        }
    }

    private fun onReservation(reservation: Reservation) {
        //TODO
    }
}