package me.rerere.compose_setting.components.types

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import me.rerere.compose_setting.components.SettingBaseItem

@Composable
fun SettingStringItem(
    modifier: Modifier = Modifier,
    state: MutableState<String>,
    stateRange: Set<String>,
    icon: (@Composable () -> Unit)? = null,
    title: @Composable () -> Unit,
    text: (@Composable () -> Unit)? = null
) {
    var dialogVisible by remember {
        mutableStateOf(false)
    }
    SettingBaseItem(
        modifier = modifier,
        icon = icon,
        title = title,
        text = text,
        action = {
            DropdownMenu(
                expanded = dialogVisible,
                onDismissRequest = {
                    dialogVisible = false
                }
            ) {
                stateRange.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = item
                            )
                        },
                        onClick = {
                            state.value = item
                            dialogVisible = false
                        },
                        leadingIcon = {
                            if(item == state.value) {
                                Icon(Icons.Outlined.Check, null)
                            }
                        }
                    )
                }
            }
        },
        onClick = {
            dialogVisible = !dialogVisible
        }
    )
}