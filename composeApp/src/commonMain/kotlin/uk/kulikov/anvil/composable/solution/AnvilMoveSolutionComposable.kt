package uk.kulikov.anvil.composable.solution

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ajailani.grid_compose.component.VerticalGrid
import com.ajailani.grid_compose.util.GridCellType
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import uk.kulikov.anvil.model.AnvilConfig
import uk.kulikov.anvil.model.AnvilMove
import uk.kulikov.anvil.utils.AnvilMoveException
import uk.kulikov.anvil.utils.solve

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
        Text(
            text = "Solution",
            style = MaterialTheme.typography.titleLarge
        )
        Column(
            modifier = Modifier.fillMaxWidth()
                .border(1.dp, Color.Black)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val result = solution?.getOrNull()
            if (result != null) {
                AnvilMoveSolutionInternal(
                    moves = result
                )
            }
            val localSolution = solution
            val text = if (localSolution == null) {
                "Loading..."
            } else {
                val exception = localSolution.exceptionOrNull()
                when (exception) {
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
    }

}

@Composable
private fun AnvilMoveSolutionInternal(
    moves: List<AnvilMove>,
    modifier: Modifier = Modifier
) {
    VerticalGrid(
        modifier = modifier,
        columns = GridCellType.Adaptive(48.dp + 2.dp)
    ) {
        items(moves.size) {
            val move = moves[it]

            Image(
                modifier = Modifier
                    .padding(2.dp)
                    .size(48.dp),
                painter = painterResource(move.icon),
                contentDescription = null
            )
        }
    }
}