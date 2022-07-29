package com.example.raktarappjustui1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun PickerView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp, 10.dp, 12.dp, 55.dp)
        ) {
            val (
                searchbar,
                list
            ) = createRefs()

            var searchExpression by remember { mutableStateOf("") }

            OutlinedTextField(
                value = searchExpression,
                onValueChange = { searchExpression = it },
                singleLine = true,
                label = { Text("Keresés") },
                leadingIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_search_24),
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(searchbar) {
                        top.linkTo(parent.top)
                    }

            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 15.dp, 0.dp, 15.dp)
                    .background(MaterialTheme.colors.secondary, RoundedCornerShape(5.dp))
                    .constrainAs(list) {
                        top.linkTo(searchbar.bottom)
                    }
            ) {
                item {
                    ListMaker("rg6-fehér", "Raktár2", "<100 méter")
                    ListMaker("fdsafdsa", "Raktár1", "<150 méter")
                    ListMaker("ghhgfgg", "Raktár1", "<100 méter")
                    ListMaker("n cbcnbv", "Raktár1", "<100 méter")
                    ListMaker("zzuikjhh", "Raktár1", "<100 méter")
                    ListMaker("dfsagfdshgf", "Raktár2", "<200 méter")
                    ListMaker("rg6-fehér", "Raktár2", "<100 méter")
                    ListMaker("fdsafdsa", "Raktár1", "<150 méter")
                    ListMaker("ghhgfgg", "Raktár1", "<100 méter")
                    ListMaker("n cbcnbv", "Raktár1", "<100 méter")
                    ListMaker("zzuikjhh", "Raktár1", "<100 méter")
                    ListMaker("dfsagfdshgf", "Raktár2", "<200 méter")
                    ListMaker("élklkj", "Raktár1", "<1000 méter")
                    ListMaker("bevételezve", "Raktár1", "<300 méter")
                    ListMaker("bevételezve", "Raktár2", "<400 méter")
                    ListMaker("rg6-fehér", "Raktár2", "<100 méter")
                    ListMaker("fdsafdsa", "Raktár1", "<150 méter")
                    ListMaker("ghhgfgg", "Raktár1", "<100 méter")
                    ListMaker("n cbcnbv", "Raktár1", "<100 méter")
                    ListMaker("zzuikjhh", "Raktár1", "<100 méter")
                    ListMaker("dfsagfdshgf", "Raktár2", "<200 méter")
                    ListMaker("élklkj", "Raktár1", "<1000 méter")
                    ListMaker("bevételezve", "Raktár1", "<300 méter")
                    ListMaker("bevételezve", "Raktár2", "<400 méter")
                    ListMaker("rg6-fehér", "Raktár2", "<100 méter")
                    ListMaker("fdsafdsa", "Raktár1", "<150 méter")
                    ListMaker("ghhgfgg", "Raktár1", "<100 méter")
                    ListMaker("n cbcnbv", "Raktár1", "<100 méter")
                    ListMaker("zzuikjhh", "Raktár1", "<100 méter")
                    ListMaker("dfsagfdshgf", "Raktár2", "<200 méter")
                    ListMaker("élklkj", "Raktár1", "<1000 méter")
                    ListMaker("bevételezve", "Raktár1", "<300 méter")
                    ListMaker("bevételezve", "Raktár2", "<400 méter")
                    ListMaker("rg6-fehér", "Raktár2", "<100 méter")
                    ListMaker("fdsafdsa", "Raktár1", "<150 méter")
                    ListMaker("ghhgfgg", "Raktár1", "<100 méter")
                    ListMaker("n cbcnbv", "Raktár1", "<100 méter")
                    ListMaker("zzuikjhh", "Raktár1", "<100 méter")
                    ListMaker("dfsagfdshgf", "Raktár2", "<200 méter")
                    ListMaker("élklkj", "Raktár1", "<1000 méter")
                    ListMaker("bevételezve", "Raktár1", "<300 méter")
                    ListMaker("bevételezve", "Raktár2", "<400 méter")
                }
            }
        }
    }
}

@Composable
fun PickerButton() {
    Button(
        content = {
            Row() {
                Text(text = "Raktár1")

                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_chevron_right_24),
                    contentDescription = null
                )
            }
        },
        onClick = {}
    )
}
