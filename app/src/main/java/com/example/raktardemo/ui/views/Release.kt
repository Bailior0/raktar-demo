package com.example.raktardemo.ui.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.raktardemo.R
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.ui.views.helpers.SegmentedControlQualitySwitch
import com.example.raktardemo.ui.views.helpers.SegmentedControlQuantitySwitch

@Composable
fun Release(
    product: StoredItem?,
    group: List<StoredItem?>,
    acqId: String?,
    onIconClick: () -> Unit = {},
    onReleaseClick: () -> Unit
) {
    val context = LocalContext.current

    Log.i("dolog", "$group $acqId")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        TopAppBar(
            title = { Text(text = "Kivezetés") },
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
                    acquisition,
                    quantitySwitch,
                    movableQuantity,
                    quantity,
                    qualitySwitch,
                    button
                ) = createRefs()

                var acquisitionInput by remember { mutableStateOf("Beszerzés") }
                var itemInput by remember { mutableStateOf("Termék") }
                var quantityInput by remember { mutableStateOf("") }

                var quantitySwitchState by remember { mutableStateOf(false) }
                var qualitySwitchState by remember { mutableStateOf(0) }

                var movable = 4
                var unit = "db"

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
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .constrainAs(item) {
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                            }
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

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Mozgatható: ")
                        }

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("$movable")
                        }

                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("$unit")
                        }
                    },
                    maxLines = 2,
                    textAlign = TextAlign.End,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .width((LocalConfiguration.current.screenWidthDp - 170).dp)
                        .padding(5.dp, 25.dp, 5.dp, 0.dp)
                        .constrainAs(movableQuantity) {
                            bottom.linkTo(quantity.top)
                            end.linkTo(parent.end)
                        }
                )

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
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                    //TODO
                    onClick = onReleaseClick,
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

/*@Preview
@Composable
fun ReleaseReleasePreview() {
    RaktarAppJustUi1Theme {
        Release()
    }
}*/