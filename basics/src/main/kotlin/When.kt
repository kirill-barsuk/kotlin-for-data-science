fun main() {
    println(replace("nine")) //OK
    println(replace("ine")) //Exception
}

private fun replace(s: String): Int {
    return when (s) { // when is an expression
        "one" -> 1
        "two" -> 2
        "three" -> 3
        "four" -> 4
        "five" -> 5
        "six" -> 6
        "seven" -> 7
        "eight" -> 8
        "nine" -> 9
        "zero" -> 0
        else -> throw Exception("Not a digit!")
    }
}
