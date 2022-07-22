package com.example.raktarappjustui1

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.raktarappjustui1.ui.theme.RaktarAppJustUi1Theme

@Composable
fun Reservation() {
    Text(
        "Foglalás",
    )
}

@Preview
@Composable
fun ReservationPreview() {
    RaktarAppJustUi1Theme {
        Reservation()
    }
}