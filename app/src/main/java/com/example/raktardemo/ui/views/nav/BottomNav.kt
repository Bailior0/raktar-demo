package com.example.raktardemo.ui.views.nav

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.raktardemo.ui.pages.account.AccountFragment
import com.example.raktardemo.ui.pages.importitem.ImportFragment
import com.example.raktardemo.ui.pages.list.ListFragment
import com.example.raktardemo.ui.pages.stats.StatsFragment
import com.example.raktardemo.ui.views.theme.Teal200

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreenView(fragmentManager: FragmentManager, listFragment: ListFragment, importFragment: ImportFragment, statsFragment: StatsFragment, accountFragment: AccountFragment) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {

        NavigationGraph(navController = navController, fragmentManager = fragmentManager, listFragment = listFragment, importFragment = importFragment, statsFragment = statsFragment, accountFragment = accountFragment)
    }
}
@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.List,
        BottomNavItem.Import,
        BottomNavItem.Stats,
        BottomNavItem.Account
    )
    androidx.compose.material.BottomNavigation(
        backgroundColor = Teal200,
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title,
                    fontSize = 9.sp) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, fragmentManager: FragmentManager, listFragment: ListFragment, importFragment: ImportFragment, statsFragment: StatsFragment, accountFragment: AccountFragment) {
    NavHost(navController, startDestination = BottomNavItem.List.screen_route) {
        composable(BottomNavItem.List.screen_route) {
            FragmentContainer(
                modifier = Modifier.fillMaxSize(),
                fragmentManager = fragmentManager,
                commit = { add(it, listFragment) }
            )
        }
        composable(BottomNavItem.Import.screen_route) {
            FragmentContainer(
                modifier = Modifier.fillMaxSize(),
                fragmentManager = fragmentManager,
                commit = { add(it, importFragment) }
            )
        }
        composable(BottomNavItem.Stats.screen_route) {
            FragmentContainer(
                modifier = Modifier.fillMaxSize(),
                fragmentManager = fragmentManager,
                commit = { add(it, statsFragment) }
            )
        }
        composable(BottomNavItem.Account.screen_route) {
            FragmentContainer(
                modifier = Modifier.fillMaxSize(),
                fragmentManager = fragmentManager,
                commit = { add(it, accountFragment) }
            )
        }
    }
}

@Composable
fun FragmentContainer(
    modifier: Modifier = Modifier,
    fragmentManager: FragmentManager,
    commit: FragmentTransaction.(containerId: Int) -> Unit
) {
    val containerId by rememberSaveable {
        mutableStateOf(View.generateViewId()) }
    AndroidView(
        modifier = modifier,
        factory = { context ->
            fragmentManager.findFragmentById(containerId)?.view
                ?.also { (it.parent as? ViewGroup)?.removeView(it) }
                ?: FragmentContainerView(context)
                    .apply { id = containerId }
                    .also {
                        fragmentManager.commit { commit(it.id) }
                    }
        },
        update = {}
    )
}

/*@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    MainScreenView(listFragment)
}*/