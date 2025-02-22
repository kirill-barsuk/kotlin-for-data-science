package multik

import org.jetbrains.kotlinx.multik.api.io.readCSV
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.*

fun main() {
    val a = mk.readCSV<Int, D2>("frameworks\\frameworks\\src\\main\\resources\\int_array.csv", ';')
    println(a)
}