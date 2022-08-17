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
import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.ui.views.Reservation
import com.example.raktardemo.ui.views.helpers.FullScreenLoading
import com.example.raktardemo.ui.views.theme.RaktarAppJustUi1Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationFragment : RainbowCakeFragment<ReservationViewState, ReservationViewModel>() {
    override fun provideViewModel() = getViewModelFromFactory()

    companion object {
        private const val EXTRA_ITEM = "ITEM"
        private const val EXTRA_STORAGES = "STORAGES"
        private const val EXTRA_GROUP = "GROUP"
        private const val EXTRA_ACQID = "ACQID"

        fun newInstance(item: StoredItem, storages: ArrayList<Storage>): ReservationFragment {
            return ReservationFragment().applyArgs {
                putParcelable(EXTRA_ITEM, item)
                putParcelableArrayList(EXTRA_STORAGES, storages)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val item = arguments?.getParcelable<StoredItem>(EXTRA_ITEM)
        val storages: ArrayList<Storage> = arguments?.getParcelableArrayList(EXTRA_STORAGES)!!

        if(item != null)
            viewModel.setReservation(item, storages)

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
                            storages = viewState.storages,
                            acqId = null,
                            onIconClick = { navigator?.pop() },
                            onReservationClick = ::onReservation,
                        )
                    }.exhaustive
                }
            }
        }
    }

    private fun onReservation(
        reservation: Reservation,
        item: StoredItem?,
        acqId: String?,
        group: List<StoredItem>
    ) {
        viewModel.onReservation(reservation, item, acqId, group)
        navigator?.pop()
    }
}