package multik

import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray

fun main() {
    val c = mk.ndarray(listOf(1,2,3,4,5,6), 2, 3, 1)
//    println(c)

//    println(c.dim)
    println(c.size)
}