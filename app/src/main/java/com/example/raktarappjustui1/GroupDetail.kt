package com.example.raktarappjustui1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.raktarappjustui1.data.Group
import com.example.raktarappjustui1.data.Item
import com.example.raktarappjustui1.ui.theme.RaktarAppJustUi1Theme
import com.example.raktarappjustui1.ui.theme.Shapes

@Composable
fun GroupDetail(
    groups: List<Group>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        TopAppBar(
            title = { Text(text = "Csoport") }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp, 10.dp, 12.dp, 0.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (
                    item,
                    itemGroup,
                    items,
                    group
                ) = createRefs()

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Termékcsoport")
                        }
                    },
                    modifier = Modifier
                        .padding(5.dp, 0.dp, 0.dp, 0.dp)
                        .constrainAs(itemGroup) {
                            top.linkTo(item.bottom)
                            start.linkTo(parent.start)
                        }
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)) {
                            append("kábel")
                        }
                    },
                    modifier = Modifier
                        .padding(5.dp, 0.dp, 0.dp, 0.dp)
                        .constrainAs(items) {
                            top.linkTo(itemGroup.bottom)
                            start.linkTo(parent.start)
                        }
                )

                LazyColumn(
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 15.dp)
                        .constrainAs(group) {
                            top.linkTo(items.bottom)
                            start.linkTo(parent.start)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                            height = Dimension.fillToConstraints
                            width = Dimension.fillToConstraints
                        }
                ) {
                    itemsIndexed(groups) { _, groups ->
                        Groups(
                            group = groups
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Groups(
    group: Group
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (
            item,
            acquisition,
            button,
            list
        ) = createRefs()

        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Gray)) {
                    append("Beszerzés - ")
                }

                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(group.number.toString())
                }
            },
            modifier = Modifier
                .padding(2.dp, 0.dp)
                .constrainAs(acquisition) {
                    top.linkTo(item.bottom)
                    start.linkTo(parent.start)
                }
        )

        Button(
            content = {
                Text(
                    text = "Kivezetés",
                    fontWeight = FontWeight.Bold,
                    fontSize = 9.sp
                )
            },
            onClick = {
                //TODO
            },
            modifier = Modifier
                .height(28.dp)
                .constrainAs(button) {
                    top.linkTo(item.bottom)
                    end.linkTo(parent.end)
                }
        )

        LazyColumn(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 15.dp)
                .constrainAs(list) {
                    top.linkTo(button.bottom)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
                .height(110.dp)
        ) {
            itemsIndexed(group.items) { _, item ->
                Items(
                    item = item
                )
            }
        }
    }
}

@Composable
fun Items(
    item: Item
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .padding(all = 1.dp)
            .background(Color.LightGray, Shapes.small)
    ) {
        Column(
            modifier = Modifier.weight(1f)
                .padding(all = 5.dp),
        ) {
            Text(
                text = item.name, color = Color.Black, fontSize = 18.sp
            )
        }
    }
}

@Preview
@Composable
fun GroupDetailPreview() {
    RaktarAppJustUi1Theme {
        val elem1 = Item(
            name = "elem1",
            category = "kábel",
            quantityUnit = "m",
            quantity = 0.0
        )
        val elem2 = Item(
            name = "elem2",
            category = "kábel",
            quantityUnit = "m",
            quantity = 0.0
        )
        val elem3 = Item(
            name = "elem3",
            category = "kábel",
            quantityUnit = "m",
            quantity = 0.0
        )
        val group1 = Group(
            number = 1111,
            items = mutableListOf(elem1, elem2, elem3, elem1, elem2)
        )
        val group2 = Group(
            number = 1112,
            items = mutableListOf(elem1, elem2, elem3)
        )
        val group3 = Group(
            number = 1113,
            items = mutableListOf(elem1, elem2, elem3)
        )

        GroupDetail(mutableListOf(group1, group2, group3, group1, group2, group3, group1, group2, group3))
    }
}