package multik

import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.rand

fun main() {
    val d = mk.rand<Int>(2, 3)
    println(d)
}