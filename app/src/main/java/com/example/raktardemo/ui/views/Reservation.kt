package com.example.raktardemo.ui.views

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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.raktardemo.R
import com.example.raktardemo.data.model.Reservation
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.ui.views.helpers.DatePicker
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Reservation(
    product: StoredItem?,
    group: List<StoredItem>,
    acqId: String?,
    onIconClick: () -> Unit = {},
    onReservationClick: (Reservation, StoredItem?, String?, List<StoredItem>) -> Unit
) {
    val context = LocalContext.current

    var quantityInput by remember { mutableStateOf("") }
    var multiplierInput by remember { mutableStateOf("") }
    var reservationGoalInput by remember { mutableStateOf("") }
    var dateInput by remember { mutableStateOf(SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())) }

    var datePickerState by remember { mutableStateOf(false) }

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
                    itemLabel,
                    item,
                    availableQuantity,
                    quantityToReserve,
                    reservationGoal,
                    reservationGoalDate,
                    button
                ) = createRefs()

                if (product == null){
                    Text(
                        text = "Beszerzés: ",
                        color = Color.Gray,
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .width(((LocalConfiguration.current.screenWidthDp / 2) + 50).dp)
                            .constrainAs(itemLabel) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                            }
                    )

                    Text(
                        text = acqId ?: "null",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .constrainAs(item) {
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                            }
                            .padding(top = 40.dp)
                    )
                } else {
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
                }

                if(product != null) {
                    Text(
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.Gray)) {
                                append("Foglalható Mennyiség: ")
                            }

                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(product.freeQuantity.toString())
                            }

                            withStyle(style = SpanStyle(color = Color.Gray)) {
                                append(" " + product.item.quantityUnit.toString())
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
                    modifier = when(product != null){
                        true -> Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 25.dp, 0.dp, 0.dp)
                            .constrainAs(reservationGoal) {
                                top.linkTo(quantityToReserve.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                        false -> Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 25.dp, 0.dp, 0.dp)
                            .constrainAs(reservationGoal) {
                                top.linkTo(item.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
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
                        text = "Foglalási céldátum: ",
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
                        val quantity = when(quantityInput != "" && quantityInput.toDouble() > 0.0){
                            true -> quantityInput.toDouble()
                            false -> 0.0
                        }

                        val multiplier = when(multiplierInput != "" && multiplierInput.toInt() > 0){
                            true -> multiplierInput.toInt()
                            false -> 1
                        }

                        if(quantity * multiplier > product?.freeQuantity!!)
                            Toast.makeText(context, "Nem megfelelő a mennyiség értéke!", Toast.LENGTH_SHORT).show()

                        else
                            onReservationClick(
                                Reservation(
                                    reservationGoal = reservationGoalInput,
                                    reservationDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date()),
                                    reservationGoalDate = dateInput,
                                    reservationQuantity = quantity,
                                    cancelled = false,
                                    repeatAmount = multiplier
                                ),
                                product,
                                acqId,
                                group
                            )
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