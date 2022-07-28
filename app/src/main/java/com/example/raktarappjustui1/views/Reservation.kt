package com.example.raktarappjustui1

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.raktarappjustui1.ui.theme.RaktarAppJustUi1Theme

@Composable
fun Reservation() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        TopAppBar(
            title = { Text(text = "Foglalás") }
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
                    availableQuantity,
                    quantityToReserve,
                    reservationGoal,
                    reservationGoalDate,
                    acquisition,
                    button
                ) = createRefs()

                var itemInput by remember { mutableStateOf("") }
                var quantityInput by remember { mutableStateOf("") }
                var multiplierInput by remember { mutableStateOf("") }
                var reservationGoalInput by remember { mutableStateOf("") }
                var dateInput by remember { mutableStateOf("") }
                var acquisitionInput by remember { mutableStateOf("") }

                var datePickerState by remember { mutableStateOf(false) }

                var available = 124

                OutlinedTextField(
                    value = itemInput,
                    onValueChange = { itemInput = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Terméknév",
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

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Foglalható Mennyiség: ")
                        }

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("$available ")
                        }

                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("db")
                        }
                    },
                    modifier = Modifier
                        .padding(5.dp, 25.dp, 0.dp, 0.dp)
                        .constrainAs(availableQuantity) {
                            top.linkTo(item.bottom)
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

                OutlinedTextField(
                    value = acquisitionInput,
                    onValueChange = { acquisitionInput = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Beszerzés (opcionális)",
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 25.dp, 0.dp, 0.dp)
                        .constrainAs(acquisition) {
                            top.linkTo(reservationGoalDate.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

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
                        Toast.makeText(context, "Foglalás", Toast.LENGTH_LONG).show()
                    },
                    modifier = Modifier
                        .scale(2f)
                        .padding(0.dp, 25.dp, 0.dp, 0.dp)
                        .constrainAs(button) {
                            top.linkTo(acquisition.bottom)
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
fun ReservationPreview() {
    RaktarAppJustUi1Theme {
        Reservation()
    }
}