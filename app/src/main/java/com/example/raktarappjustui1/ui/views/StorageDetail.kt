package com.example.raktarappjustui1.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.raktarappjustui1.data.Storage
import com.example.raktarappjustui1.data.Worker
import com.example.raktarappjustui1.ui.views.theme.RaktarAppJustUi1Theme

@Composable
fun StorageDetail(
    storage: Storage
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = "Raktár") }
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
                    description,
                    storageMaintainers,
                    maintainers,
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

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Raktárért felelős személyek")
                        }
                    },
                    modifier = Modifier
                        .padding(5.dp, 25.dp, 0.dp, 8.dp)
                        .constrainAs(storageMaintainers) {
                            top.linkTo(description.bottom)
                            start.linkTo(parent.start)
                        }
                )

                LazyColumn(
                    modifier = Modifier
                        .padding(5.dp, 0.dp, 0.dp, 10.dp)
                        .constrainAs(maintainers) {
                            top.linkTo(storageMaintainers.bottom)
                            start.linkTo(parent.start)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                            height = Dimension.fillToConstraints
                        }
                ) {
                    itemsIndexed(storage.workers) { _, worker ->
                        WorkerItem(
                            worker = worker
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WorkerItem(
    worker: Worker
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .padding(bottom = 5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f),
        ) {
            Text(
                worker.name,
                maxLines = 1,
                softWrap = true,
                fontWeight = FontWeight.Bold
            )
            Text(
                worker.email,
                maxLines = 1,
                softWrap = true
            )
            Text(
                worker.phoneNumber,
                maxLines = 1,
                softWrap = true
            )
        }
    }
}

/*@Preview
@Composable
fun StoragePreview() {
    RaktarAppJustUi1Theme {
        val storage = Storage(
            name = "Raktár1",
            address = "1117 Budapest\nGábor Dénes u. 4\nInfopark C épület",
            size = 1234.0,
            description = "Szeretnénk felhívni a figyelmet, hogy a határidőkre a lehető legnagyobb körültekintéssel figyeljenek oda, mert határidőn túli pályázatot nem tudunk elfogadni!",
            workers = mutableListOf()
        )
        val worker1 = Worker(
            name = "Raktáros Réka",
            email = "raktaros.reka@raktar.hu",
            phoneNumber = "+36 10 111-1111",
            storages = mutableListOf(storage)
        )
        val worker2 = Worker(
            name = "Raktáros Léna",
            email = "raktaros.reka@raktar.hu",
            phoneNumber = "+36 10 111-1111",
            storages = mutableListOf(storage)
        )
        val worker3 = Worker(
            name = "Raktáros Béla",
            email = "raktaros.reka@raktar.hu",
            phoneNumber = "+36 10 111-1111",
            storages = mutableListOf(storage)
        )

        storage.workers = mutableListOf(worker1, worker2, worker3, worker2, worker2, worker2, worker2, worker2, worker2, worker2, worker2, worker2, worker2, worker2, worker2, worker2, worker2, worker2)

        StorageDetail(storage)
    }
}*/