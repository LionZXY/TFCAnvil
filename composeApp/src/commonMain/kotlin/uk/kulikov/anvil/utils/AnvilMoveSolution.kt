package uk.kulikov.anvil.utils

import uk.kulikov.anvil.model.AnvilConfig
import uk.kulikov.anvil.model.AnvilMove

class AnvilMoveException(message: String) : Throwable(message)

fun solve(
    config: AnvilConfig
): Result<List<AnvilMove>> = runCatching {
    val finalMove = config.finalMove ?:
    throw AnvilMoveException("No \"Final\" move has been selected. Please select one.")

    val secondMove = config.secondMove
        ?: throw AnvilMoveException("No \"2nd last\" move has been selected. Please select one.")

    val thirdMove = config.thirdMove
        ?: throw AnvilMoveException("No \"3nd last\" move has been selected. Please select one.")
    val req3 = listOf(finalMove, secondMove, thirdMove)

    // Queue holds pairs: current sum and path (list of moves)
    val q = ArrayDeque<Pair<Int, List<AnvilMove>>>()
    q.addLast(0 to emptyList())

    // Seen states: (current sum, last up-to-3 moves)
    val seen = HashSet<Pair<Int, List<AnvilMove>>>()
    seen.add(0 to emptyList())

    while (q.isNotEmpty()) {
        val (value, path) = q.removeFirst()

        // Check target and last 3 moves
        if (value == config.target && path.takeLast(3) == req3) {
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