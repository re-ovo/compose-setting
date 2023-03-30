package me.rerere.compose_setting.components.types

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.rerere.compose_setting.components.SettingBaseItem

/**
 * A link item, you can use it to navigate to another page
 * or do whatever you want, it's basically a base setting item
 * with a click listener
 *
 * @param modifier Modifier
 * @param icon The icon of the item, will be displayed on the left of the title
 * @param title The title of the item
 * @param text The text of the item, will be displayed on the bottom of the title
 * @param tailing The tailing of the item, will be displayed on the right of the item
 * @param onClick The click listener of the item
 */
@Composable
fun SettingLinkItem(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    title: @Composable () -> Unit,
    text: (@Composable () -> Unit)? = null,
    tailing: (@Composable () -> Unit)? = null,
    onClick: () -> Unit
) {
    SettingBaseItem(
        modifier = modifier,
        icon = icon,
        title = title,
        text = text,
        action = tailing,
        onClick = onClick
    )
}