package me.rerere.compose_setting.components.types

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import me.rerere.compose_setting.components.SettingBaseItem

@Composable
fun SettingStringPickerItem(
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
                            if (item == state.value) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingStringInputDialogItem(
    modifier: Modifier = Modifier,
    state: MutableState<String>,
    icon: (@Composable () -> Unit)? = null,
    title: @Composable () -> Unit,
    validator: (String) -> Boolean,
    invalidMessage: @Composable (String) -> Unit,
    confirmText: @Composable () -> Unit,
    dismissText: @Composable () -> Unit
) {
    var dialogVisible by remember { mutableStateOf(false) }
    var newValue by remember { mutableStateOf(state.value) }
    if (dialogVisible) {
        AlertDialog(
            onDismissRequest = { dialogVisible = false },
            icon = icon,
            title = title,
            text = {
                Column {
                    OutlinedTextField(
                        value = newValue,
                        onValueChange = {
                            newValue = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        isError = !validator(newValue),
                        maxLines = 1,
                        label = {
                            if(!validator(newValue)){
                                invalidMessage(newValue)
                            }
                        }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (validator(newValue)) {
                            state.value = newValue
                        }
                        dialogVisible = false
                    }
                ) {
                    confirmText()
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { dialogVisible = false }
                ) {
                    dismissText()
                }
            }
        )
    }
    SettingBaseItem(
        modifier = modifier,
        icon = icon,
        title = title,
        text = {
            Text(state.value)
        },
        onClick = {
            dialogVisible = !dialogVisible
        }
    )
}