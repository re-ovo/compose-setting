package me.rerere.compose_setting.components.types

import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import me.rerere.compose_setting.components.SettingBaseItem

@Composable
fun SettingBooleanItem(
    modifier: Modifier = Modifier,
    state: MutableState<Boolean>,
    icon: (@Composable () -> Unit)? = null,
    title: @Composable () -> Unit,
    text: (@Composable () -> Unit)? = null,
) {
    SettingBaseItem(
        modifier = modifier,
        icon = icon,
        title = title,
        text = text,
        action = {
            Switch(checked = state.value, onCheckedChange = { state.value = it })
        },
        onClick = {
            state.value = !state.value
        }
    )
}