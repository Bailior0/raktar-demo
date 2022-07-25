package com.example.raktarappjustui1

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import java.util.*

@Composable
fun DatePickerRow(
    label: String,
    context: Context,
    datePickerState: Boolean,
    setDatePickerState: (Boolean) -> Unit,
    dateInput: String,
    onValueChange: (String) -> Unit
) {
    val myCalendar = Calendar.getInstance()

    ConstraintLayout(
    modifier = Modifier
    .fillMaxWidth()
    ) {
        val (text, dateButton) = createRefs()

        Text(
            text = label,
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
            onClick = { setDatePickerState(true) },
            modifier = Modifier
                .constrainAs(dateButton) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
        )

        if (datePickerState) {
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

            setDatePickerState(false)
        }
    }
}
