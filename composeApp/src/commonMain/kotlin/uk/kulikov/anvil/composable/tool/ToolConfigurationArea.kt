package uk.kulikov.anvil.composable.tool

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ajailani.grid_compose.component.VerticalGrid
import com.ajailani.grid_compose.util.GridCellType
import kotlinx.coroutines.flow.MutableStateFlow
import uk.kulikov.anvil.model.AnvilConfig

@Composable
fun ToolConfigurationArea(
    anvilConfig: MutableStateFlow<AnvilConfig>,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = "Tool Configuration",
            style = MaterialTheme.typography.titleLarge
        )
        Column(
            modifier = Modifier.fillMaxWidth()
                .border(1.dp, Color.Black)
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ToolConfigurationAreaInternal(
                anvilConfig = anvilConfig,
            )
        }
    }
}

private val MIN_WIDTH = 230.dp

@Composable
private fun ToolConfigurationAreaInternal(
    anvilConfig: MutableStateFlow<AnvilConfig>,
) {
    val anvilConfigValue by anvilConfig.collectAsState()
    TargetSelectableComposable(
        modifier = Modifier.padding(horizontal = 16.dp),
        counter = anvilConfigValue.target,
        onCounterChange = {
            anvilConfig.value = anvilConfig.value.copyWithNewTarget(newTarget = it)
        }
    )
    TargetSliderComposable(
        counter = anvilConfigValue.target,
        onCounterChange = {
            anvilConfig.value = anvilConfig.value.copyWithNewTarget(newTarget = it)
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
                    0 -> anvilConfigValue.finalMove
                    1 -> anvilConfigValue.secondMove
                    else -> anvilConfigValue.thirdMove
                }

                AnvilMoveTileComposable(
                    modifier = Modifier.fillMaxWidth(),
                    title = title,
                    selectedMove = selectedMove,
                    onSelectMove = {
                        anvilConfig.value = when (index) {
                            0 -> anvilConfig.value.copy(finalMove = it)
                            1 -> anvilConfig.value.copy(secondMove = it)
                            else -> anvilConfig.value.copy(thirdMove = it)
                        }
                    }
                )
            }
        }
    }
}