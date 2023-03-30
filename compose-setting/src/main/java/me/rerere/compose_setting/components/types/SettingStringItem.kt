package me.rerere.compose_setting.components.types

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import me.rerere.compose_setting.components.SettingBaseItem

/**
 * A string input item, you can use it to input a string
 *
 * @param modifier Modifier
 * @param state The state of the input
 * @param icon The icon of the setting, will be displayed on the left of the title
 * @param title The title of the setting
 * @param validator The validator of the input
 * @param invalidMessage The message of the invalid input
 * @param confirmText The text of the confirm button
 * @param dismissText The text of the dismiss button
 */
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