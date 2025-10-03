package uk.kulikov.anvil.utils

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.compositionLocalOf
import com.russhwolf.settings.ObservableSettings

/**
 * CompositionLocal used to change the [TextSelectionColors] used by text and text field components
 * in the hierarchy.
 */
val LocalSettings = compositionLocalOf<ObservableSettings> { error("No LocalSettings") }