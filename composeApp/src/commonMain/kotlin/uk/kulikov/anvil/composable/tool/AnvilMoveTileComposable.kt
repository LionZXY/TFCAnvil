package uk.kulikov.anvil.composable.tool

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import uk.kulikov.anvil.model.AnvilMove

@Preview
@Composable
fun AnvilMoveTileComposable(
    title: String,
    selectedMove: AnvilMove? = null,
    onSelectMove: (AnvilMove) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 16.sp
        )

        AnvilMatrixComposable(
            selectedMove, onSelectMove
        )
    }
}

private val SPACE = 8.dp

@Composable
private fun AnvilMatrixComposable(
    selectedMove: AnvilMove? = null,
    onSelectMove: (AnvilMove) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(SPACE)
    ) {
        val rows = listOf(
            listOf(AnvilMove.MINUS3, AnvilMove.MINUS6, AnvilMove.PLUS2, AnvilMove.PLUS7),
            listOf(AnvilMove.MINUS9, AnvilMove.MINUS15, AnvilMove.PLUS13, AnvilMove.PLUS16)
        )
        rows.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(SPACE)
            ) {
                row.forEach { move ->
                    AnvilMatrixComposableIcon(
                        move,
                        isSelected = move == selectedMove,
                        onSelect = { onSelectMove(move) }
                    )
                }
            }
        }

    }
}

@Composable
private fun AnvilMatrixComposableIcon(
    move: AnvilMove,
    isSelected: Boolean,
    onSelect: () -> Unit,
) {
    Image(
        modifier = Modifier
            .size(48.dp)
            .border(3.dp, if (isSelected) Color.Yellow else Color.Black)
            .clickable(onClick = onSelect),
        painter = painterResource(move.icon),
        contentDescription = null
    )
}