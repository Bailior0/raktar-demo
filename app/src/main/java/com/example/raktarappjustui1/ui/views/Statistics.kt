package com.example.raktarappjustui1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.raktarappjustui1.ui.views.helpers.ComboBox
import com.example.raktarappjustui1.ui.views.helpers.DatePicker
import com.example.raktarappjustui1.ui.views.helpers.ListMaker
import com.example.raktarappjustui1.ui.views.theme.RaktarAppJustUi1Theme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun Statistics() {
    val scope = rememberCoroutineScope()

    val tabTitles = listOf("Mozgások", "Grafikon", "Értesítések")
    val pagerState = rememberPagerState(pageCount = 3)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        TopAppBar(
            title = { Text(text = "Statisztikák") }
        )

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(
                        pagerState,
                        tabPositions
                    )
                )
            }
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(text = title) },
                )
            }
        }

        HorizontalPager(
            state = pagerState,
        ) { page ->
            when (page) {
                0 -> {
                    Traffic()
                }
                1 -> {
                    Graphs()
                }
                2 -> {
                    Notifications()
                }
            }
        }
    }
}


@Composable
fun Traffic() {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            //.verticalScroll(rememberScrollState())
            .padding(12.dp, 25.dp, 12.dp, 60.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val (
                dateRow,
                comboBoxRow,
                list
            ) = createRefs()

            val actionList = listOf("Bevezetés", "Foglalás", "Kivezetés")
            var actionExpanded by remember { mutableStateOf(false) }
            var actionSelectedIndex by remember { mutableStateOf(0) }

            val storageList = listOf("Raktár1", "Raktár2", "Raktár3", "Raktár4")
            var storageExpanded by remember { mutableStateOf(false) }
            var storageSelectedIndex by remember { mutableStateOf(0) }

            val groupList = listOf("Kábel", "Csavar")
            var groupExpanded by remember { mutableStateOf(false) }
            var groupSelectedIndex by remember { mutableStateOf(0) }

            var datePickerState1 by remember { mutableStateOf(false) }
            var dateInput1 by remember { mutableStateOf("") }

            var datePickerState2 by remember { mutableStateOf(false) }
            var dateInput2 by remember { mutableStateOf("") }

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 0.dp)
                    .constrainAs(dateRow) {
                        top.linkTo(parent.top)
                    }
            ) {
                val (
                    startDate,
                    fromText,
                    endDate,
                    toText
                ) = createRefs()


                Box(
                    modifier = Modifier
                        .constrainAs(startDate) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        }
                ) {
                    DatePicker(
                        fromToday = false,
                        context = context,
                        datePickerState = datePickerState1,
                        setDatePickerState = { datePickerState1 = it },
                        dateInput = dateInput1,
                        onValueChange = { dateInput1 = it }
                    )
                }

                Text(
                    text = " -tól",
                    modifier = Modifier
                        .constrainAs(fromText) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(startDate.end)
                        }
                )

                Box(
                    modifier = Modifier
                        .constrainAs(endDate) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(toText.start)
                        }
                ) {
                    DatePicker(
                        fromToday = false,
                        context = context,
                        datePickerState = datePickerState2,
                        setDatePickerState = { datePickerState2 = it },
                        dateInput = dateInput2,
                        onValueChange = { dateInput2 = it }
                    )
                }

                Text(
                    text = " -ig",
                    modifier = Modifier
                        .constrainAs(toText) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                )
            }

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(comboBoxRow) {
                        top.linkTo(dateRow.bottom)
                    }
            ) {
                val (
                    action,
                    storage,
                    group
                ) = createRefs()

                Box(
                    modifier = Modifier
                        .scale(0.8f)
                        .constrainAs(action) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                ) {
                    ComboBox(
                        list = actionList,
                        selectedIndex = actionSelectedIndex,
                        onIndexChanged = { actionSelectedIndex = it },
                        isExpanded = actionExpanded,
                        onExpandedChanged = { actionExpanded = it },
                        textWidth = ((LocalConfiguration.current.screenWidthDp / 3) - 50).dp
                    )
                }

                Box(
                    modifier = Modifier
                        .scale(0.8f)
                        .constrainAs(storage) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    ComboBox(
                        list = storageList,
                        selectedIndex = storageSelectedIndex,
                        onIndexChanged = { storageSelectedIndex = it },
                        isExpanded = storageExpanded,
                        onExpandedChanged = { storageExpanded = it },
                        textWidth = ((LocalConfiguration.current.screenWidthDp / 3) - 50).dp
                    )
                }

                Box(
                    modifier = Modifier
                        .scale(0.8f)
                        .constrainAs(group) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                ) {
                    ComboBox(
                        list = groupList,
                        selectedIndex = groupSelectedIndex,
                        onIndexChanged = { groupSelectedIndex = it },
                        isExpanded = groupExpanded,
                        onExpandedChanged = { groupExpanded = it },
                        textWidth = ((LocalConfiguration.current.screenWidthDp / 3) - 50).dp
                    )
                }
            }

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 5.dp, 0.dp, 100.dp)
                    .background(MaterialTheme.colors.secondary, RoundedCornerShape(5.dp))
                    .constrainAs(list) {
                        top.linkTo(comboBoxRow.bottom)
                    }
            ) {
                item {
                    ListMaker("rg6-fehér", "Raktár2", "<100 méter")
                    ListMaker("fdsafdsa", "Raktár1", "<150 méter")
                    ListMaker("ghhgfgg", "Raktár1", "<100 méter")
                    ListMaker("n cbcnbv", "Raktár1", "<100 méter")
                    ListMaker("zzuikjhh", "Raktár1", "<100 méter")
                    ListMaker("dfsagfdshgf", "Raktár2", "<200 méter")
                    ListMaker("élklkj", "Raktár1", "<1000 méter")
                    ListMaker("bevételezve", "Raktár1", "<300 méter")
                    ListMaker("bevételezve", "Raktár2", "<400 méter")
                    ListMaker("rg6-fehér", "Raktár2", "<100 méter")
                    ListMaker("fdsafdsa", "Raktár1", "<150 méter")
                    ListMaker("ghhgfgg", "Raktár1", "<100 méter")
                    ListMaker("n cbcnbv", "Raktár1", "<100 méter")
                    ListMaker("zzuikjhh", "Raktár1", "<100 méter")
                    ListMaker("dfsagfdshgf", "Raktár2", "<200 méter")
                    ListMaker("élklkj", "Raktár1", "<1000 méter")
                    ListMaker("bevételezve", "Raktár1", "<300 méter")
                    ListMaker("bevételezve", "Raktár2", "<400 méter")
                    ListMaker("rg6-fehér", "Raktár2", "<100 méter")
                    ListMaker("fdsafdsa", "Raktár1", "<150 méter")
                    ListMaker("ghhgfgg", "Raktár1", "<100 méter")
                    ListMaker("n cbcnbv", "Raktár1", "<100 méter")
                    ListMaker("zzuikjhh", "Raktár1", "<100 méter")
                    ListMaker("dfsagfdshgf", "Raktár2", "<200 méter")
                    ListMaker("élklkj", "Raktár1", "<1000 méter")
                    ListMaker("bevételezve", "Raktár1", "<300 méter")
                    ListMaker("bevételezve", "Raktár2", "<400 méter")
                    ListMaker("rg6-fehér", "Raktár2", "<100 méter")
                    ListMaker("fdsafdsa", "Raktár1", "<150 méter")
                    ListMaker("ghhgfgg", "Raktár1", "<100 méter")
                    ListMaker("n cbcnbv", "Raktár1", "<100 méter")
                    ListMaker("zzuikjhh", "Raktár1", "<100 méter")
                    ListMaker("dfsagfdshgf", "Raktár2", "<200 méter")
                    ListMaker("élklkj", "Raktár1", "<1000 méter")
                    ListMaker("bevételezve", "Raktár1", "<300 méter")
                    ListMaker("bevételezve", "Raktár2", "<400 méter")
                }
            }
        }
    }


}

@Composable
fun Graphs() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(12.dp, 25.dp, 12.dp, 60.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val (
                graph1Text,
                graph1,
                graph2Text,
                graph2,
                graph3Text,
                graph3,
                graph4Text,
                graph4
            ) = createRefs()

            Text(
                text = "Első Grafikon",
                color = Color.Gray,
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 5.dp)
                    .constrainAs(graph1Text) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Image(
                painter = painterResource(id = R.drawable.barchart_ver_1),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(graph1) {
                        top.linkTo(graph1Text.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Text(
                text = "Második Grafikon",
                color = Color.Gray,
                modifier = Modifier
                    .padding(0.dp, 20.dp, 0.dp, 5.dp)
                    .constrainAs(graph2Text) {
                        top.linkTo(graph1.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Image(
                painter = painterResource(id = R.drawable.pie_chart),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(graph2) {
                        top.linkTo(graph2Text.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Text(
                text = "Harmadik Grafikon",
                color = Color.Gray,
                modifier = Modifier
                    .padding(0.dp, 20.dp, 0.dp, 5.dp)
                    .constrainAs(graph3Text) {
                        top.linkTo(graph2.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Image(
                painter = painterResource(id = R.drawable.rd_s_sample_graph),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(graph3) {
                        top.linkTo(graph3Text.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Text(
                text = "Negyedik Grafikon",
                color = Color.Gray,
                modifier = Modifier
                    .padding(0.dp, 20.dp, 0.dp, 5.dp)
                    .constrainAs(graph4Text) {
                        top.linkTo(graph3.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Image(
                painter = painterResource(id = R.drawable.grouped_bar_chart),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(graph4) {
                        top.linkTo(graph4Text.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }
    }
}

@Composable
fun Notifications() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val list = createRef()

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp, 25.dp, 12.dp, 65.dp)
                .background(MaterialTheme.colors.secondary, RoundedCornerShape(5.dp))
                .constrainAs(list) {
                    top.linkTo(parent.top)
                }
        ) {
            item {
                ListMaker("rg6-fehér", "Raktár2", "<100 méter")
                ListMaker("fdsafdsa", "Raktár1", "<150 méter")
                ListMaker("ghhgfgg", "Raktár1", "<100 méter")
                ListMaker("n cbcnbv", "Raktár1", "<100 méter")
                ListMaker("zzuikjhh", "Raktár1", "<100 méter")
                ListMaker("dfsagfdshgf", "Raktár2", "<200 méter")
                ListMaker("élklkj", "Raktár1", "<1000 méter")
                ListMaker("bevételezve", "Raktár1", "<300 méter")
                ListMaker("bevételezve", "Raktár2", "<400 méter")
                ListMaker("rg6-fehér", "Raktár2", "<100 méter")
                ListMaker("fdsafdsa", "Raktár1", "<150 méter")
                ListMaker("ghhgfgg", "Raktár1", "<100 méter")
                ListMaker("n cbcnbv", "Raktár1", "<100 méter")
                ListMaker("zzuikjhh", "Raktár1", "<100 méter")
                ListMaker("dfsagfdshgf", "Raktár2", "<200 méter")
                ListMaker("élklkj", "Raktár1", "<1000 méter")
                ListMaker("bevételezve", "Raktár1", "<300 méter")
                ListMaker("bevételezve", "Raktár2", "<400 méter")
                ListMaker("rg6-fehér", "Raktár2", "<100 méter")
                ListMaker("fdsafdsa", "Raktár1", "<150 méter")
                ListMaker("ghhgfgg", "Raktár1", "<100 méter")
                ListMaker("n cbcnbv", "Raktár1", "<100 méter")
                ListMaker("zzuikjhh", "Raktár1", "<100 méter")
                ListMaker("dfsagfdshgf", "Raktár2", "<200 méter")
                ListMaker("élklkj", "Raktár1", "<1000 méter")
                ListMaker("bevételezve", "Raktár1", "<300 méter")
                ListMaker("bevételezve", "Raktár2", "<400 méter")
                ListMaker("rg6-fehér", "Raktár2", "<100 méter")
                ListMaker("fdsafdsa", "Raktár1", "<150 méter")
                ListMaker("ghhgfgg", "Raktár1", "<100 méter")
                ListMaker("n cbcnbv", "Raktár1", "<100 méter")
                ListMaker("zzuikjhh", "Raktár1", "<100 méter")
                ListMaker("dfsagfdshgf", "Raktár2", "<200 méter")
                ListMaker("élklkj", "Raktár1", "<1000 méter")
                ListMaker("bevételezve", "Raktár1", "<300 méter")
                ListMaker("bevételezve", "Raktár2", "<400 méter")
            }
        }
    }
}

/*@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun StatisticsPreview() {
    RaktarAppJustUi1Theme {
        Statistics()
    }
}*/
