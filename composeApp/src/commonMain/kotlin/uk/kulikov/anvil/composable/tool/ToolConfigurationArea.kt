package uk.kulikov.anvil.composable.tool

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ajailani.grid_compose.component.VerticalGrid
import com.ajailani.grid_compose.util.GridCellType
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

private val MIN_WIDTH = 230.dp

@Composable
private fun ToolConfigurationAreaInternal(
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

    BoxWithConstraints {
        val elementSize = maxWidth / 3
        VerticalGrid(
            columns = GridCellType.Adaptive(
                if (elementSize < MIN_WIDTH) {
                    MIN_WIDTH
                } else elementSize
            )
        ) {
            items(3) { index ->
                val title = when (index) {
                    0 -> "Final"
                    1 -> "2nd last"
                    else -> "3rd last"
                }
                val selectedMove = when (index) {
                    0 -> anvilConfig.finalMove
                    1 -> anvilConfig.secondMove
                    else -> anvilConfig.thirdMove
                }

                AnvilMoveTileComposable(
                    modifier = Modifier.fillMaxWidth(),
                    title = title,
                    selectedMove = selectedMove,
                    onSelectMove = {
                        when (index) {
                            0 -> onAnvilConfigChange(anvilConfig.copy(finalMove = it))
                            1 -> onAnvilConfigChange(anvilConfig.copy(secondMove = it))
                            else -> onAnvilConfigChange(anvilConfig.copy(thirdMove = it))
                        }
                    }
                )
            }
        }
    }
}