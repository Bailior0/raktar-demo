package com.example.raktarappjustui1

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SegmentedControlQuantitySwitch(quantitySwitchState: Boolean, onValueChange: (Boolean) -> Unit) {
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
fun SegmentedControlTwoWaySwitch(text1: String, text2: String, switchState: Boolean, onValueChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            content = {
                Text(
                    text = text1,
                )
            },
            onClick = {
                onValueChange(false)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (!switchState)
                    MaterialTheme.colors.primary
                else Color.LightGray
            ),
            modifier = Modifier
                .weight(1f)
                .padding(2.dp, 0.dp)
        )

        Button(
            content = {
                Text(
                    text = text2,
                )
            },
            onClick = {
                onValueChange(true)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (switchState)
                    MaterialTheme.colors.primary
                else Color.LightGray
            ),
            modifier = Modifier
                .weight(1f)
                .padding(2.dp, 0.dp)
        )

    }

}

@Composable
fun SegmentedControlQualitySwitch(qualitySwitchState: Int, onValueChange: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            content = {
                Text(
                    text = "Jó",
                )
            },
            onClick = {
                onValueChange(0)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (qualitySwitchState == 0)
                    MaterialTheme.colors.primary
                else Color.LightGray
            ),
            modifier = Modifier
                .weight(1f)
                .padding(2.dp, 0.dp)
        )

        Button(
            content = {
                Text(
                    text = "Hibás",
                )
            },
            onClick = {
                onValueChange(1)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (qualitySwitchState == 1)
                    MaterialTheme.colors.primary
                else Color.LightGray
            ),
            modifier = Modifier
                .weight(1f)
                .padding(2.dp, 0.dp)
        )

        Button(
            content = {
                Text(
                    text = "Egyéb",
                )
            },
            onClick = {
                onValueChange(2)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (qualitySwitchState == 2)
                    MaterialTheme.colors.primary
                else Color.LightGray
            ),
            modifier = Modifier
                .weight(1f)
                .padding(2.dp, 0.dp)
        )
    }

}