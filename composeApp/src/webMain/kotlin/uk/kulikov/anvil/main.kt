package uk.kulikov.anvil

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.StorageSettings
import com.russhwolf.settings.observable.makeObservable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import uk.kulikov.anvil.composable.solution.SolutionViewModel
import uk.kulikov.anvil.utils.LocalSettings

@OptIn(ExperimentalComposeUiApi::class, ExperimentalSettingsApi::class)
fun main() {
    val scope = CoroutineScope(SupervisorJob())
    val settings = StorageSettings().makeObservable()
    val viewModel = SolutionViewModel(scope)
    ComposeViewport {
        CompositionLocalProvider(
            LocalSettings provides settings
        ) {
            App(viewModel)
        }
    }
}