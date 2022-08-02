package com.example.raktarappjustui1.ui.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.raktarappjustui1.ui.views.helpers.ComboBox
import com.example.raktarappjustui1.ui.views.helpers.DatePicker
import com.example.raktarappjustui1.ui.views.helpers.SegmentedControlQuantitySwitch
import com.example.raktarappjustui1.ui.views.helpers.SegmentedControlTwoWaySwitch

@Composable
fun NewItem() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        TopAppBar(
            title = { Text(text = "Új termék hozzáadása") }
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(12.dp, 25.dp, 12.dp, 30.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val (
                    itemLabel,
                    divider,
                    name,
                    group,
                    manufacturer,
                    serial,
                    defaultPrice,
                ) = createRefs()

                var nameInput by remember { mutableStateOf("") }
                var groupInput by remember { mutableStateOf("") }
                var manufacturerInput by remember { mutableStateOf("") }
                var serialInput by remember { mutableStateOf("") }
                var defaultPriceInput by remember { mutableStateOf("") }

                Text(
                    text = "Termék adatok",
                    color = Color.Gray,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .constrainAs(itemLabel) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                )

                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 10.dp)
                        .constrainAs(divider) {
                            top.linkTo(itemLabel.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Terméknév",
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 2.dp)
                        .constrainAs(name) {
                            top.linkTo(divider.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                OutlinedTextField(
                    value = groupInput,
                    onValueChange = { groupInput = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Termékcsoport",
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 2.dp)
                        .constrainAs(group) {
                            top.linkTo(name.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                OutlinedTextField(
                    value = manufacturerInput,
                    onValueChange = { manufacturerInput = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Gyártó",
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 2.dp)
                        .constrainAs(manufacturer) {
                            top.linkTo(group.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                OutlinedTextField(
                    value = serialInput,
                    onValueChange = { serialInput = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Sorozatszám",
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 2.dp)
                        .constrainAs(serial) {
                            top.linkTo(manufacturer.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                OutlinedTextField(
                    value = defaultPriceInput,
                    onValueChange = { defaultPriceInput = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Alapért. beszerzési ár",
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 2.dp)
                        .constrainAs(defaultPrice) {
                            top.linkTo(serial.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
            }

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 25.dp, 0.dp, 0.dp)
            ) {
                val (
                    packagingLabel,
                    divider,
                    quantitySwitch,
                    quantity,
                    unitOfQuantity,
                    packageSize,
                    openableSwitch,
                ) = createRefs()

                var quantityInput by remember { mutableStateOf("") }
                var unitOfQuantityInput by remember { mutableStateOf("") }
                var packageSizeInput by remember { mutableStateOf("") }

                var quantitySwitchState by remember { mutableStateOf(false) }
                var openableSwitchState by remember { mutableStateOf(false) }

                Text(
                    text = "Csomagolási adatok",
                    color = Color.Gray,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .constrainAs(packagingLabel) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                )

                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 10.dp)
                        .constrainAs(divider) {
                            top.linkTo(packagingLabel.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                Box(
                    modifier = Modifier
                        .padding(5.dp, 2.dp, 0.dp, 0.dp)
                        .constrainAs(quantitySwitch) {
                            top.linkTo(divider.bottom)
                            start.linkTo(parent.start)
                        }
                ) {
                    SegmentedControlQuantitySwitch(
                        quantitySwitchState = quantitySwitchState,
                        onValueChange = { quantitySwitchState = it }
                    )
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
                    value = unitOfQuantityInput,
                    onValueChange = { unitOfQuantityInput = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Mennyiség egysége",
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 2.dp)
                        .constrainAs(unitOfQuantity) {
                            top.linkTo(quantity.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                if (!quantitySwitchState) {
                    OutlinedTextField(
                        value = packageSizeInput,
                        onValueChange = { packageSizeInput = it },
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = "Csomag mérete",
                                color = Color.Gray
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 2.dp)
                            .constrainAs(packageSize) {
                                top.linkTo(unitOfQuantity.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(openableSwitch) {
                            top.linkTo(
                                if (!quantitySwitchState)
                                    packageSize.bottom
                                else unitOfQuantity.bottom
                            )
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    SegmentedControlTwoWaySwitch(
                        text1 = "Bontható",
                        text2 = "Nem bontható",
                        switchState = openableSwitchState,
                        onValueChange = { openableSwitchState = it }
                    )
                }
            }

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 25.dp, 0.dp, 0.dp)
            ) {
                val (
                    storageLabel,
                    divider,
                    warehouse,
                    date,
                    ownerSwitch,
                    minQuantity,
                ) = createRefs()

                var dateInput by remember { mutableStateOf("") }
                var minQuantityInput by remember { mutableStateOf("") }

                var ownerSwitchState by remember { mutableStateOf(false) }
                var datePickerState by remember { mutableStateOf(false) }

                val warehouseList = listOf("Raktár1", "Raktár2", "Raktár3")
                var warehouseExpanded by remember { mutableStateOf(false) }
                var warehouseSelectedIndex by remember { mutableStateOf(0) }

                Text(
                    text = "Raktározás",
                    color = Color.Gray,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .constrainAs(storageLabel) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                )

                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 10.dp)
                        .constrainAs(divider) {
                            top.linkTo(storageLabel.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp, 2.dp, 5.dp, 0.dp)
                        .constrainAs(warehouse) {
                            top.linkTo(divider.bottom)
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

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp, 0.dp, 5.dp, 0.dp)
                        .constrainAs(date) {
                            top.linkTo(warehouse.bottom)
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

                OutlinedTextField(
                    value = minQuantityInput,
                    onValueChange = { minQuantityInput = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Min. tárolt mennyiség",
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 2.dp, 0.dp, 0.dp)
                        .constrainAs(minQuantity) {
                            top.linkTo(date.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                Box(
                    modifier = Modifier
                        .constrainAs(ownerSwitch) {
                            top.linkTo(minQuantity.bottom)
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
            }

            Button(
                content = {
                    Text(
                        text = "Hozzáad",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(25.dp, 0.dp)
                    )
                },
                onClick = {
                    //TODO
                    Toast.makeText(context, "Hozzáadás", Toast.LENGTH_LONG).show()
                },
                modifier = Modifier
                    .scale(2f)
                    .padding(0.dp, 40.dp, 0.dp, 50.dp)
            )
        }
    }
}

/*@Preview
@Composable
fun NewItemPreview() {
    RaktarAppJustUi1Theme {
        NewItem()
    }
}*/