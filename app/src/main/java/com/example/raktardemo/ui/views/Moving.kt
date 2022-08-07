package com.example.raktardemo.ui.views

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.raktardemo.R
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.ui.views.helpers.ComboBox
import com.example.raktardemo.ui.views.helpers.SegmentedControlQuantitySwitch
import com.example.raktardemo.ui.views.helpers.SegmentedControlTwoWaySwitch

@Composable
fun Moving(
    product: StoredItem,
    onIconClick: () -> Unit = {},
    onMovingClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        TopAppBar(
            title = { Text(text = "Mozgatás") },
            navigationIcon = {
                IconButton(
                    content = { Icon(painter = painterResource(id = R.drawable.baseline_arrow_back_24), contentDescription = null) },
                    onClick = onIconClick
                )
            }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(12.dp, 25.dp, 12.dp, 100.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val (
                    itemLabel,
                    item,
                    quantitySwitch,
                    movableQuantity,
                    quantity,
                    switchText,
                    switch,
                    currentWarehouse,
                    destinationWarehouse,
                    button
                ) = createRefs()

                var quantityInput by remember { mutableStateOf("") }

                var quantitySwitchState by remember { mutableStateOf(false) }

                val warehouseList = listOf("Raktár1", "Raktár2", "Raktár3")
                var curentWarehouseExpanded by remember { mutableStateOf(false) }
                var curentWarehouseSelectedIndex by remember { mutableStateOf(0) }

                var destinationWarehouseExpanded by remember { mutableStateOf(false) }
                var destinationWarehouseSelectedIndex by remember { mutableStateOf(1) }

                var movable = 4
                var unit = "db"

                var packageSwitchState by remember { mutableStateOf(false) }

                Text(
                    text = "Termék: ",
                    color = Color.Gray,
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .constrainAs(itemLabel) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                )

                Text(
                    text = product.item.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .width(((LocalConfiguration.current.screenWidthDp / 2) + 60).dp)
                        .constrainAs(item) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                )

                Box(
                    modifier = Modifier
                        .padding(5.dp, 25.dp, 0.dp, 0.dp)
                        .constrainAs(quantitySwitch) {
                            top.linkTo(item.bottom)
                            start.linkTo(parent.start)
                        }
                ) {
                    SegmentedControlQuantitySwitch(quantitySwitchState) { quantitySwitchState = it }
                }

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Mozgatható: ")
                        }

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("$movable")
                        }

                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("$unit")
                        }
                    },
                    maxLines = 2,
                    textAlign = TextAlign.End,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .width((LocalConfiguration.current.screenWidthDp - 170).dp)
                        .padding(5.dp, 25.dp, 5.dp, 0.dp)
                        .constrainAs(movableQuantity) {
                            bottom.linkTo(quantity.top)
                            end.linkTo(parent.end)
                        }
                )

                OutlinedTextField(
                    value = quantityInput,
                    onValueChange = { quantityInput = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Mennyiség",
                            color = Color.Gray
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 2.dp, 0.dp, 0.dp)
                        .constrainAs(quantity) {
                            top.linkTo(quantitySwitch.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                if (!quantitySwitchState) {
                    Text(
                        text = "Csomag állapota",
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(5.dp, 15.dp, 5.dp, 0.dp)
                            .constrainAs(switchText) {
                                top.linkTo(quantity.bottom)
                                start.linkTo(parent.start)
                            }
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp, 0.dp, 5.dp, 0.dp)
                            .constrainAs(switch) {
                                top.linkTo(switchText.bottom)
                            }
                    ) {
                        SegmentedControlTwoWaySwitch(
                            text1 = "Bontatlan",
                            text2 = "Bontott",
                            switchState = packageSwitchState,
                            onValueChange = { packageSwitchState = it }
                        )
                    }
                }

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp, 25.dp, 5.dp, 0.dp)
                        .constrainAs(currentWarehouse) {
                            top.linkTo(switch.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    val (text, comboBox) = createRefs()

                    Text(
                        text = "Raktár választása: ",
                        color = Color.Gray,
                        modifier = Modifier
                            .constrainAs(text) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                            }
                    )

                    Box(
                        modifier = Modifier
                            .constrainAs(comboBox) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                end.linkTo(parent.end)
                            }
                    ) {
                        ComboBox(
                            warehouseList,
                            curentWarehouseSelectedIndex,
                            {
                                if (destinationWarehouseSelectedIndex != it )
                                    curentWarehouseSelectedIndex = it
                            },
                            curentWarehouseExpanded,
                            { curentWarehouseExpanded = it },
                            60.dp
                        )
                    }
                }

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp, 5.dp, 5.dp, 0.dp)
                        .constrainAs(destinationWarehouse) {
                            top.linkTo(currentWarehouse.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    val (text, comboBox) = createRefs()

                    Text(
                        text = "Raktár választása: ",
                        color = Color.Gray,
                        modifier = Modifier
                            .constrainAs(text) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                            }
                    )

                    Box(
                        modifier = Modifier
                            .constrainAs(comboBox) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                end.linkTo(parent.end)
                            }
                    ) {
                        ComboBox(
                            warehouseList,
                            destinationWarehouseSelectedIndex,
                            {
                                if (it != curentWarehouseSelectedIndex)
                                    destinationWarehouseSelectedIndex = it
                            },
                            destinationWarehouseExpanded,
                            { destinationWarehouseExpanded = it },
                            60.dp
                        )
                    }
                }

                Button(
                    content = {
                        Text(
                            text = "Mozgat",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(25.dp, 0.dp)
                        )
                    },
                    onClick = {
                        //TODO
                        onMovingClick()
                    },
                    modifier = Modifier
                        .scale(2f)
                        .padding(0.dp, 25.dp, 0.dp, 0.dp)
                        .constrainAs(button) {
                            top.linkTo(destinationWarehouse.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
            }
        }
    }
}

/*@Preview
@Composable
fun MovingPreview() {
    RaktarAppJustUi1Theme {
        Moving()
    }
}*/