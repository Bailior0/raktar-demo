package com.example.raktardemo.ui.views

import android.widget.Toast
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
import com.example.raktardemo.data.enums.PackageState
import com.example.raktardemo.data.enums.PackageType
import com.example.raktardemo.data.enums.Quality
import com.example.raktardemo.data.model.Release
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.ui.views.helpers.SegmentedControlQualitySwitch
import com.example.raktardemo.ui.views.helpers.SegmentedControlTwoWaySwitch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Release(
    product: StoredItem?,
    group: List<StoredItem>,
    acqId: String?,
    onIconClick: () -> Unit = {},
    onReleaseClick: (Release, StoredItem?, String?, List<StoredItem>) -> Unit
) {
    val context = LocalContext.current

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
                    movableQuantity,
                    quantity,
                    switchText,
                    switch,
                    qualitySwitch,
                    button
                ) = createRefs()

                var quantityInput by remember { mutableStateOf("") }

                var qualitySwitchState by remember { mutableStateOf(0) }

                var packageSwitchState by remember { mutableStateOf(false) }

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
                                append("Kivezethető: ")
                            }

                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(product.freeQuantity.toString())
                            }

                            withStyle(style = SpanStyle(color = Color.Gray)) {
                                append(" " + product.item.quantityUnit.translation)
                            }
                        },
                        maxLines = 2,
                        textAlign = TextAlign.End,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .width((LocalConfiguration.current.screenWidthDp - 170).dp)
                            .padding(5.dp, 25.dp, 5.dp, 0.dp)
                            .constrainAs(movableQuantity) {
                                top.linkTo(item.bottom)
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
                                top.linkTo(movableQuantity.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )
                }

                if (product != null && product.item.type == PackageType.Package  && product.item.openable) {
                    Text(
                        text = "Csomag állapota",
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(5.dp, 15.dp, 5.dp, 0.dp)
                            .constrainAs(switchText) {
                                top.linkTo(quantity.bottom)
                                start.linkTo(parent.start)
                            }
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp, 0.dp, 5.dp, 0.dp)
                            .constrainAs(switch) {
                                top.linkTo(switchText.bottom)
                            }
                    ) {
                        SegmentedControlTwoWaySwitch(
                            text1 = "Bontatlan",
                            text2 = "Bontott",
                            switchState = packageSwitchState,
                            onValueChange = { packageSwitchState = it }
                        )
                    }
                }

                Box(
                    modifier = when(product != null){
                        true -> when(product.item.type == PackageType.Package  && product.item.openable){
                            true->  Modifier
                                .padding(0.dp, 25.dp, 0.dp, 0.dp)
                                .constrainAs(qualitySwitch) {
                                    top.linkTo(switch.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                            false-> Modifier
                                .padding(0.dp, 25.dp, 0.dp, 0.dp)
                                .constrainAs(qualitySwitch) {
                                    top.linkTo(quantity.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        }
                        false -> Modifier
                            .padding(0.dp, 25.dp, 0.dp, 0.dp)
                            .constrainAs(qualitySwitch) {
                                top.linkTo(item.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
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
                        val itemQuantity = when(quantityInput != "" && quantityInput.toDouble() > 0.0){
                            true -> quantityInput.toDouble()
                            false -> 0.0
                        }


                        if( product != null && (itemQuantity > product.freeQuantity || itemQuantity <= 0.0)) {
                            Toast.makeText(context, "Nem megfelelő a mennyiség értéke!", Toast.LENGTH_SHORT).show()
                        }
                        else
                            onReleaseClick(
                                Release(
                                    acqId = acqId ?: "",
                                    releaseDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date()),
                                    quantity = itemQuantity,
                                    quality = when(qualitySwitchState) {
                                        0 -> Quality.Good
                                        1 -> Quality.Faulty
                                        2 -> Quality.Other
                                        else -> Quality.Good
                                    },
                                    packageState = when(product != null && product.item.type == PackageType.Package && product.item.openable) {
                                        true -> when(packageSwitchState) {
                                            true -> PackageState.Opened
                                            false -> PackageState.Full
                                        }
                                        false -> null
                                    }
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