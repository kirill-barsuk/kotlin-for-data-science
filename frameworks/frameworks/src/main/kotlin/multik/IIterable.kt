package multik

import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.operations.filter
import org.jetbrains.kotlinx.multik.ndarray.operations.map

fun main() {
    val a = mk.ndarray(listOf(1,2,3,4), 2, 2)
    println(a.filter{it >= 3}.map{it + 1})
}