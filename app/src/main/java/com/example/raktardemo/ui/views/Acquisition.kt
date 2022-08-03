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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.raktardemo.R
import com.example.raktardemo.data.model.StoredItem
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
                    content = { Icon(painter = painterResource(id = R.drawable.baseline_arrow_back_24), contentDescription = null) },
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
                    product,
                    warehouse,
                    quantitySwitch,
                    quantity,
                    price,
                    date,
                    ownerSwitch,
                    button
                ) = createRefs()

                var productInput by remember { mutableStateOf("Termék") }
                var warehouseInput by remember { mutableStateOf("Raktár") }
                var quantityInput by remember { mutableStateOf("") }
                var dateInput by remember { mutableStateOf("") }
                var priceInput by remember { mutableStateOf("") }

                var ownerSwitchState by remember { mutableStateOf(false) }
                var quantitySwitchState by remember { mutableStateOf(false) }

                var datePickerState by remember { mutableStateOf(false) }

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 2.dp)
                        .constrainAs(product) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    val (text, picker) = createRefs()

                    Text(
                        text = productInput,
                        color = Color.Gray,
                        modifier = Modifier
                            .width((LocalConfiguration.current.screenWidthDp - 150).dp)
                            .constrainAs(text) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                            }
                    )

                    Button(
                        content = {
                            Text(text = "Választ")

                            Image(
                                painter = painterResource(id = R.drawable.ic_baseline_chevron_right_24),
                                contentDescription = null
                            )
                        },
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .constrainAs(picker) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                end.linkTo(parent.end)
                            }
                    )
                }

                ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 2.dp)
                            .constrainAs(warehouse) {
                                top.linkTo(product.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                        ) {
                    val (text, picker) = createRefs()

                    Text(
                        text = warehouseInput,
                        color = Color.Gray,
                        modifier = Modifier
                            .width((LocalConfiguration.current.screenWidthDp - 150).dp)
                            .constrainAs(text) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                            }
                    )

                    Button(
                        content = {
                            Text(text = "Választ")

                            Image(
                                painter = painterResource(id = R.drawable.ic_baseline_chevron_right_24),
                                contentDescription = null
                            )
                        },
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .constrainAs(picker) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                end.linkTo(parent.end)
                            }
                    )
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


