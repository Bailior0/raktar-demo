package com.example.raktarappjustui1

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.raktarappjustui1.ui.theme.RaktarAppJustUi1Theme

@Composable
fun Release() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        TopAppBar(
            title = { Text(text = "Kivezetés") }
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
                    acquisition,
                    item,
                    quantitySwitch,
                    quantity,
                    qualitySwitch,
                    button
                ) = createRefs()

                var acquisitionInput by remember { mutableStateOf("") }
                var itemInput by remember { mutableStateOf("") }
                var quantityInput by remember { mutableStateOf("") }

                var quantitySwitchState by remember { mutableStateOf(false) }
                var qualitySwitchState by remember { mutableStateOf(0) }

                OutlinedTextField(
                    value = acquisitionInput,
                    onValueChange = { acquisitionInput = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Beszerzés választása",
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 2.dp)
                        .constrainAs(acquisition) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                OutlinedTextField(
                    value = itemInput,
                    onValueChange = { itemInput = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Termék választása",
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 25.dp, 0.dp, 0.dp)
                        .constrainAs(item) {
                            top.linkTo(acquisition.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                Box(
                    modifier = Modifier
                        .padding(5.dp, 25.dp, 0.dp, 0.dp)
                        .constrainAs(quantitySwitch) {
                            top.linkTo(item.bottom)
                            start.linkTo(parent.start)
                        }
                ) {
                    SegmentedControlQuantitySwitch(quantitySwitchState) { quantitySwitchState = it }
                }

                OutlinedTextField(
                    value = quantityInput,
                    onValueChange = { quantityInput = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Mennyiség",
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 2.dp)
                        .constrainAs(quantity) {
                            top.linkTo(quantitySwitch.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                Box(
                    modifier = Modifier
                        .padding(0.dp, 25.dp, 0.dp, 0.dp)
                        .constrainAs(qualitySwitch) {
                            top.linkTo(quantity.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    SegmentedControlQualitySwitch(qualitySwitchState) { qualitySwitchState = it }
                }

                Button(
                    content = {
                        Text(
                            text = "Kivezet",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(25.dp, 0.dp)
                        )
                    },
                    onClick = {
                        //TODO
                        Toast.makeText(context, "Kivezetés", Toast.LENGTH_LONG).show()
                    },
                    modifier = Modifier
                        .scale(2f)
                        .padding(0.dp, 25.dp, 0.dp, 0.dp)
                        .constrainAs(button) {
                            top.linkTo(qualitySwitch.bottom)
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
fun ReleaseReleasePreview() {
    RaktarAppJustUi1Theme {
        Release()
    }
}