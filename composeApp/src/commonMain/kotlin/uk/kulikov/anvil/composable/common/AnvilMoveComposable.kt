package uk.kulikov.anvil.composable.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import uk.kulikov.anvil.model.AnvilMove

@Composable
fun AnvilMoveComposable(
    move: AnvilMove,
    modifier: Modifier = Modifier,
) {
    Image(
        modifier = modifier
            .size(48.dp),
        painter = painterResource(move.icon),
        contentDescription = null
    )
}