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

@Composable
fun ComboBox(
    list: List<String>,
    selectedIndex: Int,
    onIndexChanged: (Int) -> Unit,
    isExpanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
    textWidth: Dp
) {
    Button(
        content = {
            Row() {
                Text(
                    text = list[selectedIndex],
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .width(textWidth)
                )

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
        list.forEachIndexed { index, s ->
            DropdownMenuItem(
                onClick = {
                    onIndexChanged(index)
                    onExpandedChanged(false)
                }
            ) {

            }
        }
    }
}

