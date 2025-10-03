package uk.kulikov.anvil.composable.solution

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ajailani.grid_compose.component.VerticalGrid
import com.ajailani.grid_compose.util.GridCellType
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.getBooleanFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import uk.kulikov.anvil.composable.common.AnvilMoveComposable
import uk.kulikov.anvil.model.AnvilConfig
import uk.kulikov.anvil.model.AnvilMove
import uk.kulikov.anvil.utils.AnvilMoveException
import uk.kulikov.anvil.utils.LocalSettings
import uk.kulikov.anvil.utils.solve

private const val SETTING_KEY = "step_by_step"

@OptIn(ExperimentalSettingsApi::class)
@Composable
fun AnvilMoveSolutionComposable(
    anvilConfig: State<AnvilConfig>,
    modifier: Modifier = Modifier,
) {
    val anvilConfigDerived = derivedStateOf { anvilConfig.value }
    var solution by remember {
        mutableStateOf<Result<List<AnvilMove>>?>(null)
    }
    val scope = rememberCoroutineScope()
    DisposableEffect(anvilConfigDerived.value to scope) {
        solution = null
        val job = scope.launch {
            solution = solve(anvilConfigDerived.value)
        }
        onDispose {
            job.cancel()
        }
    }


    Column(modifier) {
        val settings = LocalSettings.current
        val stepByStepSolution by remember {
            settings.getBooleanFlow(SETTING_KEY, false)
        }.collectAsState(false)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Solution",
                style = MaterialTheme.typography.titleLarge
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Enable step by step mode:",
                    style = MaterialTheme.typography.titleSmall
                )
                Checkbox(
                    modifier = Modifier.size(16.dp),
                    checked = stepByStepSolution,
                    onCheckedChange = { settings.putBoolean(SETTING_KEY, it) }
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth()
                .border(1.dp, Color.Black)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val result = solution?.getOrNull()
            if (result != null) {
                if (stepByStepSolution) {
                    StepByStepSolutionInternal(anvilConfig.value, result)
                } else {
                    AnvilMoveSolutionInternal(
                        moves = result
                    )
                }
            } else {
                AnvilMoveError(solution)
            }
        }
    }

}

@Composable
private fun AnvilMoveError(solution: Result<List<AnvilMove>>?) {
    val text = if (solution == null) {
        "Loading..."
    } else {
        when (val exception = solution.exceptionOrNull()) {
            null -> {
                null
            }

            is AnvilMoveException -> {
                exception.message
            }

            else -> {
                exception.printStackTrace()
                "Unknown error"
            }
        }
    }

    if (text != null) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun AnvilMoveSolutionInternal(
    moves: List<AnvilMove>,
    modifier: Modifier = Modifier
) {
    if (moves.isEmpty()) {
        Text("Select target value first")
    }
    VerticalGrid(
        modifier = modifier,
        columns = GridCellType.Adaptive(48.dp + 2.dp)
    ) {
        items(moves.size) {
            AnvilMoveComposable(
                modifier = Modifier.padding(2.dp),
                move = moves[it]
            )
        }
    }
}
