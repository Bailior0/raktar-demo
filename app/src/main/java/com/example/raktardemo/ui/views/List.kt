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
import com.example.raktardemo.data.enums.PackageType
import com.example.raktardemo.data.enums.QuantityUnit
import com.example.raktardemo.data.model.Category
import com.example.raktardemo.data.model.Item
import com.example.raktardemo.ui.views.helpers.SegmentedControlTwoWaySwitch
import com.example.raktardemo.ui.views.theme.Shapes

@Composable
fun List(
    items: List<Item>,
    onClicked: (Item) -> Unit,
    onReleaseClicked: (ArrayList<Item>) -> Unit
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
                    item,
                    searchbar,
                    menu,
                    buttons,
                    list
                ) = createRefs()
                var typeSwitchState by remember { mutableStateOf(false) }

                var text by remember { mutableStateOf("") }
                var showMenu by remember { mutableStateOf(false) }

                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    leadingIcon = {
                        IconButton(
                            onClick = {
                                //Search
                                //TODO
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_search_24),
                                contentDescription = null
                            )
                        }
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { showMenu = !showMenu }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_filter_alt_24),
                                contentDescription = null
                            )
                        }
                    },
                    label = { Text("Kereső") },
                    modifier = Modifier
                        .padding(5.dp, 0.dp, 0.dp, 0.dp)
                        .constrainAs(searchbar) {
                            top.linkTo(item.bottom)
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
                        DropdownMenuItem(
                            onClick = {
                                //TODO
                            },
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text(text = "Név")
                        }
                        Divider(color = Color.LightGray, thickness = 1.dp)
                        DropdownMenuItem(
                            onClick = {
                                //TODO
                            },
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text(text = "Darabszám")
                        }
                        Divider(color = Color.LightGray, thickness = 1.dp)
                        DropdownMenuItem(
                            onClick = {
                                //TODO
                            },
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text(text = "Alacsony készlet")
                        }
                        Divider(color = Color.LightGray, thickness = 1.dp)
                        DropdownMenuItem(
                            onClick = {
                                //TODO
                            },
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text(text = "Készleten")
                        }
                    }
                }


                Box(
                    modifier = Modifier
                        .padding(0.dp, 25.dp, 0.dp, 0.dp)
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
                            .padding(0.dp, 0.dp, 0.dp, 15.dp)
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
                }
                else if(typeSwitchState){
                    val elem1 = Item(
                        id = "0",
                        name = "elem1",
                        category = Category("0", "kábel"),
                        manufacturer = "Dolog.Kft",
                        serialNumber = "0",
                        type = PackageType.Package,
                        quantityUnit = QuantityUnit.Meter,
                        defaultPackageQuantity = 1.0,
                        openable = false,
                        defaultPurchasePrice = null,
                        minimumStoredQuantity = null
                    )
                    val elem2 = Item(
                        id = "0",
                        name = "elem2",
                        category = Category("0", "kábel"),
                        manufacturer = "Dolog.Kft",
                        serialNumber = "0",
                        type = PackageType.Package,
                        quantityUnit = QuantityUnit.Meter,
                        defaultPackageQuantity = 1.0,
                        openable = false,
                        defaultPurchasePrice = null,
                        minimumStoredQuantity = null
                    )
                    val elem3 = Item(
                        id = "0",
                        name = "elem3",
                        category = Category("0", "kábel"),
                        manufacturer = "Dolog.Kft",
                        serialNumber = "0",
                        type = PackageType.Package,
                        quantityUnit = QuantityUnit.Meter,
                        defaultPackageQuantity = 1.0,
                        openable = false,
                        defaultPurchasePrice = null,
                        minimumStoredQuantity = null
                    )
                    val group = mutableListOf(elem1, elem2, elem3)

                    val groups = mutableListOf(group, group, group)

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
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
                            groups = groups,
                            onClicked = onClicked,
                            onReleaseClicked = onReleaseClicked
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ListItem(
    item: Item,
    onClicked: (Item) -> Unit
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
                text = item.name, color = Color.Black, fontSize = 18.sp
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