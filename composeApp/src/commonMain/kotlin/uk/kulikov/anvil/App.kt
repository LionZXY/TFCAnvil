package uk.kulikov.anvil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import uk.kulikov.anvil.composable.solution.AnvilMoveSolutionComposable
import uk.kulikov.anvil.composable.tool.ToolConfigurationArea
import uk.kulikov.anvil.model.AnvilConfig

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .safeContentPadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val config = remember { mutableStateOf(AnvilConfig()) }

            ToolConfigurationArea(
                modifier = Modifier.padding(16.dp),
                anvilConfig = config
            )
            AnvilMoveSolutionComposable(
                modifier = Modifier.padding(16.dp),
                anvilConfig = config
            )


            Text(
                text = buildAnnotatedString {
                    append("Original project: ")
                    withLink(LinkAnnotation.Url("https://github.com/Ertivc/ErtisTFCForgingBuddy")) {
                        withStyle(
                            SpanStyle(
                                color = MaterialTheme.colorScheme.secondary,
                                textDecoration = TextDecoration.Underline
                            )
                        ) {
                            append("Ertivc/ErtisTFCForgingBuddy")
                        }
                    }
                }
            )
        }
    }
}