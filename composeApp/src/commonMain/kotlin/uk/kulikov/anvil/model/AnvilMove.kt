package uk.kulikov.anvil.model

import org.jetbrains.compose.resources.DrawableResource
import tfcanvil.composeapp.generated.resources.Res
import tfcanvil.composeapp.generated.resources.move_minus15
import tfcanvil.composeapp.generated.resources.move_minus3
import tfcanvil.composeapp.generated.resources.move_minus6
import tfcanvil.composeapp.generated.resources.move_minus9
import tfcanvil.composeapp.generated.resources.move_plus13
import tfcanvil.composeapp.generated.resources.move_plus16
import tfcanvil.composeapp.generated.resources.move_plus2
import tfcanvil.composeapp.generated.resources.move_plus7

enum class AnvilMove(val icon: DrawableResource) {
    PLUS2(Res.drawable.move_plus2),
    PLUS7(Res.drawable.move_plus7),
    PLUS13(Res.drawable.move_plus13),
    PLUS16(Res.drawable.move_plus16),
    MINUS3(Res.drawable.move_minus3),
    MINUS6(Res.drawable.move_minus6),
    MINUS9(Res.drawable.move_minus9),
    MINUS15(Res.drawable.move_minus15)
}