package com.example.raktardemo.ui.views

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import com.example.raktardemo.data.model.Moving
import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.data.model.StoredItem
import com.example.raktardemo.ui.views.helpers.ComboBox
import com.example.raktardemo.ui.views.helpers.SegmentedControlTwoWaySwitch
import com.example.raktardemo.ui.views.theme.Shapes
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Moving(
    product: StoredItem,
    storages: List<Storage>,
    presentStorages: List<Storage>,
    onIconClick: () -> Unit = {},
    onMovingClick: (StoredItem, List<Pair<String, Double>>, Moving, PackageState?) -> Unit
) {
    val context = LocalContext.current

    var packagesClicked by remember { mutableStateOf(false) }

    var quantityInput by remember { mutableStateOf("") }

    val presentStorageList = mutableListOf<String>()
    for(presentStorage in presentStorages)
        presentStorageList.add(presentStorage.name)

    var presentStorageExpanded by remember { mutableStateOf(false) }
    var presentStorageSelectedIndex by remember { mutableStateOf(0) }

    val allStorageList = mutableListOf<String>()
    for(allStorage in storages)
        allStorageList.add(allStorage.name)

    var allStorageExpanded by remember { mutableStateOf(false) }
    var allStorageSelectedIndex by remember { mutableStateOf(0) }

    var packageSwitchState by remember { mutableStateOf(false) }

    var freeQuantity by remember { mutableStateOf(0) }
    var packages by remember { mutableStateOf(0) }


    var itemCnt = 0.0
    var packageCnt = 0

    val openedPackages: MutableList<Pair<String, Double>> = mutableListOf()

    val chosenOpenedPackages = remember { mutableStateListOf<Pair<String, Double>>() }

    for(acqItem in product.itemAcquisitions) {
        if(acqItem.currentStorage == presentStorages[presentStorageSelectedIndex].id) {
            if(!packageSwitchState) {
                for(count in acqItem.packageCounts) {
                    if(product.item.type == PackageType.Package && count == product.item.defaultPackageQuantity) {
                        packageCnt += 1
                        itemCnt += count
                    }
                    else if(product.item.type == PackageType.Piece) {
                        itemCnt += count
                    }
                }
            }
            else {
                for(count in acqItem.packageCounts) {
                    if(product.item.type == PackageType.Package && count != product.item.defaultPackageQuantity) {
                        openedPackages.add(Pair(acqItem.id, count))
                        packageCnt += 1
                        itemCnt += count
                    }
                }
            }
        }
    }

    freeQuantity = itemCnt.toInt()
    packages = packageCnt

    if(!packagesClicked) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            TopAppBar(
                title = { Text(text = "Mozgatás") },
                navigationIcon = {
                    IconButton(
                        content = { Icon(painter = painterResource(id = R.drawable.baseline_arrow_back_24), contentDescription = null) },
                        onClick = onIconClick
                    )
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(12.dp, 25.dp, 12.dp, 100.dp)
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    val (
                        itemLabel,
                        item,
                        quantitySwitch,
                        movableQuantity,
                        quantity,
                        switchText,
                        switch,
                        currentWarehouse,
                        destinationWarehouse,
                        button
                    ) = createRefs()



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

                    Box(
                        modifier = Modifier
                            .padding(5.dp, 25.dp, 0.dp, 0.dp)
                            .constrainAs(quantitySwitch) {
                                top.linkTo(item.bottom)
                                start.linkTo(parent.start)
                            }
                    )


                    Text(
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.Gray)) {
                                append("Mozgatható: ")
                            }

                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(freeQuantity.toString())
                            }

                            withStyle(style = SpanStyle(color = Color.Gray)) {
                                append(" " + product.item.quantityUnit.translation)
                            }

                            if(product.item.type == PackageType.Package) {
                                withStyle(style = SpanStyle(color = Color.Gray)) {
                                    append(", ")
                                }

                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append(packages.toString())
                                }

                                withStyle(style = SpanStyle(color = Color.Gray)) {
                                    append(" csomag")
                                }
                            }
                        },
                        maxLines = 2,
                        textAlign = TextAlign.End,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(5.dp, 25.dp, 5.dp, 0.dp)
                            .constrainAs(movableQuantity) {
                                bottom.linkTo(quantity.top)
                                end.linkTo(parent.end)
                            }
                    )

                    if(product.item.type == PackageType.Package && packageSwitchState) {
                        Button(
                            content = {
                                Text(
                                    text = "Csomagok",
                                    fontWeight = FontWeight.Bold
                                )
                                Image(
                                    painter = painterResource(
                                        id = R.drawable.ic_baseline_chevron_right_24
                                    ),
                                    contentDescription = null
                                )
                            },
                            onClick = { packagesClicked = true },
                            modifier = Modifier
                                .height(44.dp)
                                .width(150.dp)
                                .padding(0.dp, 10.dp, 5.dp, 0.dp)
                                .constrainAs(quantity) {
                                    top.linkTo(quantitySwitch.bottom)
                                    end.linkTo(parent.end)
                                },
                            contentPadding = PaddingValues(0.dp)
                        )
                    }
                    else
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
                                .padding(0.dp, 2.dp, 0.dp, 0.dp)
                                .constrainAs(quantity) {
                                    top.linkTo(quantitySwitch.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        )

                    if (product.item.type == PackageType.Package  && product.item.openable) {
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

                    ConstraintLayout(
                        modifier = when(product.item.type == PackageType.Package  && product.item.openable) {
                            true -> Modifier
                                .fillMaxWidth()
                                .padding(5.dp, 25.dp, 5.dp, 0.dp)
                                .constrainAs(currentWarehouse) {
                                    top.linkTo(switch.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                            false -> Modifier
                                .fillMaxWidth()
                                .padding(5.dp, 25.dp, 5.dp, 0.dp)
                                .constrainAs(currentWarehouse) {
                                    top.linkTo(quantity.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        }


                    ) {
                        val (text, comboBox) = createRefs()

                        Text(
                            text = "Kiinduló raktár választása: ",
                            color = Color.Gray,
                            modifier = Modifier
                                .constrainAs(text) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                }
                        )

                        Box(
                            modifier = Modifier
                                .constrainAs(comboBox) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    end.linkTo(parent.end)
                                }
                        ) {
                            ComboBox(
                                presentStorageList,
                                presentStorageSelectedIndex,
                                { presentStorageSelectedIndex = it},
                                presentStorageExpanded,
                                { presentStorageExpanded = it },
                                60.dp
                            )
                        }
                    }

                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .constrainAs(destinationWarehouse) {
                                top.linkTo(currentWarehouse.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    ) {
                        val (text, comboBox) = createRefs()

                        Text(
                            text = "Célraktár választása: ",
                            color = Color.Gray,
                            modifier = Modifier
                                .constrainAs(text) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                }
                        )

                        Box(
                            modifier = Modifier
                                .constrainAs(comboBox) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    end.linkTo(parent.end)
                                }
                        ) {
                            ComboBox(
                                allStorageList,
                                allStorageSelectedIndex,
                                { allStorageSelectedIndex = it},
                                allStorageExpanded,
                                { allStorageExpanded = it },
                                60.dp
                            )
                        }
                    }

                    Button(
                        content = {
                            Text(
                                text = "Mozgat",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(25.dp, 0.dp)
                            )
                        },
                        onClick = {
                            var packageQuantity = 0.0
                            for(chosenPackage in chosenOpenedPackages)
                                packageQuantity += chosenPackage.second

                            if( !packageSwitchState && (quantityInput == "" || quantityInput.toDouble() * product.item.defaultPackageQuantity > freeQuantity || quantityInput.toDouble() <= 0.0)) {
                                Toast.makeText(context, "Nem megfelelő a mennyiség értéke!", Toast.LENGTH_SHORT).show()
                            }
                            else if(packageSwitchState && chosenOpenedPackages.toList().isEmpty()) {
                                Toast.makeText(context, "Nincsenek csomagok választva!", Toast.LENGTH_SHORT).show()
                            }
                            else if(storages[allStorageSelectedIndex] == presentStorages[presentStorageSelectedIndex]) {
                                Toast.makeText(context, "A kezdő és célraktár nem egyezhetnek meg!", Toast.LENGTH_SHORT).show()
                            }
                            else
                                onMovingClick(
                                    product,
                                    chosenOpenedPackages.toList(),
                                    Moving(
                                        quantity = when(packageSwitchState) {
                                            true -> packageQuantity
                                            false -> quantityInput.toDouble() * product.item.defaultPackageQuantity
                                        },
                                        movingDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(
                                            Date()
                                        ),
                                        sourceStorage = presentStorages[presentStorageSelectedIndex].id,
                                        destinationStorage = storages[allStorageSelectedIndex].id,
                                    ),
                                    when(product.item.type == PackageType.Package) {
                                        true -> when(packageSwitchState) {
                                            true -> PackageState.Opened
                                            false -> PackageState.Full
                                        }
                                        false -> null
                                    }
                                )
                        },
                        modifier = Modifier
                            .scale(2f)
                            .padding(0.dp, 25.dp, 0.dp, 0.dp)
                            .constrainAs(button) {
                                top.linkTo(destinationWarehouse.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )


                }
            }
        }
    }
    else if(packagesClicked) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            TopAppBar(
                title = { Text(text = "Csomagok") },
                navigationIcon = {
                    IconButton(
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                                contentDescription = null
                            )
                        },
                        onClick = { packagesClicked = false }
                    )
                }
            )
            LazyColumn(
                modifier = Modifier
                    .padding(all = 10.dp)
                    .fillMaxSize()
            ) {
                itemsIndexed(openedPackages) { _, item ->
                    var checkedState by remember { mutableStateOf(false) }

                    if(checkedState)
                        chosenOpenedPackages.add(item)
                    else if(packagesClicked && !checkedState)
                        chosenOpenedPackages.remove(item)

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .padding(all = 5.dp)
                            .background(Color.LightGray, Shapes.small)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = item.second.toString(), color = Color.Black, fontSize = 18.sp, modifier = Modifier.padding(all = 15.dp)
                        )
                        Checkbox(
                            checked = checkedState,
                            onCheckedChange = {
                                checkedState = it
                            }
                        )
                    }
                }
            }
        }
    }
}