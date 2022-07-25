package com.example.raktarappjustui1

import android.media.Image
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.raktarappjustui1.ui.theme.RaktarAppJustUi1Theme

@Composable
fun Moving() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        TopAppBar(
            title = { Text(text = "Mozgatás") }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(12.dp, 25.dp, 12.dp, 0.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val (
                    item,
                    quantitySwitch,
                    quantity,
                    currentWarehouse,
                    destinationWarehouse,
                    button
                ) = createRefs()

                var itemInput by remember { mutableStateOf("") }
                var quantityInput by remember { mutableStateOf("") }

                var quantitySwitchState by remember { mutableStateOf(false) }

                var currentExpanded by remember { mutableStateOf(false) }
                val currentWarehouseList = listOf("Raktár1", "Raktár2", "Raktár3")
                var currentWarehouseSelectedIndex by remember { mutableStateOf(0) }

                var destinationExpanded by remember { mutableStateOf(false) }
                val destinationWarehouseList = listOf("Raktár1", "Raktár2", "Raktár3")
                var destinationWarehouseSelectedIndex by remember { mutableStateOf(0) }

                OutlinedTextField(
                    value = itemInput,
                    onValueChange = { itemInput = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Termék választása",
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(item) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
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
                        .padding(0.dp, 2.dp, 0.dp, 0.dp)
                        .constrainAs(quantity) {
                            top.linkTo(quantitySwitch.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp, 25.dp, 5.dp, 0.dp)
                        .constrainAs(currentWarehouse) {
                            top.linkTo(quantity.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    val (text, comboBox) = createRefs()

                    Text(
                        text = "Forrás raktár: ",
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
                        Button(
                            content = {
                                Row() {
                                    Text(
                                        text = currentWarehouseList[currentWarehouseSelectedIndex],
                                    )

                                    Image(
                                        painter = painterResource(
                                            id = R.drawable.ic_baseline_keyboard_arrow_down_24
                                        ),
                                        contentDescription = null,
                                    )
                                }
                            },
                            onClick = { currentExpanded = true },
                        )

                        DropdownMenu(
                            expanded = currentExpanded,
                            onDismissRequest = { currentExpanded = false },
                        ) {
                            currentWarehouseList.forEachIndexed { index, s ->
                                DropdownMenuItem(
                                    onClick = {
                                        currentWarehouseSelectedIndex = index
                                        currentExpanded = false
                                    }
                                ) {
                                    Text(text = s)
                                }
                            }
                        }
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
                        text = "Cél raktár: ",
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
                        Button(
                            content = {
                                Row() {
                                    Text(
                                        text = destinationWarehouseList[destinationWarehouseSelectedIndex],
                                    )

                                    Image(
                                        painter = painterResource(
                                            id = R.drawable.ic_baseline_keyboard_arrow_down_24
                                        ),
                                        contentDescription = null,
                                    )
                                }
                            },
                            onClick = { destinationExpanded = true },
                        )

                        DropdownMenu(
                            expanded = destinationExpanded,
                            onDismissRequest = { destinationExpanded = false },
                        ) {
                            currentWarehouseList.forEachIndexed { index, s ->
                                DropdownMenuItem(
                                    onClick = {
                                        destinationWarehouseSelectedIndex = index
                                        destinationExpanded = false
                                    }
                                ) {
                                    Text(text = s)
                                }
                            }
                        }
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
                        Toast.makeText(context, "Mozgatás", Toast.LENGTH_LONG).show()
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

@Preview
@Composable
fun MovingPreview() {
    RaktarAppJustUi1Theme {
        Moving()
    }
}