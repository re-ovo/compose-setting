package me.rerere.compose_setting.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Switch
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
@Suppress("ComposableLambdaParameterNaming", "ComposableLambdaParameterPosition")
fun Switch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    thumbContent: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: SwitchColors = SwitchDefaults.colors(),
) {
    val uncheckedThumbDiameter = if (thumbContent == null) {
        UncheckedThumbDiameter
    } else {
        ThumbDiameter
    }

    val thumbPaddingStart = (SwitchHeight - uncheckedThumbDiameter) / 2
    val minBound = with(LocalDensity.current) { thumbPaddingStart.toPx() }
    val maxBound = with(LocalDensity.current) { ThumbPathLength.toPx() }
    val valueToOffset = remember<(Boolean) -> Float>(minBound, maxBound) {
        { value -> if (value) maxBound else minBound }
    }

    val offset = remember { Animatable(valueToOffset(checked)) }
    SideEffect {
        // min bound might have changed if the icon is only rendered in checked state.
        offset.updateBounds(lowerBound = minBound)
    }

    // 修复 https://issuetracker.google.com/issues/228336571
    LaunchedEffect(checked) {
        offset.animateTo(valueToOffset(checked), AnimationSpec)
    }

    // TODO: Add Swipeable modifier b/223797571
    val toggleableModifier =
        if (onCheckedChange != null) {
            Modifier.toggleable(
                value = checked,
                onValueChange = { value: Boolean ->
                    onCheckedChange(value)
                },
                enabled = enabled,
                role = Role.Switch,
                interactionSource = interactionSource,
                indication = null
            )
        } else {
            Modifier
        }

    Box(
        modifier
            .then(toggleableModifier)
            .wrapContentSize(Alignment.Center)
            .requiredSize(SwitchWidth, SwitchHeight)
    ) {
        SwitchImpl(
            checked = checked,
            enabled = enabled,
            colors = colors,
            thumbValue = offset.asState(),
            interactionSource = interactionSource,
            thumbShape = SwitchTokens.HandleShape.toShape(),
            uncheckedThumbDiameter = uncheckedThumbDiameter,
            minBound = thumbPaddingStart,
            maxBound = ThumbPathLength,
            thumbContent = thumbContent,
        )
    }
}

/**
 * Represents the colors used by a [Switch] in different states
 *
 * See [SwitchDefaults.colors] for the default implementation that follows Material
 * specifications.
 */
@Stable
interface SwitchColors {

    /**
     * Represents the color used for the switch's thumb, depending on [enabled] and [checked].
     *
     * @param enabled whether the [Switch] is enabled or not
     * @param checked whether the [Switch] is checked or not
     */
    @Composable
    fun thumbColor(enabled: Boolean, checked: Boolean): State<Color>

    /**
     * Represents the color used for the switch's track, depending on [enabled] and [checked].
     *
     * @param enabled whether the [Switch] is enabled or not
     * @param checked whether the [Switch] is checked or not
     */
    @Composable
    fun trackColor(enabled: Boolean, checked: Boolean): State<Color>

    /**
     * Represents the color used for the switch's border, depending on [enabled] and [checked].
     *
     * @param enabled whether the [Switch] is enabled or not
     * @param checked whether the [Switch] is checked or not
     */
    @Composable
    fun borderColor(enabled: Boolean, checked: Boolean): State<Color>

    /**
     * Represents the content color passed to the icon if used
     *
     * @param enabled whether the [Switch] is enabled or not
     * @param checked whether the [Switch] is checked or not
     */
    @Composable
    fun iconColor(enabled: Boolean, checked: Boolean): State<Color>
}

@Composable
@Suppress("ComposableLambdaParameterNaming", "ComposableLambdaParameterPosition")
private fun BoxScope.SwitchImpl(
    checked: Boolean,
    enabled: Boolean,
    colors: SwitchColors,
    thumbValue: State<Float>,
    thumbContent: (@Composable () -> Unit)?,
    interactionSource: InteractionSource,
    thumbShape: Shape,
    uncheckedThumbDiameter: Dp,
    minBound: Dp,
    maxBound: Dp,
) {
    val trackColor by colors.trackColor(enabled, checked)
    val isPressed by interactionSource.collectIsPressedAsState()

    val thumbValueDp = with(LocalDensity.current) { thumbValue.value.toDp() }
    val thumbSizeDp = if (isPressed) {
        SwitchTokens.PressedHandleWidth
    } else {
        uncheckedThumbDiameter + (ThumbDiameter - uncheckedThumbDiameter) *
                ((thumbValueDp - minBound) / (maxBound - minBound))
    }

    val thumbOffset = if (isPressed) {
        with(LocalDensity.current) {
            if (checked) {
                ThumbPathLength - SwitchTokens.TrackOutlineWidth
            } else {
                SwitchTokens.TrackOutlineWidth
            }.toPx()
        }
    } else {
        thumbValue.value
    }

    val trackShape = SwitchTokens.TrackShape.toShape()
    val modifier = Modifier
        .align(Alignment.Center)
        .width(SwitchWidth)
        .height(SwitchHeight)
        .border(
            SwitchTokens.TrackOutlineWidth,
            colors.borderColor(enabled, checked).value,
            trackShape
        )
        .background(trackColor, trackShape)

    Box(modifier) {
        val thumbColor by colors.thumbColor(enabled, checked)
        val resolvedThumbColor = thumbColor
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset { IntOffset(thumbOffset.roundToInt(), 0) }
                .indication(
                    interactionSource = interactionSource,
                    indication = rememberRipple(bounded = false, SwitchTokens.StateLayerSize / 2)
                )
                .requiredSize(thumbSizeDp)
                .background(resolvedThumbColor, thumbShape),
            contentAlignment = Alignment.Center
        ) {
            if (thumbContent != null) {
                val iconColor = colors.iconColor(enabled, checked)
                CompositionLocalProvider(
                    LocalContentColor provides iconColor.value,
                    content = thumbContent
                )
            }
        }
    }
}

internal val ThumbDiameter = SwitchTokens.SelectedHandleWidth
internal val UncheckedThumbDiameter = SwitchTokens.UnselectedHandleWidth
private val SwitchWidth = SwitchTokens.TrackWidth
private val SwitchHeight = SwitchTokens.TrackHeight
private val ThumbPadding = (SwitchHeight - ThumbDiameter) / 2
private val ThumbPathLength = (SwitchWidth - ThumbDiameter) - ThumbPadding

private val AnimationSpec = TweenSpec<Float>(durationMillis = 100)

/**
 * Contains the default values used by [Switch]
 */
object SwitchDefaults {
    /**
     * Creates a [SwitchColors] that represents the different colors used in a [Switch] in
     * different states.
     *
     * @param checkedThumbColor the color used for the thumb when enabled and checked
     * @param checkedTrackColor the color used for the track when enabled and checked
     * @param checkedBorderColor the color used for the border when enabled and checked
     * @param checkedIconColor the color used for the icon when enabled and checked
     * @param uncheckedThumbColor the color used for the thumb when enabled and unchecked
     * @param uncheckedTrackColor the color used for the track when enabled and unchecked
     * @param uncheckedBorderColor the color used for the border when enabled and unchecked
     * @param uncheckedIconColor the color used for the icon when enabled and unchecked
     * @param disabledCheckedThumbColor the color used for the thumb when disabled and checked
     * @param disabledCheckedTrackColor the color used for the track when disabled and checked
     * @param disabledCheckedBorderColor the color used for the border when disabled and checked
     * @param disabledCheckedIconColor the color used for the icon when disabled and checked
     * @param disabledUncheckedThumbColor the color used for the thumb when disabled and unchecked
     * @param disabledUncheckedTrackColor the color used for the track when disabled and unchecked
     * @param disabledUncheckedBorderColor the color used for the border when disabled and unchecked
     * @param disabledUncheckedIconColor the color used for the icon when disabled and unchecked
     */
    @Composable
    fun colors(
        checkedThumbColor: Color = SwitchTokens.SelectedHandleColor.toColor(),
        checkedTrackColor: Color = SwitchTokens.SelectedTrackColor.toColor(),
        checkedBorderColor: Color = Color.Transparent,
        checkedIconColor: Color = SwitchTokens.SelectedIconColor.toColor(),
        uncheckedThumbColor: Color = SwitchTokens.UnselectedHandleColor.toColor(),
        uncheckedTrackColor: Color = SwitchTokens.UnselectedTrackColor.toColor(),
        uncheckedBorderColor: Color = SwitchTokens.UnselectedFocusTrackOutlineColor.toColor(),
        uncheckedIconColor: Color = SwitchTokens.UnselectedIconColor.toColor(),
        disabledCheckedThumbColor: Color = checkedThumbColor
            .copy(alpha = SwitchTokens.DisabledSelectedHandleOpacity)
            .compositeOver(MaterialTheme.colorScheme.surface),
        disabledCheckedTrackColor: Color = checkedTrackColor
            .copy(alpha = SwitchTokens.DisabledTrackOpacity)
            .compositeOver(MaterialTheme.colorScheme.surface),
        disabledCheckedBorderColor: Color = Color.Transparent,
        disabledCheckedIconColor: Color = SwitchTokens.DisabledSelectedIconColor.toColor()
            .copy(alpha = SwitchTokens.DisabledSelectedIconOpacity)
            .compositeOver(MaterialTheme.colorScheme.surface),
        disabledUncheckedThumbColor: Color = uncheckedThumbColor
            .copy(alpha = SwitchTokens.DisabledSelectedHandleOpacity)
            .compositeOver(MaterialTheme.colorScheme.surface),
        disabledUncheckedTrackColor: Color = uncheckedTrackColor
            .copy(alpha = SwitchTokens.DisabledTrackOpacity)
            .compositeOver(MaterialTheme.colorScheme.surface),
        disabledUncheckedBorderColor: Color =
            SwitchTokens.DisabledUnselectedTrackOutlineColor.toColor()
                .copy(alpha = SwitchTokens.DisabledTrackOpacity)
                .compositeOver(MaterialTheme.colorScheme.surface),
        disabledUncheckedIconColor: Color = SwitchTokens.DisabledUnselectedIconColor.toColor()
            .copy(alpha = SwitchTokens.DisabledUnselectedIconOpacity)
            .compositeOver(MaterialTheme.colorScheme.surface),
    ): SwitchColors = DefaultSwitchColors(
        checkedThumbColor = checkedThumbColor,
        checkedTrackColor = checkedTrackColor,
        checkedBorderColor = checkedBorderColor,
        checkedIconColor = checkedIconColor,
        uncheckedThumbColor = uncheckedThumbColor,
        uncheckedTrackColor = uncheckedTrackColor,
        uncheckedBorderColor = uncheckedBorderColor,
        uncheckedIconColor = uncheckedIconColor,
        disabledCheckedThumbColor = disabledCheckedThumbColor,
        disabledCheckedTrackColor = disabledCheckedTrackColor,
        disabledCheckedBorderColor = disabledCheckedBorderColor,
        disabledCheckedIconColor = disabledCheckedIconColor,
        disabledUncheckedThumbColor = disabledUncheckedThumbColor,
        disabledUncheckedTrackColor = disabledUncheckedTrackColor,
        disabledUncheckedBorderColor = disabledUncheckedBorderColor,
        disabledUncheckedIconColor = disabledUncheckedIconColor
    )

    /**
     * Icon size to use for `thumbContent`
     */
    val IconSize = 16.dp
}

/**
 * Default [SwitchColors] implementation.
 */
@Immutable
private class DefaultSwitchColors(
    private val checkedThumbColor: Color,
    private val checkedTrackColor: Color,
    private val checkedBorderColor: Color,
    private val checkedIconColor: Color,
    private val uncheckedThumbColor: Color,
    private val uncheckedTrackColor: Color,
    private val uncheckedBorderColor: Color,
    private val uncheckedIconColor: Color,
    private val disabledCheckedThumbColor: Color,
    private val disabledCheckedTrackColor: Color,
    private val disabledCheckedBorderColor: Color,
    private val disabledCheckedIconColor: Color,
    private val disabledUncheckedThumbColor: Color,
    private val disabledUncheckedTrackColor: Color,
    private val disabledUncheckedBorderColor: Color,
    private val disabledUncheckedIconColor: Color
) : SwitchColors {
    @Composable
    override fun thumbColor(enabled: Boolean, checked: Boolean): State<Color> {
        return rememberUpdatedState(
            if (enabled) {
                if (checked) checkedThumbColor else uncheckedThumbColor
            } else {
                if (checked) disabledCheckedThumbColor else disabledUncheckedThumbColor
            }
        )
    }

    @Composable
    override fun trackColor(enabled: Boolean, checked: Boolean): State<Color> {
        return rememberUpdatedState(
            if (enabled) {
                if (checked) checkedTrackColor else uncheckedTrackColor
            } else {
                if (checked) disabledCheckedTrackColor else disabledUncheckedTrackColor
            }
        )
    }

    @Composable
    override fun borderColor(enabled: Boolean, checked: Boolean): State<Color> {
        return rememberUpdatedState(
            if (enabled) {
                if (checked) checkedBorderColor else uncheckedBorderColor
            } else {
                if (checked) disabledCheckedBorderColor else disabledUncheckedBorderColor
            }
        )
    }

    @Composable
    override fun iconColor(enabled: Boolean, checked: Boolean): State<Color> {
        return rememberUpdatedState(
            if (enabled) {
                if (checked) checkedIconColor else uncheckedIconColor
            } else {
                if (checked) disabledCheckedIconColor else disabledUncheckedIconColor
            }
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DefaultSwitchColors

        if (checkedThumbColor != other.checkedThumbColor) return false
        if (checkedTrackColor != other.checkedTrackColor) return false
        if (checkedBorderColor != other.checkedBorderColor) return false
        if (checkedIconColor != other.checkedIconColor) return false
        if (uncheckedThumbColor != other.uncheckedThumbColor) return false
        if (uncheckedTrackColor != other.uncheckedTrackColor) return false
        if (uncheckedBorderColor != other.uncheckedBorderColor) return false
        if (uncheckedIconColor != other.uncheckedIconColor) return false
        if (disabledCheckedThumbColor != other.disabledCheckedThumbColor) return false
        if (disabledCheckedTrackColor != other.disabledCheckedTrackColor) return false
        if (disabledCheckedBorderColor != other.disabledCheckedBorderColor) return false
        if (disabledCheckedIconColor != other.disabledCheckedIconColor) return false
        if (disabledUncheckedThumbColor != other.disabledUncheckedThumbColor) return false
        if (disabledUncheckedTrackColor != other.disabledUncheckedTrackColor) return false
        if (disabledUncheckedBorderColor != other.disabledUncheckedBorderColor) return false
        if (disabledUncheckedIconColor != other.disabledUncheckedIconColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = checkedThumbColor.hashCode()
        result = 31 * result + checkedTrackColor.hashCode()
        result = 31 * result + checkedBorderColor.hashCode()
        result = 31 * result + checkedIconColor.hashCode()
        result = 31 * result + uncheckedThumbColor.hashCode()
        result = 31 * result + uncheckedTrackColor.hashCode()
        result = 31 * result + uncheckedBorderColor.hashCode()
        result = 31 * result + uncheckedIconColor.hashCode()
        result = 31 * result + disabledCheckedThumbColor.hashCode()
        result = 31 * result + disabledCheckedTrackColor.hashCode()
        result = 31 * result + disabledCheckedBorderColor.hashCode()
        result = 31 * result + disabledCheckedIconColor.hashCode()
        result = 31 * result + disabledUncheckedThumbColor.hashCode()
        result = 31 * result + disabledUncheckedTrackColor.hashCode()
        result = 31 * result + disabledUncheckedBorderColor.hashCode()
        result = 31 * result + disabledUncheckedIconColor.hashCode()
        return result
    }
}

internal object SwitchTokens {
    val DisabledSelectedHandleColor = ColorSchemeKeyTokens.Surface
    const val DisabledSelectedHandleOpacity = 1.0f
    val DisabledSelectedIconColor = ColorSchemeKeyTokens.OnSurface
    const val DisabledSelectedIconOpacity = 0.38f
    val DisabledSelectedTrackColor = ColorSchemeKeyTokens.OnSurface
    const val DisabledTrackOpacity = 0.12f
    val DisabledUnselectedHandleColor = ColorSchemeKeyTokens.OnSurface
    const val DisabledUnselectedHandleOpacity = 0.38f
    val DisabledUnselectedIconColor = ColorSchemeKeyTokens.SurfaceVariant
    const val DisabledUnselectedIconOpacity = 0.38f
    val DisabledUnselectedTrackColor = ColorSchemeKeyTokens.SurfaceVariant
    val DisabledUnselectedTrackOutlineColor = ColorSchemeKeyTokens.OnSurface
    val HandleShape = ShapeKeyTokens.CornerFull
    val PressedHandleHeight = 28.0.dp
    val PressedHandleWidth = 28.0.dp
    val SelectedFocusHandleColor = ColorSchemeKeyTokens.PrimaryContainer
    val SelectedFocusIconColor = ColorSchemeKeyTokens.OnPrimaryContainer
    val SelectedFocusTrackColor = ColorSchemeKeyTokens.Primary
    val SelectedHandleColor = ColorSchemeKeyTokens.OnPrimary
    val SelectedHandleHeight = 24.0.dp
    val SelectedHandleWidth = 24.0.dp
    val SelectedHoverHandleColor = ColorSchemeKeyTokens.PrimaryContainer
    val SelectedHoverIconColor = ColorSchemeKeyTokens.OnPrimaryContainer
    val SelectedHoverTrackColor = ColorSchemeKeyTokens.Primary
    val SelectedIconColor = ColorSchemeKeyTokens.OnPrimaryContainer
    val SelectedIconSize = 16.0.dp
    val SelectedPressedHandleColor = ColorSchemeKeyTokens.PrimaryContainer
    val SelectedPressedIconColor = ColorSchemeKeyTokens.OnPrimaryContainer
    val SelectedPressedTrackColor = ColorSchemeKeyTokens.Primary
    val SelectedTrackColor = ColorSchemeKeyTokens.Primary
    val StateLayerShape = ShapeKeyTokens.CornerFull
    val StateLayerSize = 40.0.dp
    val TrackHeight = 32.0.dp
    val TrackOutlineWidth = 2.0.dp
    val TrackShape = ShapeKeyTokens.CornerFull
    val TrackWidth = 52.0.dp
    val UnselectedFocusHandleColor = ColorSchemeKeyTokens.OnSurfaceVariant
    val UnselectedFocusIconColor = ColorSchemeKeyTokens.SurfaceVariant
    val UnselectedFocusTrackColor = ColorSchemeKeyTokens.SurfaceVariant
    val UnselectedFocusTrackOutlineColor = ColorSchemeKeyTokens.Outline
    val UnselectedHandleColor = ColorSchemeKeyTokens.Outline
    val UnselectedHandleHeight = 16.0.dp
    val UnselectedHandleWidth = 16.0.dp
    val UnselectedHoverHandleColor = ColorSchemeKeyTokens.OnSurfaceVariant
    val UnselectedHoverIconColor = ColorSchemeKeyTokens.SurfaceVariant
    val UnselectedHoverTrackColor = ColorSchemeKeyTokens.SurfaceVariant
    val UnselectedHoverTrackOutlineColor = ColorSchemeKeyTokens.Outline
    val UnselectedIconColor = ColorSchemeKeyTokens.SurfaceVariant
    val UnselectedIconSize = 16.0.dp
    val UnselectedPressedHandleColor = ColorSchemeKeyTokens.OnSurfaceVariant
    val UnselectedPressedIconColor = ColorSchemeKeyTokens.SurfaceVariant
    val UnselectedPressedTrackColor = ColorSchemeKeyTokens.SurfaceVariant
    val UnselectedPressedTrackOutlineColor = ColorSchemeKeyTokens.Outline
    val UnselectedTrackColor = ColorSchemeKeyTokens.SurfaceVariant
    val IconHandleHeight = 24.0.dp
    val IconHandleWidth = 24.0.dp
}

enum class ColorSchemeKeyTokens {
    OnPrimary,
    OnPrimaryContainer,
    OnSurface,
    OnSurfaceVariant,
    Outline,
    Primary,
    PrimaryContainer,
    Surface,
    SurfaceVariant
}

internal enum class ShapeKeyTokens {
    CornerExtraLarge,
    CornerExtraLargeTop,
    CornerExtraSmall,
    CornerExtraSmallTop,
    CornerFull,
    CornerLarge,
    CornerLargeEnd,
    CornerLargeTop,
    CornerMedium,
    CornerNone,
    CornerSmall,
}

@Composable
fun ColorSchemeKeyTokens.toColor(): Color = when (this) {
    ColorSchemeKeyTokens.OnPrimary -> MaterialTheme.colorScheme.onPrimary
    ColorSchemeKeyTokens.OnPrimaryContainer -> MaterialTheme.colorScheme.onPrimaryContainer
    ColorSchemeKeyTokens.OnSurface -> MaterialTheme.colorScheme.onSurface
    ColorSchemeKeyTokens.OnSurfaceVariant -> MaterialTheme.colorScheme.onSurfaceVariant
    ColorSchemeKeyTokens.Outline -> MaterialTheme.colorScheme.outline
    ColorSchemeKeyTokens.Primary -> MaterialTheme.colorScheme.primary
    ColorSchemeKeyTokens.PrimaryContainer -> MaterialTheme.colorScheme.primaryContainer
    ColorSchemeKeyTokens.Surface -> MaterialTheme.colorScheme.surface
    ColorSchemeKeyTokens.SurfaceVariant -> MaterialTheme.colorScheme.surfaceVariant
}

@Composable
internal fun ShapeKeyTokens.toShape(): Shape {
    return MaterialTheme.shapes.fromToken(this)
}

internal fun Shapes.fromToken(value: ShapeKeyTokens): Shape {
    return when (value) {
        ShapeKeyTokens.CornerExtraLarge -> extraLarge
        ShapeKeyTokens.CornerExtraLargeTop -> extraLarge
        ShapeKeyTokens.CornerExtraSmall -> extraSmall
        ShapeKeyTokens.CornerExtraSmallTop -> extraSmall
        ShapeKeyTokens.CornerFull -> Shapes.Full
        ShapeKeyTokens.CornerLarge -> large
        ShapeKeyTokens.CornerLargeEnd -> large
        ShapeKeyTokens.CornerLargeTop -> large
        ShapeKeyTokens.CornerMedium -> medium
        ShapeKeyTokens.CornerNone -> Shapes.None
        ShapeKeyTokens.CornerSmall -> small
    }
}