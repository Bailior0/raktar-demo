package com.example.raktardemo.ui.views

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.raktardemo.R
import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.Worker
import com.example.raktardemo.ui.views.theme.Shapes

@Composable
fun Account(
    worker: Worker,
    storageList: List<Storage>,
    onClicked: (Storage) -> Unit,
    onLogoutClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        TopAppBar(
            title = { Text(text = "Felhasználó") }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp, 10.dp, 12.dp, 40.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (
                    item,
                    editIcon,
                    loggedIn,
                    name,
                    email,
                    phone,
                    maintainedStorages,
                    storages,
                    button
                ) = createRefs()



                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .constrainAs(editIcon) {
                            top.linkTo(item.bottom)
                            end.linkTo(parent.end)
                        }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_edit_24),
                        contentDescription = null
                    )
                }

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Bejelentkezve mint")
                        }
                    },
                    modifier = Modifier
                        .constrainAs(loggedIn) {
                            top.linkTo(item.bottom)
                            start.linkTo(parent.start)
                        }
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Black, fontSize = 25.sp, fontWeight = FontWeight.Bold)) {
                            append(worker.name)
                        }
                    },
                    modifier = Modifier
                        .constrainAs(name) {
                            top.linkTo(loggedIn.bottom)
                            start.linkTo(parent.start)
                        }
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.DarkGray, fontSize = 17.sp)) {
                            append(worker.email)
                        }
                    },
                    modifier = Modifier
                        .constrainAs(email) {
                            top.linkTo(name.bottom)
                            start.linkTo(parent.start)
                        }
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.DarkGray, fontSize = 17.sp)) {
                            append(worker.phoneNumber)
                        }
                    },
                    modifier = Modifier
                        .constrainAs(phone) {
                            top.linkTo(email.bottom)
                            start.linkTo(parent.start)
                        }
                )

                Text(
                    text = "Kezelt raktárak: ",
                    color = Color.Gray,
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .padding(0.dp, 15.dp, 0.dp, 10.dp)
                        .constrainAs(maintainedStorages) {
                            top.linkTo(phone.bottom)
                            start.linkTo(parent.start)
                        }
                )

                LazyColumn(
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 15.dp)
                        .constrainAs(storages) {
                            top.linkTo(maintainedStorages.bottom)
                            start.linkTo(parent.start)
                            bottom.linkTo(button.top)
                            end.linkTo(parent.end)
                            height = Dimension.fillToConstraints
                            width = Dimension.fillToConstraints
                        }
                ) {
                    itemsIndexed(storageList) { _, storage ->
                        StorageItem(
                            storage = storage,
                            onClicked = {
                                onClicked(storage)
                            }
                        )
                    }
                }

                Button(
                    content = {
                        Text(
                            text = "Kijelentkezés",
                            fontWeight = FontWeight.Bold,
                        )
                    },
                    onClick = onLogoutClick,
                    modifier = Modifier
                        .scale(2f)
                        .padding(0.dp, 50.dp)
                        .constrainAs(button) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                )
            }
        }
    }
}

@Composable
fun StorageItem(
    storage: Storage,
    onClicked: (Storage) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .clickable(onClick = { onClicked(storage) })
            .height(IntrinsicSize.Min)
            .padding(all = 1.dp)
            .background(Color.LightGray, Shapes.small)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .padding(all = 5.dp),
        ) {
            Text(
                text = storage.name, color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold
            )
        }
    }
}