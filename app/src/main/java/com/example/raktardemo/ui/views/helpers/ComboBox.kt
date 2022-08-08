package com.example.raktardemo.ui.views.helpers

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import com.example.raktardemo.R
import com.example.raktardemo.data.enums.QuantityUnit
import com.example.raktardemo.data.model.Storage

@Composable
fun ComboBox(
    list: List<Any>,
    selectedIndex: Int,
    onIndexChanged: (Int) -> Unit,
    isExpanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
    textWidth: Dp
) {
    var stringList = emptyList<String>()
    var storageList = emptyList<Storage>()
    var enumList = emptyList<QuantityUnit>()

    Button(
        content = {
            Row() {
                when (list[0]) {
                    is String -> {
                        stringList = list as List<String>

                        Text(
                            text = stringList[selectedIndex],
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .width(textWidth)
                        )
                    }
                    is Storage -> {
                        storageList = list as List<Storage>

                        Text(
                            text = storageList[selectedIndex].name,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .width(textWidth)
                        )
                    }
                    is QuantityUnit -> {
                        enumList = list as List<QuantityUnit>

                        Text(
                            text = enumList[selectedIndex].translation,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .width(textWidth)
                        )
                    }
                    else -> {
                        Text(
                            text = "Hiba",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .width(textWidth)
                        )
                    }
                }

                Image(
                    painter = painterResource(
                        id = R.drawable.ic_baseline_keyboard_arrow_down_24
                    ),
                    contentDescription = null
                )
            }
        },
        onClick = { onExpandedChanged(true) },
    )

    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = { onExpandedChanged(false) },
    ) {
        when (list[0]) {
            is String -> {
                stringList.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        onClick = {
                            onIndexChanged(index)
                            onExpandedChanged(false)
                        }
                    ) {
                        Text(text = s)
                    }
                }
            }
            is Storage -> {
                storageList.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        onClick = {
                            onIndexChanged(index)
                            onExpandedChanged(false)
                        }
                    ) {
                        Text(text = s.name)
                    }
                }
            }
            is QuantityUnit -> {
                enumList.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        onClick = {
                            onIndexChanged(index)
                            onExpandedChanged(false)
                        }
                    ) {
                        Text(text = s.translation)
                    }
                }
            }
            else -> {
                //TODO?
            }
        }
    }
}