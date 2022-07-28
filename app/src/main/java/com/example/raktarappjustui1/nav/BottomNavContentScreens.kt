package com.example.raktarappjustui1.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.raktarappjustui1.ui.theme.Teal700

@Composable
fun ListScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Teal700)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Lista nézet",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}

@Composable
fun ImportScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Teal700)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Bevételező nézet",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}

@Composable
fun StatsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Teal700)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Statisztika nézet",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}

@Composable
fun AccountScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Teal700)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Fiók nézet",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}