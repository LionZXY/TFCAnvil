package uk.kulikov.anvil

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.observable.makeObservable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import uk.kulikov.anvil.composable.solution.SolutionViewModel
import uk.kulikov.anvil.utils.LocalSettings
import java.util.prefs.Preferences

@OptIn(ExperimentalSettingsApi::class)
fun main() {
    val scope = CoroutineScope(SupervisorJob())
    val delegate: Preferences = Preferences.userRoot()
    val settings = PreferencesSettings(delegate).makeObservable()
    application {
        CompositionLocalProvider(
            LocalSettings provides settings
        ) {
            Window(
                onCloseRequest = ::exitApplication,
                title = "TFCAnvil",
            ) {
                App(SolutionViewModel(scope))
            }
        }
    }
}