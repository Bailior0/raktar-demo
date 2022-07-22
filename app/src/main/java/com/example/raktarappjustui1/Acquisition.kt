package com.example.raktarappjustui1

import android.app.DatePickerDialog
import android.app.RemoteInput
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.raktarappjustui1.ui.theme.RaktarAppJustUi1Theme
import org.w3c.dom.Text
import java.text.DateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun AcquisitionView() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = "Bevételezés") }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(12.dp, 24.dp, 12.dp, 0.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
            ) {
                val (title, name, group, quantitySwitch, quantity, date, ownerSwitch, button) = createRefs()

                var nameInput by remember { mutableStateOf("") }
                var groupInput by remember { mutableStateOf("") }
                var quantityInput by remember { mutableStateOf("") }
                var dateInput by remember { mutableStateOf("") }

                val myCalendar = Calendar.getInstance()
                var datePickerState by remember { mutableStateOf(false) }

                var ownerSwitchState by remember { mutableStateOf(false) }
                var quantitySwitchState by remember { mutableStateOf(false) }

                /*Text(
                    text = "Bevételezés",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 30.dp)
                        .constrainAs(title) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )*/

                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    singleLine = true,
                    placeholder = { Text("Terméknév", color = Color.Gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 2.dp)
                        .constrainAs(name) {
                            top.linkTo(title.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                OutlinedTextField(
                    value = groupInput,
                    onValueChange = { groupInput = it },
                    singleLine = true,
                    placeholder = { Text("Termékcsoport", color = Color.Gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 2.dp)
                        .constrainAs(group) {
                            top.linkTo(name.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                Box(
                    modifier = Modifier
                        .padding(5.dp, 25.dp, 0.dp, 0.dp)
                        .constrainAs(quantitySwitch) {
                            top.linkTo(group.bottom)
                            start.linkTo(parent.start)
                        }
                ) {
                    SegmentedControlQuantitySwitch(quantitySwitchState) { quantitySwitchState = it }
                }

                OutlinedTextField(
                    value = quantityInput,
                    onValueChange = { quantityInput = it },
                    singleLine = true,
                    placeholder = { Text("Mennyiség", color = Color.Gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 2.dp)
                        .constrainAs(quantity) {
                            top.linkTo(quantitySwitch.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp, 25.dp, 5.dp, 0.dp)
                        .constrainAs(date) {
                            top.linkTo(quantity.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
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

                        Button(
                            content = {
                                if (dateInput == "")
                                    Text(
                                        text = "${myCalendar.get(Calendar.YEAR)}-" +
                                                "${myCalendar.get(Calendar.MONTH) + 1}-" +
                                                "${myCalendar.get(Calendar.DAY_OF_MONTH)}"
                                    )
                                else
                                    Text(
                                        text = dateInput
                                    )
                            },
                            onClick = { datePickerState = true },
                            modifier = Modifier
                                .constrainAs(dateButton) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    end.linkTo(parent.end)
                                }
                        )

                        if (datePickerState) {
                            DatePickerComposable(context, dateInput, myCalendar) { dateInput = it }
                            datePickerState = false
                        }
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
                    SegmentedControlOwnerSwitch(ownerSwitchState) { ownerSwitchState = it }
                }

                Button(
                    content = {
                        Text(
                            text = "Bevételez",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(25.dp, 0.dp)
                        )
                    },
                    onClick = {
                        //TODO
                        Toast.makeText(context, "Bevételezés", Toast.LENGTH_LONG).show()
                    },
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

@Composable
fun DatePickerComposable(
    context: Context,
    dateInput: String,
    myCalendar: Calendar,
    onValueChange: (String) -> Unit
) {
    val datePickerDialog = DatePickerDialog(
        context,
        R.style.DatePickerTheme,
        { _: DatePicker, Year: Int, Month: Int, Day: Int ->
            onValueChange("$Year-${Month + 1}-$Day")
        },
        myCalendar.get(Calendar.YEAR),
        myCalendar.get(Calendar.MONTH),
        myCalendar.get(Calendar.DAY_OF_MONTH)
    )

    datePickerDialog.datePicker.minDate = System.currentTimeMillis()
    datePickerDialog.show()
}

@Composable
fun SegmentedControlQuantitySwitch(quantitySwitchState: Boolean, onValueChange: (Boolean) -> Unit) {
    //var index = remember { mutableStateOf(0) }

    Row() {
        Text(
            text = "CSOMAG",
            fontSize = 14.sp,
            color = if (!quantitySwitchState)
                Color.Black
            else Color.Gray,
            modifier = Modifier
                .clickable { onValueChange(false) }
        )

        Text(
            text = " | ",
            fontSize = 14.sp,
            color = Color.LightGray,
            modifier = Modifier
                .scale(1.1f)
        )

        Text(
            text = "EGYSÉG",
            fontSize = 14.sp,
            color = if (quantitySwitchState)
                Color.Black
            else Color.Gray,
            modifier = Modifier
                .clickable { onValueChange(true) }
        )
    }
}

@Composable
fun SegmentedControlOwnerSwitch(ownerSwitchState: Boolean, onValueChange: (Boolean) -> Unit) {
    //var index = remember { mutableStateOf(0) }

    Box(
        //modifier = Modifier
        //.border(1.dp, Color.Gray, RoundedCornerShape(5.dp))
        //.background(Color.Gray, RoundedCornerShape(5.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                content = {
                    Text(
                        text = "Saját",
                    )
                },
                onClick = {
                    //index.value = 0
                    onValueChange(false)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (!ownerSwitchState)
                        MaterialTheme.colors.primary
                    else Color.LightGray
                ),
                modifier = Modifier
                    .weight(5f)
                    .padding(2.dp, 0.dp)
            )

            Button(
                content = {
                    Text(
                        text = "Idegen",
                    )
                },
                onClick = {
                    onValueChange(true)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (ownerSwitchState)
                        MaterialTheme.colors.primary
                    else Color.LightGray
                ),
                modifier = Modifier
                    .weight(5f)
                    .padding(2.dp, 0.dp)
            )

        }
    }
}

@Preview
@Composable
fun AcquisitionPreview() {
    RaktarAppJustUi1Theme {
        AcquisitionView()
    }
}
