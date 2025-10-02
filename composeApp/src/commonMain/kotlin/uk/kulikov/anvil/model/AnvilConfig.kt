package uk.kulikov.anvil.model

private const val ANVIL_LIMIT = 140

data class AnvilConfig(
    val target: Int = 0,
    val finalMove: AnvilMove? = null,
    val secondMove: AnvilMove? = null,
    val thirdMove: AnvilMove? = null
) {
    fun copyWithNewTarget(newTarget: Int): AnvilConfig {
        return copy(
            target = newTarget.coerceIn(0, ANVIL_LIMIT)
        )
    }
}