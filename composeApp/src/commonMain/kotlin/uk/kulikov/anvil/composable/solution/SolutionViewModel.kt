package uk.kulikov.anvil.composable.solution

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uk.kulikov.anvil.model.AnvilConfig
import uk.kulikov.anvil.model.AnvilMove
import uk.kulikov.anvil.utils.solve
import kotlin.time.Duration.Companion.seconds

@OptIn(FlowPreview::class)
class SolutionViewModel(
    private val coroutineScope: CoroutineScope
) {
    val configFlow = MutableStateFlow(AnvilConfig())
    val solutionFlow = MutableStateFlow<Result<List<AnvilMove>>?>(null)

    init {
        coroutineScope.launch(Dispatchers.Default) {
            configFlow
                .onEach {
                    solutionFlow.emit(null)
                }
                .debounce(timeout = 0.5.seconds)
                .collectLatest {
                    solutionFlow.emit(solve(coroutineScope, it))
                }

        }
    }
}