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
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.ui.views.helpers.SegmentedControlTwoWaySwitch
import com.example.raktardemo.ui.views.theme.Shapes

@Composable
fun List(
    items: List<StoredItem>,
    onClicked: (StoredItem) -> Unit,
    onReleaseClicked: (ArrayList<StoredItem>) -> Unit,
    onReserveClicked: (ArrayList<StoredItem>) -> Unit
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
                var selectedMenuItem by remember { mutableStateOf(0) }
                val menuItemList = listOf("Név", "Vonalkód", "Készlet", "Gyártó")

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
                        itemsIndexed(items) { _, item ->
                            ListItem(
                                item = item,
                                onClicked = {
                                    onClicked(item)
                                }
                            )
                        }
                    }
                } else if (typeSwitchState) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
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
                        GroupDetail(
                            groups = mutableListOf(mutableListOf(StoredItem())),
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

@Composable
fun ListItem(
    item: StoredItem,
    onClicked: (StoredItem) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .clickable(onClick = { onClicked(item) })
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