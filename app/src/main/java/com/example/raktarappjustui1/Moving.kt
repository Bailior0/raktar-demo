package com.example.raktarappjustui1

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.raktarappjustui1.ui.theme.RaktarAppJustUi1Theme

@Composable
fun Moving() {
    Text(
        "Mozgatás",
    )
}

@Preview
@Composable
fun MovingPreview() {
    RaktarAppJustUi1Theme {
        Moving()
    }
}