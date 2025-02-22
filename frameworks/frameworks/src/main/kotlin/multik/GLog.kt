package multik

import org.jetbrains.kotlinx.multik.api.math.log
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray

fun main() {
    val a = mk.ndarray(listOf(1,2,3,4), 2, 2)
    println(a.log())
}