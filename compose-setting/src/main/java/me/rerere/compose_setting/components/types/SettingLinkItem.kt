package me.rerere.compose_setting.components.types

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.rerere.compose_setting.components.SettingBaseItem

@Composable
fun SettingLinkItem(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    title: @Composable () -> Unit,
    text: (@Composable () -> Unit)? = null,
    onClick: () -> Unit
) {
    SettingBaseItem(
        modifier = modifier,
        icon = icon,
        title = title,
        text = text,
        onClick = onClick
    )
}