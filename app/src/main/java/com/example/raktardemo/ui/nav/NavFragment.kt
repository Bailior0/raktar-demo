package com.example.raktardemo.ui.nav

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
import com.example.raktardemo.ui.pages.account.AccountFragment
import com.example.raktardemo.ui.pages.importitem.ImportFragment
import com.example.raktardemo.ui.pages.list.ListFragment
import com.example.raktardemo.ui.pages.stats.StatsFragment
import com.example.raktardemo.ui.views.helpers.FullScreenLoading
import com.example.raktardemo.ui.views.nav.MainScreenView
import com.example.raktardemo.ui.views.theme.RaktarAppJustUi1Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavFragment: RainbowCakeFragment<NavViewState, NavViewModel>() {
    override fun provideViewModel() = getViewModelFromFactory()

    private lateinit var listFragment: ListFragment
    private lateinit var importFragment: ImportFragment
    private lateinit var statsFragment: StatsFragment
    private lateinit var accountFragment: AccountFragment


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        listFragment = ListFragment()
        importFragment = ImportFragment()
        statsFragment = StatsFragment()
        accountFragment = AccountFragment()

        return ComposeView(requireContext()).apply {
            setContent {
                FullScreenLoading()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setNav()
    }

    override fun render(viewState: NavViewState) {
        (view as ComposeView).setContent {
            RaktarAppJustUi1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    when (viewState) {
                        is Loading -> FullScreenLoading()
                        is NavContent -> MainScreenView(parentFragmentManager, listFragment, importFragment, statsFragment, accountFragment)
                    }.exhaustive
                }
            }
        }
    }
}