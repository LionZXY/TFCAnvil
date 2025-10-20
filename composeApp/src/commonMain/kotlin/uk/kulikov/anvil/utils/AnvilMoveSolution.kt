package uk.kulikov.anvil.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.isActive
import uk.kulikov.anvil.model.AnvilConfig
import uk.kulikov.anvil.model.AnvilMove

class AnvilMoveException(message: String) : Throwable(message)

suspend fun solve(
    scope: CoroutineScope, config: AnvilConfig
): Result<List<AnvilMove>> = runCatching {
    val req3 = config.getRequestList()
    // Queue holds pairs: current sum and path (list of moves)
    val q = ArrayDeque<Pair<Int, List<AnvilMove>>>()
    q.addLast(0 to emptyList())

    // Seen states: (current sum, last up-to-3 moves)
    val seen = HashSet<Pair<Int, List<AnvilMove>>>()
    seen.add(0 to emptyList())

    while (scope.isActive && q.isNotEmpty()) {
        val (value, path) = q.removeFirst()

        // Check target and last 3 moves
        if (value == config.target && path.takeLast(req3.size) == req3) {
            return@runCatching path
        }

        for (m in AnvilMove.entries) {
            val nv = value + m.value
            val np = path + m
            val last3 = np.takeLast(3)
            val state = nv to last3
            if (seen.add(state)) {
                q.addLast(nv to np)
            }
        }
    }
    throw AnvilMoveException("No solution found")
}