package uk.kulikov.anvil.composable.solution

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajailani.grid_compose.component.VerticalGrid
import com.ajailani.grid_compose.util.GridCellType
import com.dshatz.composempp.AutoSizeText
import uk.kulikov.anvil.composable.common.AnvilMoveComposable
import uk.kulikov.anvil.model.AnvilConfig
import uk.kulikov.anvil.model.AnvilMove


@Composable
internal fun StepByStepSolutionInternal(
    config: AnvilConfig,
    moves: List<AnvilMove>
) {
    if (moves.isEmpty()) {
        Text("Select target value first")
        return
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val lastSteps = remember(config) { config.getRequestList() }
        val steps = remember(moves, lastSteps) {
            moves.dropLast(lastSteps.size)
                .groupBy { it }
                .map { (move, list) -> move to list.size }
        }

        VerticalGrid(
            modifier = Modifier,
            columns = GridCellType.Adaptive(48.dp + 2.dp)
        ) {
            items(steps.size) {
                val step = steps[it]
                StepColumn(
                    modifier = Modifier.padding(2.dp),
                    move = step.first,
                    text = "x${step.second}"
                )
            }
        }

        Text("And last steps:")

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            lastSteps.forEach { move ->
                AnvilMoveComposable(move)
            }
        }
    }
}

@Composable
private fun StepColumn(
    move: AnvilMove,
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.border(1.dp, Color.Gray),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnvilMoveComposable(
            move = move
        )
        AutoSizeText(
            modifier = Modifier.width(36.dp),
            text = text,
            alignment = Alignment.Center,
            maxLines = 1
        )
    }
}

