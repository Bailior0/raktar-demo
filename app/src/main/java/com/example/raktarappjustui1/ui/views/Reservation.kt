package com.example.raktarappjustui1.ui.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.raktarappjustui1.R
import com.example.raktarappjustui1.data.Item
import com.example.raktarappjustui1.ui.views.helpers.DatePicker
import com.example.raktarappjustui1.ui.views.theme.RaktarAppJustUi1Theme

@Composable
fun Reservation(
    item: Item,
    onIconClick: () -> Unit = {}
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        TopAppBar(
            title = { Text(text = "Foglalás") },
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
                    item,
                    availableQuantity,
                    quantityToReserve,
                    reservationGoal,
                    reservationGoalDate,
                    acquisition,
                    button
                ) = createRefs()

                var itemInput by remember { mutableStateOf("Termék") }
                var quantityInput by remember { mutableStateOf("") }
                var multiplierInput by remember { mutableStateOf("") }
                var reservationGoalInput by remember { mutableStateOf("") }
                var dateInput by remember { mutableStateOf("") }
                var acquisitionInput by remember { mutableStateOf("Beszerzés") }

                var datePickerState by remember { mutableStateOf(false) }

                var available = 124
                var unit = "db"

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(item) {
                            top.linkTo(parent.top)
                        }
                ) {
                    val (text, picker) = createRefs()

                    Text(
                        text = itemInput,
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
                        .constrainAs(acquisition) {
                            top.linkTo(item.bottom)
                        }
                ) {
                    val (text, picker) = createRefs()

                    Text(
                        text = acquisitionInput,
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

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Foglalható Mennyiség: ")
                        }

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("$available ")
                        }

                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("$unit")
                        }
                    },
                    modifier = Modifier
                        .padding(5.dp, 25.dp, 0.dp, 0.dp)
                        .constrainAs(availableQuantity) {
                            top.linkTo(acquisition.bottom)
                            start.linkTo(parent.start)
                        }
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(0.dp, 2.dp, 0.dp, 0.dp)
                        .constrainAs(quantityToReserve) {
                            top.linkTo(availableQuantity.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    OutlinedTextField(
                        value = quantityInput,
                        onValueChange = { quantityInput = it },
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = "Foglalandó mennyiség",
                                color = Color.Gray
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(2f)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_clear_24),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(2.dp, 0.dp)
                    )

                    OutlinedTextField(
                        value = multiplierInput,
                        onValueChange = { multiplierInput = it },
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = "Ismételve",
                                color = Color.Gray
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(1f)
                    )
                }

                OutlinedTextField(
                    value = reservationGoalInput,
                    onValueChange = { reservationGoalInput = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Foglalási cél",
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 25.dp, 0.dp, 0.dp)
                        .constrainAs(reservationGoal) {
                            top.linkTo(quantityToReserve.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )


                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp, 25.dp, 5.dp, 0.dp)
                        .constrainAs(reservationGoalDate) {
                            top.linkTo(reservationGoal.bottom)
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

                Button(
                    content = {
                        Text(
                            text = "Foglalás",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(25.dp, 0.dp)
                        )
                    },
                    onClick = {
                        //TODO
                        Toast.makeText(context, "Foglal", Toast.LENGTH_LONG).show()
                    },
                    modifier = Modifier
                        .scale(2f)
                        .padding(0.dp, 25.dp, 0.dp, 0.dp)
                        .constrainAs(button) {
                            top.linkTo(reservationGoalDate.bottom)
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
fun ReservationPreview() {
    RaktarAppJustUi1Theme {
        Reservation()
    }
}*/