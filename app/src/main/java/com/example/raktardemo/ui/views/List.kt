package com.example.raktardemo.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.raktardemo.R
import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.ui.views.helpers.SegmentedControlTwoWaySwitch
import com.example.raktardemo.ui.views.theme.Shapes

@Composable
fun List(
    items: List<StoredItem>,
    storages: List<Storage>,
    onClicked: (StoredItem, List<Storage>) -> Unit,
    onReleaseClicked: (ArrayList<StoredItem>, String) -> Unit,
    onReserveClicked: (ArrayList<StoredItem>, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        TopAppBar(
            title = { Text(text = "Lista") }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp, 10.dp, 12.dp, 55.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (
                    searchbar,
                    menu,
                    buttons,
                    list
                ) = createRefs()

                var typeSwitchState by remember { mutableStateOf(false) }

                var text by remember { mutableStateOf("") }

                var showMenu by remember { mutableStateOf(false) }
                var checkedState by remember { mutableStateOf(false) }
                var selectedMenuItem by remember { mutableStateOf(0) }
                val menuItemList = listOf("Név", "Készlet", "Gyártó")

                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_search_24),
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        if (!typeSwitchState) {
                            IconButton(
                                onClick = { showMenu = !showMenu }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_filter_alt_24),
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    label = { Text("Kereső") },
                    singleLine = true,
                    modifier = Modifier
                        .constrainAs(searchbar) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .fillMaxWidth()
                )

                Box(
                    modifier = Modifier
                        .constrainAs(menu) {
                            top.linkTo(searchbar.bottom)
                            end.linkTo(parent.end)
                        }
                ) {
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        menuItemList.forEachIndexed { index, s ->
                            DropdownMenuItem(
                                //modifier = Modifier.height(30.dp),
                                onClick = {
                                    selectedMenuItem = index
                                    showMenu = false
                                }
                            ) {
                                Text(text = s)
                            }
                            //Divider(color = Color.LightGray, thickness = 1.dp)
                        }
                        DropdownMenuItem(
                            onClick = {}
                        ) {
                            Text(text = "Készleten")
                            Checkbox(
                                checked = checkedState,
                                onCheckedChange = {
                                    checkedState = it
                                }
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(0.dp, 5.dp, 0.dp, 0.dp)
                        .constrainAs(buttons) {
                            top.linkTo(searchbar.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    SegmentedControlTwoWaySwitch(
                        "Termék",
                        "Csoport",
                        typeSwitchState
                    ) { typeSwitchState = it }
                }

                if (!typeSwitchState) {
                    var sortedItems = items

                    if(checkedState)
                        sortedItems = sortedItems.filter {it.currentQuantity > 0.0}

                    sortedItems = sortedItems.filter { it.item.name.contains(text, true) || it.item.serialNumber.contains(text, true) || it.item.category.name.contains(text, true)}

                    sortedItems = when(selectedMenuItem) {
                        0 -> sortedItems.sortedBy { it.item.name }
                        1 -> sortedItems.sortedBy { it.currentQuantity }
                        2 -> sortedItems.sortedBy { it.item.manufacturer }
                        else -> sortedItems.sortedBy { it.item.name }
                    }

                    LazyColumn(
                        modifier = Modifier
                            .padding(0.dp, 25.dp, 0.dp, 15.dp)
                            .constrainAs(list) {
                                top.linkTo(buttons.bottom)
                                start.linkTo(parent.start)
                                bottom.linkTo(parent.bottom)
                                end.linkTo(parent.end)
                                height = Dimension.fillToConstraints
                                width = Dimension.fillToConstraints
                            }
                    ) {
                        itemsIndexed(sortedItems) { _, item ->
                            ListItem(
                                item = item,
                                storages = storages,
                                onClicked = onClicked
                            )
                        }
                    }
                } else if (typeSwitchState) {
                    var sortedItems = items

                    if(checkedState)
                        sortedItems = sortedItems.filter {it.currentQuantity > 0.0}

                    val itemGroups = ArrayList(sortedItems.groupBy{
                        when {
                            it.itemAcquisitions.isNotEmpty() && (it.item.name.contains(text, true) || it.item.serialNumber.contains(text, true) || it.item.category.name.contains(text, true)) -> it.item.category.name
                            else -> null
                        }
                    }.filterKeys { it != null }.values)

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(0.dp, 0.dp, 0.dp, 5.dp)
                            .constrainAs(list) {
                                top.linkTo(buttons.bottom)
                                start.linkTo(parent.start)
                                bottom.linkTo(parent.bottom)
                                end.linkTo(parent.end)
                                height = Dimension.fillToConstraints
                                width = Dimension.fillToConstraints
                            }
                    ) {
                        itemsIndexed(itemGroups) { _, item ->
                            GroupDetail(
                                groups = item,
                                storages = storages,
                                onClicked = onClicked,
                                onReleaseClicked = onReleaseClicked,
                                onReserveClicked = onReserveClicked
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListItem(
    item: StoredItem,
    storages: List<Storage>,
    onClicked: (StoredItem, List<Storage>) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .clickable(onClick = {
                /*var acqStorages = storages
                for(acq in item.itemAcquisitions)
                    acqStorages = acqStorages.filter { it.id == acq.currentStorage }*/

                onClicked(item, storages)
            })
            .height(IntrinsicSize.Min)
            .padding(all = 1.dp)
            .background(Color.LightGray, Shapes.small)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(all = 5.dp),
        ) {
            Text(
                text = item.item.name, color = Color.Black, fontSize = 18.sp
            )
        }
    }
}

/*@Preview
@Composable
fun ListPreview() {
    RaktarAppJustUi1Theme {
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

        List(mutableListOf(elem1, elem2, elem3, elem2, elem2, elem2, elem2, elem2, elem2, elem2, elem2, elem2, elem2, elem2, elem2, elem2, elem2, elem2, elem2, elem2, elem2, elem2, elem2, elem2, elem2, elem2, elem2, elem2, elem2))
    }
}*/