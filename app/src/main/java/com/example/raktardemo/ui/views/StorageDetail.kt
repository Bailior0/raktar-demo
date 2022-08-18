package com.example.raktardemo.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.raktardemo.R
import com.example.raktardemo.data.model.Storage

@Composable
fun StorageDetail(
    storage: Storage,
    onIconClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = "Raktár") },
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
                .background(MaterialTheme.colors.background)
                .padding(12.dp, 10.dp, 12.dp, 55.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (
                    item,
                    storageName,
                    name,
                    storageSize,
                    size,
                    storageAddress,
                    address,
                    storageDescription,
                    description
                ) = createRefs()

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Raktár neve")
                        }
                    },
                    modifier = Modifier
                        .padding(5.dp, 0.dp, 0.dp, 0.dp)
                        .constrainAs(storageName) {
                            top.linkTo(item.bottom)
                            start.linkTo(parent.start)
                        }
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(storage.name)
                        }
                    },
                    modifier = Modifier
                        .padding(5.dp, 0.dp, 0.dp, 0.dp)
                        .constrainAs(name) {
                            top.linkTo(storageName.bottom)
                            start.linkTo(parent.start)
                        }
                )

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Méret")
                        }
                    },
                    modifier = Modifier
                        .padding(2.dp, 0.dp)
                        .constrainAs(storageSize) {
                            top.linkTo(item.bottom)
                            end.linkTo(parent.end)
                        }
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(storage.size.toString())
                        }

                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append(" m")
                        }

                        withStyle(style = SpanStyle(color = Color.Gray, baselineShift = BaselineShift.Superscript, fontSize = 10.sp)) {
                            append("2")
                        }
                    },
                    modifier = Modifier
                        .padding(2.dp, 0.dp)
                        .constrainAs(size) {
                            top.linkTo(storageSize.bottom)
                            end.linkTo(parent.end)
                        }
                )

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Cím")
                        }
                    },
                    modifier = Modifier
                        .padding(5.dp, 15.dp, 0.dp, 0.dp)
                        .constrainAs(storageAddress) {
                            top.linkTo(size.bottom)
                            start.linkTo(parent.start)
                        }
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(storage.address)
                        }
                    },
                    modifier = Modifier
                        .padding(5.dp, 0.dp, 0.dp, 0.dp)
                        .constrainAs(address) {
                            top.linkTo(storageAddress.bottom)
                            start.linkTo(parent.start)
                        }
                )

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Leírás")
                        }
                    },
                    modifier = Modifier
                        .padding(5.dp, 15.dp, 0.dp, 0.dp)
                        .constrainAs(storageDescription) {
                            top.linkTo(address.bottom)
                            start.linkTo(parent.start)
                        }
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(storage.description)
                        }
                    },
                    modifier = Modifier
                        .padding(5.dp, 0.dp, 5.dp, 0.dp)
                        .constrainAs(description) {
                            top.linkTo(storageDescription.bottom)
                            start.linkTo(parent.start)
                        },
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}