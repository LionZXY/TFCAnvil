package uk.kulikov.anvil.composable.tool

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.kulikov.anvil.model.AnvilConfig

@Composable
fun ToolConfigurationArea(
    anvilConfig: AnvilConfig,
    onAnvilConfigChange: (AnvilConfig) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = "Tool Configuration",
            style = MaterialTheme.typography.titleLarge
        )
        Column(
            modifier = Modifier.fillMaxWidth()
                .border(1.dp, MaterialTheme.colorScheme.onPrimary)
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ToolConfigurationAreaInternal(
                anvilConfig = anvilConfig,
                onAnvilConfigChange = onAnvilConfigChange
            )
        }
    }
}

@Composable
private fun ColumnScope.ToolConfigurationAreaInternal(
    anvilConfig: AnvilConfig,
    onAnvilConfigChange: (AnvilConfig) -> Unit
) {
    TargetSelectableComposable(
        modifier = Modifier.padding(horizontal = 16.dp),
        counter = anvilConfig.target,
        onCounterChange = {
            onAnvilConfigChange(anvilConfig.copyWithNewTarget(newTarget = it))
        }
    )
    TargetSliderComposable(
        counter = anvilConfig.target,
        onCounterChange = {
            onAnvilConfigChange(anvilConfig.copyWithNewTarget(newTarget = it))
        }
    )

    Row {
        AnvilMoveTileComposable(
            modifier = Modifier.weight(1f),
            title = "Final",
            selectedMove = anvilConfig.finalMove,
            onSelectMove = {
                onAnvilConfigChange(anvilConfig.copy(finalMove = it))
            }
        )
        AnvilMoveTileComposable(
            modifier = Modifier.weight(1f),
            title = "2nd last",
            selectedMove = anvilConfig.secondMove,
            onSelectMove = {
                onAnvilConfigChange(anvilConfig.copy(secondMove = it))
            }
        )
        AnvilMoveTileComposable(
            modifier = Modifier.weight(1f),
            title = "3nd last",
            selectedMove = anvilConfig.thirdMove,
            onSelectMove = {
                onAnvilConfigChange(anvilConfig.copy(thirdMove = it))
            }
        )
    }
}