package me.rerere.compose_setting.components.types

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.rerere.compose_setting.components.SettingBaseItem

/**
 * A picker item, you can use it to pick a value from a list
 *
 * @param modifier Modifier
 * @param state The state of the picker
 * @param items The all items of the picker
 * @param itemLabel The label of the each picker item
 * @param icon The icon of the setting, will be displayed on the left of the title
 * @param title The title of the setting
 * @param text The text of the setting, will be displayed on the bottom of the title
 */
@Composable
fun <T> SettingPickerItem(
    modifier: Modifier = Modifier,
    state: MutableState<T>,
    items: List<T>,
    itemLabel: @Composable (T) -> Unit,
    icon: (@Composable () -> Unit)? = null,
    title: @Composable () -> Unit,
    text: (@Composable () -> Unit)? = null,
) {
    var dropdownVisible by remember { mutableStateOf(false) }
    Box {
        SettingBaseItem(
            title = title,
            icon = icon,
            text = text,
            modifier = modifier,
            onClick = {
                dropdownVisible = true
            },
            action = {
                Row {
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp)
                        ) {
                            itemLabel(state.value)

                            Icon(
                                if(dropdownVisible) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                                "Dropdown"
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = dropdownVisible,
                        onDismissRequest = {
                            dropdownVisible = false
                        }
                    ) {
                        items.forEach { item ->
                            DropdownMenuItem(
                                onClick = {
                                    state.value = item
                                    dropdownVisible = false
                                },
                                text = {
                                    itemLabel(item)
                                }
                            )
                        }
                    }
                }
            }
        )
    }
}