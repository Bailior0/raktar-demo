package com.example.raktardemo.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.raktardemo.R
import com.example.raktardemo.data.model.ItemAcquisition
import com.example.raktardemo.data.model.Storage
import com.example.raktardemo.ui.views.helpers.ListMaker

@Composable
fun AcquisitionPicker(
    acquisitions: List<ItemAcquisition>,
    storages: List<Storage>,
    onValueChange: (String) -> Unit,
    onClose: () -> Unit
) {
    for (acquisition in acquisitions) {
        for (storage in storages) {
            if (acquisition.currentStorage == storage.id) {
                acquisition.currentStorage = storage.name
                break
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(LocalConfiguration.current.screenHeightDp.dp)
            .background(MaterialTheme.colors.background)
    ) {
        itemsIndexed(acquisitions) { _, acquisition ->
            ListMaker(
                text1 = acquisition.acquisitionDate,
                text2 = acquisition.currentStorage,
                text3 = acquisition.quantity.toString(),
                id = acquisition.id,
                onValueChange,
                onClose
            )
        }
    }
}
