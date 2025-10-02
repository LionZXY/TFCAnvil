package uk.kulikov.anvil.composable.tool

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tfcanvil.composeapp.generated.resources.Res
import tfcanvil.composeapp.generated.resources.select_minus1
import tfcanvil.composeapp.generated.resources.select_minus10
import tfcanvil.composeapp.generated.resources.select_minus5
import tfcanvil.composeapp.generated.resources.select_plus1
import tfcanvil.composeapp.generated.resources.select_plus10
import tfcanvil.composeapp.generated.resources.select_plus5
import kotlin.math.max
import kotlin.math.min

private const val ANVIL_LIMIT = 140

@Composable
fun TargetSelectableComposable(
    counter: Int,
    onCounterChange: (Int) -> Unit,
) {
    Row {
        Text(
            text = "Target",
            style = MaterialTheme.typography.titleMedium,
        )

        TargetSelectableComposableInternal(
            modifier = Modifier.weight(1f),
            counter = counter,
            onCounterChange = onCounterChange
        )
    }
}

@Composable
private fun TargetSelectableComposableInternal(
    counter: Int,
    onCounterChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(3.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TargetSelectableIcon(
            icon = Res.drawable.select_minus10,
            onClick = { onCounterChange(counter - 10) }
        )
        TargetSelectableIcon(
            icon = Res.drawable.select_minus5,
            onClick = { onCounterChange(counter - 5) }
        )
        TargetSelectableIcon(
            icon = Res.drawable.select_minus1,
            onClick = { onCounterChange(counter - 1) }
        )
        EditFieldComposable(
            counter = counter,
            onCounterChange = onCounterChange
        )

        TargetSelectableIcon(
            icon = Res.drawable.select_plus1,
            onClick = { onCounterChange(counter + 1) }
        )
        TargetSelectableIcon(
            icon = Res.drawable.select_plus5,
            onClick = { onCounterChange(counter + 5) }
        )
        TargetSelectableIcon(
            icon = Res.drawable.select_plus10,
            onClick = { onCounterChange(counter + 10) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditFieldComposable(
    counter: Int,
    onCounterChange: (Int) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    BasicTextField(
        modifier = Modifier
            .width(56.dp)
            .border(3.dp, Color.Black),
        value = counter.toString(),
        onValueChange = { line ->
            if (line.isBlank()) {
                onCounterChange(0)
            } else {
                line.toIntOrNull()?.let { number -> onCounterChange(number) }
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
        ),
        singleLine = true,
        decorationBox = @Composable { innerTextField ->
            // places leading icon, text field with label and placeholder, trailing icon
            TextFieldDefaults.DecorationBox(
                value = counter.toString(),
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                singleLine = true,
                enabled = true,
                interactionSource = interactionSource,
                contentPadding = PaddingValues(vertical = 6.dp)
            )
        }
    )
}

@Composable
private fun TargetSelectableIcon(
    icon: DrawableResource,
    onClick: () -> Unit
) {

    Image(
        modifier = Modifier
            .size(32.dp)
            .border(1.dp, Color.Black)
            .clickable(onClick = onClick),
        painter = painterResource(icon),
        contentDescription = null
    )
}