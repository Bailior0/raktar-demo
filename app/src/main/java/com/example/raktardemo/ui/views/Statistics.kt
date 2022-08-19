package com.example.raktardemo.ui.views

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.raktardemo.R
import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.ui.views.helpers.ComboBox
import com.example.raktardemo.ui.views.helpers.DatePicker
import com.example.raktardemo.ui.views.helpers.ListMaker
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.getInstance

@ExperimentalPagerApi
@Composable
fun Statistics(
    items: List<StoredItem>,
    storages: List<Storage>
) {
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
                    Traffic(items, storages)
                }
                1 -> {
                    Graphs()
                }
                2 -> {
                    Notifications(items, storages)
                }
            }
        }
    }
}

@Composable
fun Traffic(
    items: List<StoredItem>,
    storages: List<Storage>
) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
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

            var itemList = items

            val actionList = listOf("Összes", "Bevezetés", "Foglalás", "Kivezetés", "Mozgatás")
            var actionExpanded by remember { mutableStateOf(false) }
            var actionSelectedIndex by remember { mutableStateOf(0) }

            /*val storageNameList = mutableListOf<String>()
            val storageIdList = mutableListOf<String>()

            for (storage in storages) {
                storageNameList.add(storage.name)
                storageIdList.add(storage.id)
            }

            var storageExpanded by remember { mutableStateOf(false) }
            var storageSelectedIndex by remember { mutableStateOf(0) }*/

            val groupList = mutableListOf("Összes")

            for (item in items) {
                if (!(item.item.category.name.equals("")))
                    groupList.add(item.item.category.name)
            }

            var groupExpanded by remember { mutableStateOf(false) }
            var groupSelectedIndex by remember { mutableStateOf(0) }

            var datePickerState1 by remember { mutableStateOf(false) }
            var dateInput1 by remember { mutableStateOf("") }

            var datePickerState2 by remember { mutableStateOf(false) }
            var dateInput2 by remember { mutableStateOf("") }

            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            var dateInputDate1: Date
            var dateInputDate2: Date
            val shiftedDate1: Date
            val shiftedDate2: Date

            var acquisitionDate: Date
            var reservationDate: Date
            var releaseDate: Date

            var calendar = Calendar.getInstance()

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
                        //.scale(0.8f)
                        .constrainAs(action) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            //end.linkTo(group.start)
                        }
                ) {
                    ComboBox(
                        list = actionList,
                        selectedIndex = actionSelectedIndex,
                        onIndexChanged = { actionSelectedIndex = it },
                        isExpanded = actionExpanded,
                        onExpandedChanged = { actionExpanded = it },
                        textWidth = 90.dp
                        //textWidth = ((LocalConfiguration.current.screenWidthDp / 3) - 50).dp
                    )
                }

                /*Box(
                    modifier = Modifier
                        .scale(0.8f)
                        .constrainAs(storage) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    ComboBox(
                        list = storageNameList,
                        selectedIndex = storageSelectedIndex,
                        onIndexChanged = { storageSelectedIndex = it },
                        isExpanded = storageExpanded,
                        onExpandedChanged = { storageExpanded = it },
                        textWidth = ((LocalConfiguration.current.screenWidthDp / 3) - 50).dp
                    )
                }*/

                Box(
                    modifier = Modifier
                        //.scale(0.8f)
                        .constrainAs(group) {
                            top.linkTo(parent.top)
                            //start.linkTo(action.end)
                            end.linkTo(parent.end)
                        }
                ) {
                    ComboBox(
                        list = groupList,
                        selectedIndex = groupSelectedIndex,
                        onIndexChanged = { groupSelectedIndex = it },
                        isExpanded = groupExpanded,
                        onExpandedChanged = { groupExpanded = it },
                        textWidth = 90.dp
                        //textWidth = ((LocalConfiguration.current.screenWidthDp / 3) - 50).dp
                    )
                }
            }

            if (groupSelectedIndex != 0)
                itemList = itemList.filter {
                    it.item.category.name.equals(groupList[groupSelectedIndex])
                }

            //var contains = false
            //var itemList2 = mutableListOf<StoredItem>()

            /*if (actionSelectedIndex == 0) {
                for (item in itemList) {
                    for (acq in item.itemAcquisitions) {
                        for (item2 in itemList2) {
                            for (acq2 in item2.itemAcquisitions) {
                                if (acq2.id.equals(acq.id))
                                    contains = true
                            }
                        }

                        if (acq.currentStorage.equals(storageIdList[storageSelectedIndex]) && !contains) {
                            itemList2.add(item)
                        }

                        contains = false
                    }
                }
            } else {
                itemList2 = itemList.toMutableList()
            }*/

            if (!(dateInput1.equals(""))) {
                dateInputDate1 = dateFormat.parse(dateInput1) as Date
                calendar.time = dateInputDate1
                calendar.add(Calendar.DAY_OF_MONTH, -1)
                shiftedDate1 = calendar.time
            } else {
                shiftedDate1 = dateFormat.parse(
                    "${calendar.get(Calendar.YEAR)}-" +
                            "${calendar.get(Calendar.MONTH) + 1}-" +
                            "${calendar.get(Calendar.DAY_OF_MONTH) - 1}"
                ) as Date
            }

            calendar = getInstance()

            if (!(dateInput2.equals(""))) {
                dateInputDate2 = dateFormat.parse(dateInput2) as Date
                calendar.time = dateInputDate2
                calendar.add(Calendar.DAY_OF_MONTH, 1)
                shiftedDate2 = calendar.time
            } else {
                shiftedDate2 = dateFormat.parse(
                    "${calendar.get(Calendar.YEAR)}-" +
                            "${calendar.get(Calendar.MONTH) + 1}-" +
                            "${calendar.get(Calendar.DAY_OF_MONTH) + 1}"
                ) as Date
            }

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 5.dp, 0.dp, 100.dp)
                    .constrainAs(list) {
                        top.linkTo(comboBoxRow.bottom)
                    }
            ) {
                itemsIndexed(itemList) { _, item ->
                    when (actionSelectedIndex) {
                        0 -> {
                            acquisitionStatList(item, dateFormat, shiftedDate1, shiftedDate2, true)
                            reserveStatList(item, dateFormat, shiftedDate1, shiftedDate2, true)
                            releaseStatList(item, dateFormat, shiftedDate1, shiftedDate2, true)
                            movingStatList(item, dateFormat, shiftedDate1, shiftedDate2, true)
                        }
                        1 -> {
                            acquisitionStatList(item, dateFormat, shiftedDate1, shiftedDate2)
                        }
                        2 -> {
                            reserveStatList(item, dateFormat, shiftedDate1, shiftedDate2)
                        }
                        3 -> {
                            releaseStatList(item, dateFormat, shiftedDate1, shiftedDate2)
                        }
                        4 -> {
                            movingStatList(item, dateFormat, shiftedDate1, shiftedDate2)
                        }
                        else -> {
                            ListMaker(
                                text1 = "Hiba",
                                text2 = "",
                                text3 = ""
                            )
                        }
                    }
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
fun Notifications(
    items: List<StoredItem>,
    storages: List<Storage>
) {
    val itemList = items.filter {
        it.currentQuantity < (it.item.minimumStoredQuantity ?: 0.0)
    }

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
                .constrainAs(list) {
                    top.linkTo(parent.top)
                }
        ) {
            itemsIndexed(itemList) { _, item ->
                ListMaker(
                    text1 = item.item.name,
                    text2 = item.item.serialNumber,
                    text3 = "<${item.item.minimumStoredQuantity}"
                )
            }

            /*item {
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
            }*/
        }
    }
}

@Composable
fun acquisitionStatList(
    item: StoredItem,
    dateFormat: SimpleDateFormat,
    shiftedDate1: Date,
    shiftedDate2: Date,
    all: Boolean = false
) {
    var acquisitionDate: Date

    val name =
        if (all)
            "Bev.: " + item.item.name
        else
            item.item.name

    for (acq in item.itemAcquisitions) {
        if (!(acq.acquisitionDate.equals("")))
            acquisitionDate = dateFormat.parse(acq.acquisitionDate) as Date
        else
            acquisitionDate = dateFormat.parse("1969-06-09") as Date

        if (
            acquisitionDate.after(shiftedDate1) &&
            acquisitionDate.before(shiftedDate2) &&
            acq.quantity > 0
        //acq.currentStorage.equals(storageIdList[storageSelectedIndex])
        ) {
            ListMaker(
                text1 = name,
                text2 = (acq.quantity * item.item.defaultPackageQuantity).toString(),
                text3 = acq.acquisitionDate
            )
        }
    }
}

@Composable
fun reserveStatList(
    item: StoredItem,
    dateFormat: SimpleDateFormat,
    shiftedDate1: Date,
    shiftedDate2: Date,
    all: Boolean = false
) {
    var reservationDate: Date

    val name =
        if (all)
            "Fogl.: " + item.item.name
        else
            item.item.name

    for (res in item.reservations) {
        if (!(res.reservationDate.equals("")))
            reservationDate =
                dateFormat.parse(res.reservationDate) as Date
        else
            reservationDate = dateFormat.parse("1969-06-09") as Date

        if (
            reservationDate.after(shiftedDate1) &&
            reservationDate.before(shiftedDate2)
        ) {
            ListMaker(
                text1 = name,
                text2 = res.reservationQuantity.toString(),
                text3 = res.reservationDate
            )
        }
    }
}

@Composable
fun releaseStatList(
    item: StoredItem,
    dateFormat: SimpleDateFormat,
    shiftedDate1: Date,
    shiftedDate2: Date,
    all: Boolean = false
) {
    var releaseDate: Date

    val name =
        if (all)
            "Kiv.: " + item.item.name
        else
            item.item.name

    for (rel in item.releases) {
        if (!(rel.releaseDate.equals("")))
            releaseDate =
                dateFormat.parse(rel.releaseDate) as Date
        else
            releaseDate = dateFormat.parse("1969-06-09") as Date

        if (
            releaseDate.after(shiftedDate1) &&
            releaseDate.before(shiftedDate2)
        ) {
            ListMaker(
                text1 = name,
                text2 = rel.quantity.toString(),
                text3 = rel.quality.translation
            )
        }
    }
}

@Composable
fun movingStatList(
    item: StoredItem,
    dateFormat: SimpleDateFormat,
    shiftedDate1: Date,
    shiftedDate2: Date,
    all: Boolean = false
) {
    var movingDate: Date

    val name =
        if (all)
            "Mozg.: " + item.item.name
        else
            item.item.name

    for (mov in item.movings) {
        if (!(mov.movingDate.equals("")))
            movingDate =
                dateFormat.parse(mov.movingDate) as Date
        else
            movingDate = dateFormat.parse("1969-06-09") as Date

        if (
            movingDate.after(shiftedDate1) &&
            movingDate.before(shiftedDate2)
        ) {
            ListMaker(
                text1 = name,
                text2 = mov.quantity.toString(),
                text3 = mov.movingDate
            )
        }
    }
}