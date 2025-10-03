package uk.kulikov.anvil.model

private const val ANVIL_LIMIT = 140

data class AnvilConfig(
    val target: Int = 0,
    val secondMove: AnvilMove? = null,
    val thirdMove: AnvilMove? = null,
    val finalMove: AnvilMove? = null,
) {
    fun copyWithNewTarget(newTarget: Int): AnvilConfig {
        return copy(
            target = newTarget.coerceIn(0, ANVIL_LIMIT)
        )
    }

    fun getRequestList(): List<AnvilMove> {
        val lastElements = mutableListOf<AnvilMove>()

        if (thirdMove != null) {
            lastElements.add(thirdMove)
        }

        if (secondMove != null) {
            lastElements.add(secondMove)
        }

        if (finalMove != null) {
            lastElements.add(finalMove)
        }
        return lastElements
    }
}