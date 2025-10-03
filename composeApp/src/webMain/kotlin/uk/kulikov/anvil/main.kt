package uk.kulikov.anvil

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.StorageSettings
import com.russhwolf.settings.observable.makeObservable
import uk.kulikov.anvil.utils.LocalSettings

@OptIn(ExperimentalComposeUiApi::class, ExperimentalSettingsApi::class)
fun main() {
    val settings = StorageSettings().makeObservable()
    ComposeViewport {
        CompositionLocalProvider(
            LocalSettings provides settings
        ) {
            App()
        }
    }
}