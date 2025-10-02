package uk.kulikov.anvil

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import uk.kulikov.anvil.composable.solution.AnvilMoveSolutionComposable
import uk.kulikov.anvil.composable.tool.AnvilMoveTileComposable
import uk.kulikov.anvil.composable.tool.TargetSelectableComposable
import uk.kulikov.anvil.composable.tool.TargetSliderComposable
import uk.kulikov.anvil.composable.tool.ToolConfigurationArea
import uk.kulikov.anvil.model.AnvilConfig
import uk.kulikov.anvil.model.AnvilMove

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            var config by remember { mutableStateOf(AnvilConfig()) }

            ToolConfigurationArea(
                modifier = Modifier.padding(16.dp),
                anvilConfig = config,
                onAnvilConfigChange = { config = it }
            )
            AnvilMoveSolutionComposable(
                modifier = Modifier.padding(16.dp),
                anvilConfig = config
            )
        }
    }
}