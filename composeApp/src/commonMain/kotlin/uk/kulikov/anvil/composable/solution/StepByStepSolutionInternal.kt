package uk.kulikov.anvil.composable.solution

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Text("Step by step:")
        val lastSteps = remember(config) { config.getRequestList() }
        val steps = remember(moves, lastSteps) {
            moves.dropLast(lastSteps.size)
                .groupBy { it }
                .map { (move, list) -> move to list.size }
        }

        steps.forEach { (move, count) ->
            StepLine(move, "x${count}")
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
private fun StepLine(move: AnvilMove, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "•",
            fontSize = 48.sp
        )
        AnvilMoveComposable(
            move = move
        )
        Text(
            text = text,
            fontSize = 36.sp
        )
    }
}

