package uk.kulikov.anvil

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import uk.kulikov.anvil.composable.tool.AnvilMoveTileComposable
import uk.kulikov.anvil.composable.tool.TargetSelectableComposable
import uk.kulikov.anvil.model.AnvilMove

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TargetSelectableComposable()
            var move by remember { mutableStateOf<AnvilMove?>(null) }
            AnvilMoveTileComposable(
                "Final",
                selectedMove = move,
                onSelectMove = { move = it }
            )
        }
    }
}