package com.example.raktardemo.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.ui.views.theme.Shapes

@Composable
fun GroupDetail(
    groups: List<StoredItem>,
    onClicked: (StoredItem) -> Unit,
    onReleaseClicked: (ArrayList<StoredItem>, String) -> Unit,
    onReserveClicked: (ArrayList<StoredItem>, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
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
                    withStyle(style = SpanStyle(
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )) {
                        append(groups[0].item.category.name)
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
                    }
                    .height(200.dp)
            ) {
                val itemGroups = groups.groupBy{
                    when {
                        it.itemAcquisitions.isNotEmpty() -> it.itemAcquisitions.firstOrNull()?.id
                        else -> null
                    }
                }.filterKeys { it != null }

                val itemAcquisitionId = ArrayList(itemGroups.keys)

                itemsIndexed(ArrayList(itemGroups.values)) { index, group ->
                    Groups(
                        index = index,
                        group = group,
                        itemAcquisitionId = itemAcquisitionId,
                        onClicked = onClicked,
                        onReleaseClicked = onReleaseClicked,
                        onReserveClicked = onReserveClicked
                    )
                }
            }
        }
    }
}

@Composable
fun Groups(
    index: Int,
    itemAcquisitionId: ArrayList<String?>,
    group: List<StoredItem>,
    onClicked: (StoredItem) -> Unit,
    onReleaseClicked: (ArrayList<StoredItem>, String) -> Unit,
    onReserveClicked: (ArrayList<StoredItem>, String) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (
            item,
            acquisition,
            releaseButton,
            reserveButton,
            list
        ) = createRefs()

        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Gray)) {
                    append("Beszerzés - ")
                }

                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(itemAcquisitionId[index]!!)
                }
            },
            modifier = Modifier
                .padding(5.dp, 0.dp)
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
                    fontSize = 12.sp
                )
            },
            onClick = {
                onReleaseClicked(group as ArrayList<StoredItem>, itemAcquisitionId[index]!!)
            },
            modifier = Modifier
                .height(26.dp)
                .width(80.dp)
                .padding(2.dp, 0.dp)
                .constrainAs(releaseButton) {
                    top.linkTo(item.bottom)
                    end.linkTo(reserveButton.start)
                },
            contentPadding = PaddingValues(0.dp)
        )

        Button(
            content = {
                Text(
                    text = "Foglalás",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            },
            onClick = {
                onReserveClicked(group as ArrayList<StoredItem>, itemAcquisitionId[index]!!)
            },
            modifier = Modifier
                .height(26.dp)
                .width(80.dp)
                .padding(0.dp, 0.dp, 5.dp, 0.dp)
                .constrainAs(reserveButton) {
                    top.linkTo(item.bottom)
                    end.linkTo(parent.end)
                },
            contentPadding = PaddingValues(0.dp)
        )

        LazyColumn(
            modifier = Modifier
                .padding(0.dp, 2.dp, 0.dp, 15.dp)
                .constrainAs(list) {
                    top.linkTo(reserveButton.bottom)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
                .height(110.dp)
        ) {
            itemsIndexed(group) { _, item ->
                Items(
                    item = item,
                    onClicked = {
                        onClicked(item)
                    }
                )
            }
        }
    }
}

@Composable
fun Items(
    item: StoredItem,
    onClicked: (StoredItem) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .clickable(onClick = { onClicked(item) })
            .height(IntrinsicSize.Min)
            .padding(all = 1.dp)
            .background(Color.LightGray, Shapes.small)
    ) {
        Column(
            modifier = Modifier.weight(1f)
                .padding(all = 5.dp),
        ) {
            Text(
                text = item.item.name, color = Color.Black, fontSize = 18.sp
            )
        }
    }
}

/*@Preview
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
}*/