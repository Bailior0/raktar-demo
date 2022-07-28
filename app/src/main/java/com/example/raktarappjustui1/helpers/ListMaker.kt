package com.example.raktarappjustui1

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun ListMaker(text1: String, text2: String, text3: String) {
    return ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val (first, second, third, divider) = createRefs()

        Text(
            text = text1,
            modifier = Modifier
                .padding(5.dp, 0.dp, 0.dp, 0.dp)
                .constrainAs(first) {
                    start.linkTo(parent.start)
                }
        )

        Text(
            text = text2,
            modifier = Modifier
                .constrainAs(second) {
                    end.linkTo(third.start)
                }
        )
        Text(
            text = text3,
            modifier = Modifier
                .padding(20.dp, 0.dp, 5.dp, 0.dp)
                .constrainAs(third) {
                    end.linkTo(parent.end)
                }
        )

        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier
                .padding(5.dp, 0.dp, 5.dp, 0.dp)
                .constrainAs(divider) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}