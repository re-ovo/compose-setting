package me.rerere.compose_setting.components.types

import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import me.rerere.compose_setting.components.SettingBaseItem

/**
 * A boolean item, you can use it to toggle a boolean value
 *
 * @param modifier Modifier
 * @param state The state of the boolean value
 * @param icon The icon of the item, will be displayed on the left of the title
 * @param title The title of the item
 * @param text The text of the item, will be displayed on the bottom of the title
 */
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