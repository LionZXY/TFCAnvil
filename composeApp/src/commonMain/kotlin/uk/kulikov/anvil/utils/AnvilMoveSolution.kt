package uk.kulikov.anvil.utils

import uk.kulikov.anvil.model.AnvilConfig
import uk.kulikov.anvil.model.AnvilMove

class AnvilMoveException(message: String) : Throwable(message)

fun solve(
    config: AnvilConfig
): Result<List<AnvilMove>> = runCatching {
    // Queue holds pairs: current sum and path (list of moves)
    val q = ArrayDeque<Pair<Int, List<AnvilMove>>>()
    q.addLast(0 to emptyList())

    // Seen states: (current sum, last up-to-3 moves)
    val seen = HashSet<Pair<Int, List<AnvilMove>>>()
    seen.add(0 to emptyList())

    while (q.isNotEmpty()) {
        val (value, path) = q.removeFirst()

        // Check target and last 3 moves
        if (isPathValid(value, path, config)) {
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

private fun isPathValid(value: Int, path: List<AnvilMove>, config: AnvilConfig): Boolean {
    if (value != config.target) {
        return false
    }
    val lastElements = mutableListOf<AnvilMove>()

    if (config.thirdMove != null) {
        lastElements.add(config.thirdMove)
    }

    if (config.secondMove != null) {
        lastElements.add(config.secondMove)
    }

    if (config.finalMove != null) {
        lastElements.add(config.finalMove)
    }

    return path.takeLast(lastElements.size) == lastElements
}