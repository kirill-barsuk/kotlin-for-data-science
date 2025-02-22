package multik

import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.operations.plus

fun main() {
    val a = mk.ndarray(listOf(1,2,3,4), 2, 2)
    val b = mk.ndarray(listOf(4,3,2,1), 2, 2)
    println(a + b)
}