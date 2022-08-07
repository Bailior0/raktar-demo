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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.raktardemo.R
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.ui.views.helpers.ComboBox
import com.example.raktardemo.ui.views.helpers.DatePicker
import com.example.raktardemo.ui.views.helpers.SegmentedControlQuantitySwitch
import com.example.raktardemo.ui.views.helpers.SegmentedControlTwoWaySwitch

@Composable
fun Acquisition(
    product: StoredItem,
    onIconClick: () -> Unit = {},
    onAcquisitionClick: () -> Unit = {}
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        TopAppBar(
            title = { Text(text = "Beszerzés") },
            navigationIcon = {
                IconButton(
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = null
                        )
                    },
                    onClick = onIconClick
                )
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(12.dp, 25.dp, 12.dp, 100.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val (
                    itemLabel,
                    item,
                    warehouse,
                    quantitySwitch,
                    quantity,
                    price,
                    date,
                    ownerSwitch,
                    button
                ) = createRefs()

                var quantityInput by remember { mutableStateOf("") }
                var dateInput by remember { mutableStateOf("") }
                var priceInput by remember { mutableStateOf("") }

                var ownerSwitchState by remember { mutableStateOf(false) }
                var quantitySwitchState by remember { mutableStateOf(false) }

                var datePickerState by remember { mutableStateOf(false) }

                val warehouseList = listOf("Raktár1", "Raktár2", "Raktár3")
                var warehouseExpanded by remember { mutableStateOf(false) }
                var warehouseSelectedIndex by remember { mutableStateOf(0) }

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

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 15.dp, 0.dp, 0.dp)
                        .constrainAs(warehouse) {
                            top.linkTo(item.bottom)
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
                            warehouseSelectedIndex,
                            { warehouseSelectedIndex = it },
                            warehouseExpanded,
                            { warehouseExpanded = it },
                            60.dp
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(5.dp, 25.dp, 0.dp, 0.dp)
                        .constrainAs(quantitySwitch) {
                            top.linkTo(warehouse.bottom)
                            start.linkTo(parent.start)
                        }
                ) {
                    SegmentedControlQuantitySwitch(quantitySwitchState) { quantitySwitchState = it }
                }

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
                        .padding(0.dp, 2.dp)
                        .constrainAs(quantity) {
                            top.linkTo(quantitySwitch.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                OutlinedTextField(
                    value = priceInput,
                    onValueChange = { priceInput = it },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = {
                        Text(
                            text = "Beszerzési ár",
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 2.dp)
                        .constrainAs(price) {
                            top.linkTo(quantity.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp, 25.dp, 5.dp, 0.dp)
                        .constrainAs(date) {
                            top.linkTo(price.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    val (text, dateButton) = createRefs()

                    Text(
                        text = "Szavatosság: ",
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
                            .constrainAs(dateButton) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                end.linkTo(parent.end)
                            }
                    ) {
                        DatePicker(
                            fromToday = true,
                            context,
                            datePickerState,
                            { datePickerState = it },
                            dateInput,
                            { dateInput = it }
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(0.dp, 25.dp, 0.dp, 0.dp)
                        .constrainAs(ownerSwitch) {
                            top.linkTo(date.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    SegmentedControlTwoWaySwitch(
                        "Saját",
                        "Idegen",
                        ownerSwitchState
                    ) { ownerSwitchState = it }
                }

                Button(
                    content = {
                        Text(
                            text = "Beszerez",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(25.dp, 0.dp)
                        )
                    },
                    //TODO
                    onClick = onAcquisitionClick,
                    modifier = Modifier
                        .scale(2f)
                        .padding(0.dp, 25.dp, 0.dp, 0.dp)
                        .constrainAs(button) {
                            top.linkTo(ownerSwitch.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
            }
        }
    }
}


