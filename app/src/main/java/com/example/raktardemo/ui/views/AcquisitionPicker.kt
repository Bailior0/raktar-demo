package com.example.raktardemo.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.raktardemo.data.model.ItemAcquisition
import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.ui.views.helpers.ListMaker

@Composable
fun AcquisitionPicker(
    acquisitions: List<ItemAcquisition>,
    storages: List<Storage>,
    onIdChange: (String) -> Unit,
    onDateChange: (String) -> Unit,
    onClose: () -> Unit
) {
    val storageNameList = mutableListOf<String>()
    val quantityList = mutableListOf<Double>()

    for (acquisition in acquisitions) {
        var storageName = ""
        var quantity = 0.0
        for (storage in storages) {
            if (acquisition.currentStorage == storage.id) {
                storageName = storage.name
                break
            }
        }
        for (count in acquisition.packageCounts) {
            quantity += count
        }
        storageNameList.add(storageName)
        quantityList.add(quantity)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(LocalConfiguration.current.screenHeightDp.dp)
            .background(MaterialTheme.colors.background)
    ) {
        itemsIndexed(acquisitions) { index, acquisition ->
            if(quantityList[index] != 0.0)
                ListMaker(
                    text1 = acquisition.acquisitionDate,
                    text2 = storageNameList[index],
                    text3 = quantityList[index].toString(),
                    id = acquisition.id,
                    date = acquisition.acquisitionDate,
                    padding = 15.dp,
                    onIdChange,
                    onDateChange,
                    onClose
                )
        }
    }
}
