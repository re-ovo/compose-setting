package me.rerere.compose_setting.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                ProvideTextStyle(
                    MaterialTheme.typography.titleLarge.copy(
                        fontSize = 20.sp
                    )
                ) {
                    title()
                }
                ProvideTextStyle(MaterialTheme.typography.bodySmall) {
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