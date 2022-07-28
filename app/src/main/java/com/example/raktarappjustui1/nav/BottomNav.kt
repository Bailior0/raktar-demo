package com.example.raktarappjustui1.nav

import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.raktarappjustui1.Account
import com.example.raktarappjustui1.NewItem
import com.example.raktarappjustui1.Statistics
import com.example.raktarappjustui1.data.Item
import com.example.raktarappjustui1.data.Storage
import com.example.raktarappjustui1.data.Worker
import com.example.raktarappjustui1.ui.theme.Teal200
import com.google.accompanist.pager.ExperimentalPagerApi

@Composable
fun MainScreenView(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {

        NavigationGraph(navController = navController)
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

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.List.screen_route) {
        composable(BottomNavItem.List.screen_route) {
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

            com.example.raktarappjustui1.List( mutableListOf(elem1, elem2, elem3 ) )
        }
        composable(BottomNavItem.Import.screen_route) {
            NewItem()
        }
        composable(BottomNavItem.Stats.screen_route) {
            Statistics()
        }
        composable(BottomNavItem.Account.screen_route) {
            val worker = Worker(
                name = "Raktáros Réka",
                email = "raktaros.reka@raktar.hu",
                phoneNumber = "+36 10 111-1111",
                storages = mutableListOf()
            )
            val storage1 = Storage(
                name = "Raktár1",
                address = "1117 Budapest",
                size = 1234.0,
                description = "Raktár leírása",
                workers = mutableListOf(worker)
            )
            val storage2 = Storage(
                name = "Raktár2",
                address = "1117 Budapest",
                size = 1234.0,
                description = "Raktár leírása",
                workers = mutableListOf(worker)
            )
            val storage3 = Storage(
                name = "Raktár3",
                address = "1117 Budapest",
                size = 1234.0,
                description = "Raktár leírása",
                workers = mutableListOf(worker)
            )
            worker.storages = mutableListOf(storage1, storage2, storage3)

            Account(worker)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    MainScreenView()
}