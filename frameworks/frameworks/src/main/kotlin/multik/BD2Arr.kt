package multik

import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.get

fun main() {
    val b = mk.d2array(2, 2){it}
    //println(b)

    println(b[1])
}