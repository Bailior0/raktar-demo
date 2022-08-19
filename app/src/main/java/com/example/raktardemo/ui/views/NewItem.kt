package com.example.raktardemo.ui.views

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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.raktardemo.R
import com.example.raktardemo.data.enums.Ownership
import com.example.raktardemo.data.enums.PackageType
import com.example.raktardemo.data.enums.QuantityUnit
import com.example.raktardemo.data.model.*
import com.example.raktardemo.ui.views.helpers.*
import com.example.raktardemo.ui.views.theme.Shapes
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NewItem(
    storages: List<Storage>,
    products: List<StoredItem>,
    onAddClicked: (StoredItem) -> Unit
) {
    val context = LocalContext.current

    var nameInput by remember { mutableStateOf("") }
    var groupInput by remember { mutableStateOf("") }
    var manufacturerInput by remember { mutableStateOf("") }
    var serialInput by remember { mutableStateOf("") }
    var barCode by remember { mutableStateOf("") }
    var defaultPriceInput by remember { mutableStateOf("") }

    var quantityInput by remember { mutableStateOf("") }
    var packageSizeInput by remember { mutableStateOf("") }

    var quantitySwitchState by remember { mutableStateOf(false) }
    var openableSwitchState by remember { mutableStateOf(false) }

    val unitList = mutableListOf<String>()
    for(qUnit in QuantityUnit.values().toList())
        unitList.add(qUnit.translation)

    var unitSelectedIndex by remember { mutableStateOf(0) }
    var unitExpanded by remember { mutableStateOf(false) }

    var dateInput by remember { mutableStateOf(SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())) }
    var minQuantityInput by remember { mutableStateOf("") }

    var ownerSwitchState by remember { mutableStateOf(false) }
    var datePickerState by remember { mutableStateOf(false) }

    val warehouseList = mutableListOf<String>()
    for(warehouse in storages)
        warehouseList.add(warehouse.name)

    var warehouseExpanded by remember { mutableStateOf(false) }
    var warehouseSelectedIndex by remember { mutableStateOf(0) }

    val categories = mutableListOf<Category>()

    for(product in products) {
        categories.add(product.item.category)
    }

    var categoryName by remember { mutableStateOf("") }
    var categoryId by remember { mutableStateOf("") }
    var categoryClicked by remember { mutableStateOf(false) }

    if(!categoryClicked) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            TopAppBar(
                title = { Text(text = "Új termék hozzáadása") }
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(12.dp, 25.dp, 12.dp, 30.dp)
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    val (
                        itemLabel,
                        divider,
                        name,
                        group,
                        manufacturer,
                        serial,
                        barcode,
                        defaultPrice,
                    ) = createRefs()

                    Text(
                        text = "Termék adatok",
                        color = Color.Gray,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .constrainAs(itemLabel) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                            }
                    )

                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 0.dp, 10.dp)
                            .constrainAs(divider) {
                                top.linkTo(itemLabel.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = "Terméknév",
                                color = Color.Gray
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 2.dp)
                            .constrainAs(name) {
                                top.linkTo(divider.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 10.dp, 0.dp, 10.dp)
                            .constrainAs(group) {
                                top.linkTo(name.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    ) {
                        val (text, catButton) = createRefs()

                        OutlinedTextField(
                            value = groupInput,
                            onValueChange = {
                                groupInput = it
                                categoryName = ""
                                categoryId = ""
                            },
                            singleLine = true,
                            placeholder = {
                                Text(
                                    text = "Termékcsoport",
                                    color = Color.Gray
                                )
                            },
                            modifier = Modifier
                                .width((LocalConfiguration.current.screenWidthDp /2).dp)
                                .constrainAs(text) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                }
                        )

                        Button(
                            content = {
                                Text(
                                    text = when(categoryName == "") {
                                        true -> "Kategóriák"
                                        false -> categoryName
                                    },
                                    fontWeight = FontWeight.Bold,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Image(
                                    painter = painterResource(
                                        id = R.drawable.ic_baseline_chevron_right_24
                                    ),
                                    contentDescription = null
                                )
                            },
                            onClick = { categoryClicked = true },
                            modifier = Modifier
                                .width(((LocalConfiguration.current.screenWidthDp / 2) - 40).dp)
                                .constrainAs(catButton) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    end.linkTo(parent.end)
                                },
                        )
                    }

                    OutlinedTextField(
                        value = manufacturerInput,
                        onValueChange = { manufacturerInput = it },
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = "Gyártó",
                                color = Color.Gray
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 2.dp)
                            .constrainAs(manufacturer) {
                                top.linkTo(group.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                    OutlinedTextField(
                        value = serialInput,
                        onValueChange = { serialInput = it },
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = "Sorozatszám",
                                color = Color.Gray
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 2.dp)
                            .constrainAs(serial) {
                                top.linkTo(manufacturer.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                    OutlinedTextField(
                        value = barCode,
                        onValueChange = { barCode = it },
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = "Vonalkód",
                                color = Color.Gray
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 2.dp)
                            .constrainAs(barcode) {
                                top.linkTo(serial.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                    OutlinedTextField(
                        value = defaultPriceInput,
                        onValueChange = { defaultPriceInput = it },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        placeholder = {
                            Text(
                                text = "Alapért. beszerzési ár",
                                color = Color.Gray
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 2.dp)
                            .constrainAs(defaultPrice) {
                                top.linkTo(barcode.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )
                }

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 25.dp, 0.dp, 0.dp)
                ) {
                    val (
                        packagingLabel,
                        divider,
                        quantitySwitch,
                        quantity,
                        unitOfQuantity,
                        packageSize,
                        openableSwitch,
                    ) = createRefs()

                    Text(
                        text = "Csomagolási adatok",
                        color = Color.Gray,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .constrainAs(packagingLabel) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                            }
                    )

                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 0.dp, 10.dp)
                            .constrainAs(divider) {
                                top.linkTo(packagingLabel.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                    Box(
                        modifier = Modifier
                            .padding(5.dp, 2.dp, 0.dp, 0.dp)
                            .constrainAs(quantitySwitch) {
                                top.linkTo(divider.bottom)
                                start.linkTo(parent.start)
                            }
                    ) {
                        SegmentedControlQuantitySwitch(
                            quantitySwitchState = quantitySwitchState,
                            onValueChange = { quantitySwitchState = it }
                        )
                    }

                    OutlinedTextField(
                        value = quantityInput,
                        onValueChange = { quantityInput = it },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        placeholder = {
                            Text(
                                text = "Mennyiség",
                                color = Color.Gray
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 2.dp)
                            .constrainAs(quantity) {
                                top.linkTo(quantitySwitch.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .constrainAs(unitOfQuantity) {
                                top.linkTo(quantity.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    ) {
                        val (text, comboBox) = createRefs()

                        Text(
                            text = "Mértékegység: ",
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
                                list = unitList,
                                selectedIndex = unitSelectedIndex,
                                onIndexChanged = { unitSelectedIndex = it },
                                isExpanded = unitExpanded,
                                onExpandedChanged = { unitExpanded = it },
                                textWidth = 60.dp
                            )
                        }
                    }


                    if (!quantitySwitchState) {
                        OutlinedTextField(
                            value = packageSizeInput,
                            onValueChange = { packageSizeInput = it },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            placeholder = {
                                Text(
                                    text = "Csomag mérete",
                                    color = Color.Gray
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 2.dp)
                                .constrainAs(packageSize) {
                                    top.linkTo(unitOfQuantity.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .constrainAs(openableSwitch) {
                                    top.linkTo(packageSize.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        ) {
                            SegmentedControlTwoWaySwitch(
                                text1 = "Bontható",
                                text2 = "Nem bontható",
                                switchState = openableSwitchState,
                                onValueChange = { openableSwitchState = it }
                            )
                        }
                    }
                }

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 25.dp, 0.dp, 0.dp)
                ) {
                    val (
                        storageLabel,
                        divider,
                        warehouse,
                        date,
                        ownerSwitch,
                        minQuantity,
                    ) = createRefs()

                    Text(
                        text = "Raktározás",
                        color = Color.Gray,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .constrainAs(storageLabel) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                            }
                    )

                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 0.dp, 10.dp)
                            .constrainAs(divider) {
                                top.linkTo(storageLabel.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp, 2.dp, 5.dp, 0.dp)
                            .constrainAs(warehouse) {
                                top.linkTo(divider.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    ) {
                        val (text, comboBox) = createRefs()

                        Text(
                            text = "Raktár választása: ",
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
                                warehouseList,
                                warehouseSelectedIndex,
                                { warehouseSelectedIndex = it },
                                warehouseExpanded,
                                { warehouseExpanded = it },
                                60.dp
                            )
                        }
                    }

                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp, 0.dp, 5.dp, 0.dp)
                            .constrainAs(date) {
                                top.linkTo(warehouse.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    ) {
                        val (text, dateButton) = createRefs()

                        Text(
                            text = "Szavatosság: ",
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
                                .constrainAs(dateButton) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    end.linkTo(parent.end)
                                }
                        ) {
                            DatePicker(
                                fromToday = true,
                                context,
                                datePickerState,
                                { datePickerState = it },
                                dateInput,
                                { dateInput = it }
                            )
                        }
                    }

                    OutlinedTextField(
                        value = minQuantityInput,
                        onValueChange = { minQuantityInput = it },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        placeholder = {
                            Text(
                                text = "Min. tárolt mennyiség",
                                color = Color.Gray
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 2.dp, 0.dp, 0.dp)
                            .constrainAs(minQuantity) {
                                top.linkTo(date.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                    Box(
                        modifier = Modifier
                            .constrainAs(ownerSwitch) {
                                top.linkTo(minQuantity.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    ) {
                        SegmentedControlTwoWaySwitch(
                            "Saját",
                            "Idegen",
                            ownerSwitchState
                        ) { ownerSwitchState = it }
                    }
                }

                Button(
                    content = {
                        Text(
                            text = "Hozzáad",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(25.dp, 0.dp)
                        )
                    },
                    onClick = {
                        val packageType = when(quantitySwitchState) {
                            false -> PackageType.Package
                            true -> PackageType.Piece
                        }
                        val quantity = when(quantityInput != "" && quantityInput.toDouble() > 0.0){
                            true -> quantityInput.toDouble()
                            false -> 0.0
                        }
                        val price = when(defaultPriceInput != "" && defaultPriceInput.toDouble() > 0.0){
                            true -> defaultPriceInput.toDouble()
                            false -> 0.0
                        }
                        val minQuantity = when(minQuantityInput != "" && minQuantityInput.toDouble() > 0.0){
                            true -> minQuantityInput.toDouble()
                            false -> 0.0
                        }
                        val packageSize = when(packageSizeInput != "" && packageSizeInput.toDouble() > 0.0){
                            true -> packageSizeInput.toDouble()
                            false -> 1.0
                        }

                        val totalQuantity = when(packageType == PackageType.Package){
                            true -> (quantity * packageSize)
                            false -> quantity
                        }
                        val currentQuantity = when(!openableSwitchState){
                            true -> totalQuantity
                            false -> quantity
                        }

                        val pricePerUnit = when(price != 0.0 && packageSize != 0.0){
                            true -> price/currentQuantity
                            false -> 0.0
                        }

                        val packageCounts = mutableListOf<Double>()
                        if(packageType == PackageType.Package) {
                            for(i in 0 until quantity.toInt()) {
                                packageCounts.add(packageSize)
                            }
                        } else {
                            packageCounts.add(quantity)
                        }

                        val defaultPackageQuantity = when(packageType == PackageType.Package) {
                            true -> packageSize
                            false -> 1.0
                        }

                        val cat = when(categoryName != "") {
                            true -> Category(categoryId, categoryName)
                            false -> Category(UUID.randomUUID().toString(), groupInput)
                        }
                        if(cat.name == "")
                            cat.id = "0"

                        var groupingId = ""
                        if(categoryName.length < 3)
                            groupingId = categoryName + (10000..99999).shuffled().last().toString()
                        else
                            groupingId = categoryName.substring(0, 3) + (10000..99999).shuffled().last().toString()


                        onAddClicked(
                            StoredItem(
                                item = Item(
                                    name = nameInput,
                                    category = cat,
                                    manufacturer = manufacturerInput,
                                    serialNumber = serialInput,
                                    barCode = barCode,
                                    type = packageType,
                                    quantityUnit = QuantityUnit.values().toList()[unitSelectedIndex],
                                    defaultPackageQuantity = defaultPackageQuantity,
                                    openable = when(packageType == PackageType.Package) {
                                        true -> !openableSwitchState
                                        false -> false
                                    },
                                    defaultPurchasePrice = 1.0,
                                    minimumStoredQuantity = minQuantity,
                                ),
                                itemAcquisitions = mutableListOf(
                                    ItemAcquisition(
                                        groupingId = groupingId,
                                        acquisitionDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date()),
                                        acquisitionWorker = "",
                                        expiryDate = dateInput,
                                        quantity = quantity,
                                        acquisitionPrice = price,
                                        pricePerUnit = pricePerUnit,
                                        currentStorage = when(storages.isNotEmpty()) {
                                            true -> storages[warehouseSelectedIndex].id
                                            false -> ""
                                        },
                                        ownedBy = when(ownerSwitchState) {
                                            false -> Ownership.Own
                                            true -> Ownership.Foreign
                                        },
                                        packageCounts = packageCounts
                                    )
                                )
                            )
                        )

                        nameInput = ""
                        categoryName = ""
                        categoryId =  ""
                        manufacturerInput = ""
                        serialInput = ""
                        barCode = ""
                        defaultPriceInput = ""
                        quantityInput = ""
                        packageSizeInput = ""
                        quantitySwitchState = false
                        openableSwitchState = false
                        unitSelectedIndex = 0
                        unitExpanded = false
                        dateInput = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
                        minQuantityInput = ""
                        ownerSwitchState = false
                        datePickerState = false
                        warehouseExpanded = false
                        warehouseSelectedIndex = 0
                    },
                    modifier = Modifier
                        .scale(2f)
                        .padding(0.dp, 40.dp, 0.dp, 50.dp)
                )
            }
        }
    }
    else if(categoryClicked) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            TopAppBar(
                title = { Text(text = "Kategóriák") },
                navigationIcon = {
                    IconButton(
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                                contentDescription = null
                            )
                        },
                        onClick = {
                            categoryClicked = false
                        }
                    )
                }
            )
            LazyColumn(
                modifier = Modifier
                    .padding(all = 10.dp)
                    .fillMaxSize()
            ) {
                itemsIndexed(categories.toSet().toList()) { _, item ->
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .padding(all = 5.dp)
                            .background(Color.LightGray, Shapes.small)
                            .fillMaxWidth()
                            .clickable(onClick = {
                                categoryName = item.name
                                categoryId = item.id
                                groupInput = ""
                                categoryClicked = false
                            })
                    ) {
                        Text(
                            text = item.name, color = Color.Black, fontSize = 18.sp, modifier = Modifier.padding(all = 15.dp)
                        )
                    }
                }
            }
        }
    }
}