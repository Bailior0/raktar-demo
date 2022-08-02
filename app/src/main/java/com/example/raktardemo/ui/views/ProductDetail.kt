package com.example.raktardemo.ui.views

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.raktardemo.data.Item
import com.example.raktardemo.ui.views.helpers.ListMaker

@Composable
fun ProductDetail(
    item: Item,
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
            title = { Text(text = item.name) },
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
                            append("Raktár2")
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
                            append("1000 csomag / 3050 méter")
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
                            append("Hama Hama Hama Hama Hama Hama Hama Hama Hama Hama Hama Hama")
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
                            append("kábel kábel kábel kábel kábel kábel kábel kábel kábel kábel kábel kábel kábel kábel kábel")
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
                            append("123456789123456789123456789123456789123456789")
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
                            append("43274368946")
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
                            append("csomag")
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
                            append("méter")
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
                            append("70000000000000000000000000 forint/kilométer\n")
                        }

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("20000 forint/csomag")
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
                            append("igen")
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
                            append("300000000000000000000000000000000000000000000000000000000001 méter")
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
                item {
                    ListMaker("foglalva", "100 méter", "2022.02.02.")
                    ListMaker("bevételezve", "5000 méter", "2023.03.03.")
                    ListMaker("foglalva", "5800 méter", "2023.03.04.")
                    ListMaker("bevételezve", "200 méter", "2023.03.05.")
                    ListMaker("bevételezve", "8000 méter", "2023.03.06.")
                    ListMaker("foglalva", "60 méter", "2023.03.09.")
                    ListMaker("foglalva", "910 méter", "2023.03.11.")
                    ListMaker("bevételezve", "323 méter", "2023.03.24.")
                    ListMaker("bevételezve", "700 méter", "2023.03.25.")
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