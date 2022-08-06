package com.example.raktardemo.ui.views

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.raktardemo.R
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.ui.views.helpers.ListMaker

@Composable
fun ProductDetail(
    product: StoredItem,
    onIconClick: () -> Unit = {},
    onReservationClick: () -> Unit = {},
    onAcquisitionClick: () -> Unit = {},
    onReleaseClick: () -> Unit = {},
    onMovingClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        TopAppBar(
            title = { Text(text = product.item.name) },
            navigationIcon = {
                IconButton(
                    content = { Icon(painter = painterResource(id = R.drawable.baseline_arrow_back_24), contentDescription = null) },
                    onClick = onIconClick
                )
            }
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(12.dp, 25.dp, 12.dp, 55.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val (
                    storage,
                    storedAmount,
                    buttonRow,
                    manufacturer,
                    group,
                    serial,
                    barcode,
                    packagingType,
                    measurementUnit,
                    acquisitionPrice,
                    defaultPackageSize,
                    openable,
                ) = createRefs()

                val buttonColor = MaterialTheme.colors.primary

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Raktár\n")
                        }

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("TODO")
                        }
                    },
                    modifier = Modifier
                        .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
                        .constrainAs(storage) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                )

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Raktáron\n")
                        }

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("TODO")
                        }
                    },
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
                        .constrainAs(storedAmount) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                )

                Row(
                    modifier = Modifier
                        .padding(0.dp, 25.dp)
                        .constrainAs(buttonRow) {
                            top.linkTo(storedAmount.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    CircularButton(
                        buttonColor = buttonColor,
                        icon = R.drawable.ic_baseline_lock_24,
                        label = "Foglalás",
                        onClick = onReservationClick
                    )

                    CircularButton(
                        buttonColor = buttonColor,
                        icon = R.drawable.ic_baseline_input_24,
                        label = "Bevételezés",
                        onClick = onAcquisitionClick
                    )

                    CircularButton(
                        buttonColor = buttonColor,
                        icon = R.drawable.ic_baseline_output_24,
                        label = "Kivezetés",
                        onClick = onReleaseClick
                    )

                    CircularButton(
                        buttonColor = buttonColor,
                        icon = R.drawable.ic_baseline_sync_24,
                        label = "Mozgatás",
                        onClick = onMovingClick
                    )
                }

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Gyártó\n")
                        }

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(product.item.manufacturer)
                        }
                    },
                    modifier = Modifier
                        .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
                        .constrainAs(manufacturer) {
                            top.linkTo(buttonRow.bottom)
                            start.linkTo(parent.start)
                        }
                )

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Termékcsoport\n")
                        }

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(product.item.category.name)
                        }
                    },
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
                        .constrainAs(group) {
                            top.linkTo(buttonRow.bottom)
                            end.linkTo(parent.end)
                        }
                )

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Cikkszám\n")
                        }

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(product.item.serialNumber)
                        }
                    },
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
                        .constrainAs(serial) {
                            top.linkTo(manufacturer.bottom)
                            start.linkTo(parent.start)
                        }
                )

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Vonalkód\n")
                        }

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("TODO")
                        }
                    },
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
                        .constrainAs(barcode) {
                            top.linkTo(group.bottom)
                            end.linkTo(parent.end)
                        }
                )

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Típus\n")
                        }

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(product.item.type)
                        }
                    },
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
                        .constrainAs(packagingType) {
                            top.linkTo(serial.bottom)
                            start.linkTo(parent.start)
                        }
                )

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Egység\n")
                        }

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(product.item.quantityUnit)
                        }
                    },
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
                        .constrainAs(measurementUnit) {
                            top.linkTo(barcode.bottom)
                            end.linkTo(parent.end)
                        }
                )

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Beszerzési ár\n")
                        }

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("TODO")
                        }

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("TODO")
                        }
                    },
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
                        .constrainAs(acquisitionPrice) {
                            top.linkTo(packagingType.bottom)
                            start.linkTo(parent.start)
                        }
                )

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Bontható\n")
                        }

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(
                                when(product.item.openable) {
                                    true -> "igen"
                                    false -> "nem"
                                }
                            )
                        }
                    },
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
                        .constrainAs(openable) {
                            top.linkTo(measurementUnit.bottom)
                            end.linkTo(parent.end)
                        }
                )

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Alapértelmezett csomag\n")
                        }

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("TODO")
                        }
                    },
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .constrainAs(defaultPackageSize) {
                            top.linkTo(acquisitionPrice.bottom)
                            start.linkTo(parent.start)
                        }
                )
            }

            LazyColumn(
                modifier = Modifier
                    //.fillMaxSize()
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(5.dp, 30.dp, 5.dp, 5.dp)
                    .background(MaterialTheme.colors.secondary, RoundedCornerShape(5.dp))
            ) {
                val itemOperations: MutableList<Triple<String, String, String>> = mutableListOf()
                for(reservation in product.reservations)
                    itemOperations.add(Triple("foglalva", reservation.reservationQuantity.toString() + " " + product.item.quantityUnit.toString(), reservation.reservationDate))
                for(acquisition in product.itemAcquisitions)
                    itemOperations.add(Triple("bevételezve", acquisition.quantity.toString() + " " + product.item.quantityUnit.toString(), acquisition.acquisitionDate))

                itemOperations.sortBy { it.third }

                itemsIndexed(itemOperations) { _, item ->
                    ListMaker(
                        text1 = item.first,
                        text2 = item.second,
                        text3 = item.third
                    )
                }
            }
        }
    }
}

@Composable
fun CircularButton(buttonColor: Color, icon: Int, label: String, onClick: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .padding(5.dp)
            .clickable { onClick() }
    ) {
        val (circle, image, text) = createRefs()

        Canvas(
            modifier = Modifier
                .size(60.dp)
                .constrainAs(circle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            onDraw = {
                drawCircle(color = buttonColor)
            }
        )

        Image(
            painter = painterResource(
                id = icon
            ),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(circle.top)
                    start.linkTo(circle.start)
                    end.linkTo(circle.end)
                    bottom.linkTo(circle.bottom)
                }

        )

        Text(
            text = label,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .constrainAs(text) {
                    top.linkTo(circle.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}

/*@Preview
@Composable
fun ProductDetailPreview() {
    RaktarAppJustUi1Theme {
        ProductDetail()
    }
}*/