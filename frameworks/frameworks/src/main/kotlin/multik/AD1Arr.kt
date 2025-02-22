package multik

import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray

fun main() {
    val a = mk.ndarray(mk[1, 2, 3])
    println(a)
}