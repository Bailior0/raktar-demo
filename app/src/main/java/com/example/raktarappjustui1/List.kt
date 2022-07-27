package com.example.raktarappjustui1

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.raktarappjustui1.data.Item
import com.example.raktarappjustui1.ui.theme.RaktarAppJustUi1Theme
import com.example.raktarappjustui1.ui.theme.Shapes

@Composable
fun List(
    items: List<Item>
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
                .padding(12.dp, 10.dp, 12.dp, 0.dp)
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
                var ownerSwitchState by remember { mutableStateOf(false) }

                var text by remember { mutableStateOf("") }
                var showMenu by remember { mutableStateOf(false) }

                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    leadingIcon = {
                        IconButton(
                            onClick = { }
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
                        DropdownMenuItem(onClick = { }) {
                            Text(text = "Név")
                        }
                        DropdownMenuItem(onClick = { }) {
                            Text(text = "Darabszám")
                        }
                        DropdownMenuItem(onClick = { }) {
                            Text(text = "Alacsony készlet")
                        }
                        DropdownMenuItem(onClick = { }) {
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
                        ownerSwitchState
                    ) { ownerSwitchState = it }
                }


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
                            onClicked = { /*::onStorageSelected*/ }
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

@Composable
fun SegmentedControlTwoWaySwitch(text1: String, text2: String, switchState: Boolean, onValueChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            content = {
                Text(
                    text = text1,
                )
            },
            onClick = {
                onValueChange(false)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (!switchState)
                    MaterialTheme.colors.primary
                else Color.LightGray
            ),
            modifier = Modifier
                .weight(1f)
                .padding(2.dp, 0.dp)
        )

        Button(
            content = {
                Text(
                    text = text2,
                )
            },
            onClick = {
                onValueChange(true)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (switchState)
                    MaterialTheme.colors.primary
                else Color.LightGray
            ),
            modifier = Modifier
                .weight(1f)
                .padding(2.dp, 0.dp)
        )

    }

}


@Preview
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

        List(mutableListOf(elem1, elem2, elem3))
    }
}