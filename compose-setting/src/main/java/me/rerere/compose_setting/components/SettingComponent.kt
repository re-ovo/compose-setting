package me.rerere.compose_setting.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingBaseItem(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    title: @Composable () -> Unit,
    text: (@Composable () -> Unit)? = null,
    action: (@Composable () -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    Surface(
        onClick = { onClick.invoke() },
        color = Color.Unspecified,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = modifier
                .padding(
                    horizontal = MenuTokens.ContentPaddingHorizontal,
                    vertical = MenuTokens.ContentPaddingVertical
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MenuTokens.ElementHorizontalSpace)
        ) {
            icon?.invoke()
            Column(
                modifier = Modifier.weight(1f)
            ) {
                ProvideTextStyle(MaterialTheme.typography.titleLarge) {
                    title()
                }
                ProvideTextStyle(MaterialTheme.typography.bodyMedium) {
                    text?.invoke()
                }
            }
            action?.invoke()
        }
    }
}

internal object MenuTokens {
    val ContentPaddingHorizontal = 24.dp
    val ContentPaddingVertical = 12.dp
    val ElementHorizontalSpace = 24.dp
}